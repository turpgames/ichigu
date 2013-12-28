package com.turpgames.ichigu.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.turpgames.db.DbManager;
import com.turpgames.db.IJson;
import com.turpgames.db.SqlQuery;

public class HiScore implements IJson {
	private Score score;
	private Player player;

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public static List<HiScore> getHiScores(int mode) {
		return getHiScores(mode, -1, -1);
	}

	public static List<HiScore> getHiScoresOfTime(int mode, int days) {
		return getHiScores(mode, days, -1);
	}

	public static List<HiScore> getHiScoresOfPlayer(int mode, int playerId) {
		return getHiScores(mode, -1, playerId);
	}
			
	public static List<HiScore> getHiScores(int mode, int days, int playerId) {
		SqlQuery sql = new SqlQuery("select * from scores where mode = " + mode);
		
		if (days > 0) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -days);

			sql.append(" and time > ?")
					.addParameter(cal.getTimeInMillis(), Types.BIGINT);
		}
		
		if (playerId > 0) {
			sql.append(" and player_id = " + playerId);
		}

		sql.append(" order by score");

		if (mode != Score.ModeStandard)
			sql.append(" desc");
		
		sql.append(", time");

		sql.append(" limit 10");

		List<HiScore> hiscores = new ArrayList<HiScore>();
		
		Player player = null;		
		if (playerId > 0)
			player = Player.get(playerId);

		DbManager man = null;
		ResultSet rs = null;
		try {
			man = new DbManager();
			rs = man.select(sql);

			Score score = null;
			while ((score = Score.fromResultSet(rs)) != null) {
				HiScore hiscore = new HiScore();
				hiscore.score = score;
				
				if (playerId > 0)
					hiscore.player = player;
				else
					hiscore.player = Player.get(score.getPlayerId());
				
				hiscores.add(hiscore);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (man != null)
				man.close(rs);
		}

		return hiscores;
	}
	
	public String toJson() {
		return JsonEncoders.hiscore.encode(this);
	}
	
	public static HiScore fromJson(String json) {
		return JsonEncoders.hiscore.decode(json);
	}
}
