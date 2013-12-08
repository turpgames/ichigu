package com.turpgames.ichigu.server.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.turpgames.framework.v0.db.DbManager;
import com.turpgames.framework.v0.db.SqlQuery;

public class Score {
	public static final int ModeMini = 1;
	public static final int ModeStandard = 2;
	public static final int ModeTime = 3;

	private int playerId;
	private int score;
	private int mode;
	private Date time;
	private String username;

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean insert() {
		try {
			DbManager.executeInsert(new SqlQuery(
							"insert into scores (player_id,mode,score,time) values (?,?,?,?)")
							.addParameter(playerId, Types.INTEGER)
							.addParameter(mode, Types.INTEGER)
							.addParameter(score, Types.INTEGER)
							.addParameter(time, Types.TIMESTAMP));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public static List<Score> getHiScores(int mode) {
		return getHiScores(mode, -1);
	}

	public static List<Score> getHiScores(int mode, int playerId) {
		SqlQuery sql = new SqlQuery(
				"select s.*, p.username from scores s, players p where s.player_id = p.id and mode = "
						+ mode);

		if (playerId > 0)
			sql.append(" and p.id = " + playerId);

		sql.append(" order by score");

		if (mode != ModeStandard)
			sql.append(" desc");

		sql.append(" limit 10");

		List<Score> hiscores = new ArrayList<Score>();

		DbManager man = null;
		ResultSet rs = null;
		try {
			man = new DbManager();
			rs = man.select(sql);
			
			Score hiscore = null;			
			while ((hiscore = fromResultSet(rs)) != null)
				hiscores.add(hiscore);			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (man != null)
				man.close(rs);
		}

		return hiscores;
	}

	private static Score fromResultSet(ResultSet rs) throws SQLException {
		if (!rs.next())
			return null;

		Score score = new Score();

		score.playerId = rs.getInt("player_id");
		score.mode = rs.getInt("mode");
		score.score = rs.getInt("score");
		score.time = rs.getDate("time");
		score.username = rs.getString("username");

		return score;
	}
}
