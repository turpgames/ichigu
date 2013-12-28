package com.turpgames.ichigu.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;

import com.turpgames.db.DbManager;
import com.turpgames.db.IJson;
import com.turpgames.db.SqlQuery;

public class Score implements IJson {
	public static final int ModeMini = 1;
	public static final int ModeStandard = 2;
	public static final int ModeTime = 3;

	private int playerId;
	private int mode;
	private int score;
	private long time;

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal.getTime();
	}

	public boolean insert() {
		try {
			DbManager.executeInsert(new SqlQuery(
					"insert into scores (player_id,mode,score,time) values (?,?,?,?)")
					.addParameter(playerId, Types.INTEGER)
					.addParameter(mode, Types.INTEGER)
					.addParameter(score, Types.INTEGER)
					.addParameter(time, Types.BIGINT));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Score fromResultSet(ResultSet rs) throws SQLException {
		if (!rs.next())
			return null;

		Score score = new Score();

		score.playerId = rs.getInt("player_id");
		score.mode = rs.getInt("mode");
		score.score = rs.getInt("score");
		score.time = rs.getLong("time");

		return score;
	}

	public String toJson() {
		return JsonEncoders.score.encode(this);
	}

	public static Score fromJson(String json) {
		return JsonEncoders.score.decode(json);
	}
}
