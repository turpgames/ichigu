package com.turpgames.ichigu.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.turpgames.db.DbManager;
import com.turpgames.db.SqlQuery;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.utils.Util;

public final class Db {
	private Db() {
	}

	public static final class Players {
		public static boolean insert(Player player) {
			try {
				int id = (int) DbManager
						.executeInsert(new SqlQuery(
								"insert into players (username,password,email,facebook_id) values (?,?,?,?)")
								.addParameter(player.getUsername(), Types.VARCHAR)
								.addParameter(Util.Crypto.digest(player.getPassword()), Types.VARCHAR)
								.addParameter(player.getEmail(), Types.VARCHAR)
								.addParameter(player.getFacebookId(), Types.VARCHAR));

				player.setId(id);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return player.getId() > 0;
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

				return fromResultSet(rs);
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

			player.setId(rs.getInt("id"));
			player.setUsername(rs.getString("username"));
			player.setEmail(rs.getString("email"));
			player.setFacebookId(rs.getString("facebook_id"));

			return player;
		}
	}

	public static final class LeadersBoards {
		public static LeadersBoard getLeadersBoard(int mode, int days, int playerId) {
			SqlQuery sql = prepareSql(mode, days, playerId);

			List<Player> players = new ArrayList<Player>();
			List<Score> scores = new ArrayList<Score>();

			DbManager man = null;
			ResultSet rs = null;
			try {
				man = new DbManager();
				rs = man.select(sql);

				Score score = null;
				while ((score = Scores.fromResultSet(rs)) != null) {
					if (addPlayer(players, score.getPlayerId()))
						scores.add(score);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (man != null)
					man.close(rs);
			}

			LeadersBoard LeadersBoard = new LeadersBoard();
			
			LeadersBoard.setScoreMode(mode);
			LeadersBoard.setDays(days);
			LeadersBoard.setPlayerId(playerId);

			LeadersBoard.setPlayers(players.toArray(new Player[0]));
			LeadersBoard.setScores(scores.toArray(new Score[0]));

			return LeadersBoard;
		}

		private static SqlQuery prepareSql(int mode, int days, int playerId) {
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
			return sql;
		}

		private static boolean addPlayer(List<Player> players, int playerId) {
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getId() == playerId) {
					return true;
				}
			}

			Player player = Players.get(playerId);

			if (player == null)
				return false;

			players.add(player);
			return true;
		}
	}

	public final static class Scores {
		public static boolean insert(Score score) {
			try {
				DbManager.executeInsert(new SqlQuery(
						"insert into scores (player_id,mode,score,time) values (?,?,?,?)")
						.addParameter(score.getPlayerId(), Types.INTEGER)
						.addParameter(score.getMode(), Types.INTEGER)
						.addParameter(score.getScore(), Types.INTEGER)
						.addParameter(score.getTime(), Types.BIGINT));
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

			score.setPlayerId(rs.getInt("player_id"));
			score.setMode(rs.getInt("mode"));
			score.setScore(rs.getInt("score"));
			score.setTime(rs.getLong("time"));

			return score;
		}
	}
}