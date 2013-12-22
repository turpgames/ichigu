package com.turpgames.ichigu.server.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.turpgames.framework.v0.db.DbManager;
import com.turpgames.framework.v0.db.SqlQuery;
import com.turpgames.framework.v0.util.Utils;

public class Player {
	private int id;
	private String username;
	private String password;
	private String email;
	private String facebookId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	@Override
	public String toString() {
		return username;
	}

	public boolean insert() {
		try {
			id = (int)(long)DbManager
					.executeInsert(new SqlQuery(
							"insert into players (username,password,email,facebook_id) values (?,?,?,?)")
							.addParameter(username, Types.VARCHAR)
							.addParameter(Utils.digest(password), Types.VARCHAR)
							.addParameter(email, Types.VARCHAR)
							.addParameter(facebookId, Types.VARCHAR));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id > 0;
	}

	public static Player get(int id) {
		return select(new SqlQuery("select * from players where id = " + id));
	}

	public static Player findByUsername(String username) {
		return select(new SqlQuery("select * from players where username = ?")
				.addParameter(username, Types.VARCHAR));
	}

	public static Player findByFacebookId(String facebookId) {
		return select(new SqlQuery(
				"select * from players where facebook_id = ?").addParameter(
				facebookId, Types.VARCHAR));
	}

	private static Player select(SqlQuery sql) {
		DbManager man = null;
		ResultSet rs = null;
		try {
			man = new DbManager();
			rs = man.select(sql);

			return Player.fromResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (man != null)
				man.close(rs);
		}
		return null;
	}

	private static Player fromResultSet(ResultSet rs) throws SQLException {
		if (!rs.next())
			return null;

		Player player = new Player();
		
		player.id = rs.getInt("id");
		player.username = rs.getString("username");
		player.email = rs.getString("email");
		player.facebookId = rs.getString("facebook_id");

		return player;
	}
	
	public String toJson() {
		return String.format("{ id: %d, username: '%s', email: '%s', facebookId: '%s' }", 
				id, username, email, facebookId);
	}
}
