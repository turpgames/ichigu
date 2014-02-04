package com.turpgames.ichigu.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.turpgames.db.DbManager;
import com.turpgames.db.IEntityFactory;
import com.turpgames.db.SqlQuery;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.utils.Util;

public final class Db {
	private Db() {
	}

	public static final class Players {
		public final static IEntityFactory<Player> factory = new IEntityFactory<Player>() {
			@Override
			public Player create(ResultSet rs) throws SQLException {
				Player player = new Player();

				player.setId(rs.getInt("id"));
				player.setUsername(rs.getString("username"));
				player.setEmail(rs.getString("email"));
				player.setFacebookId(rs.getString("facebook_id"));

				return player;
			};
		};

		public static boolean insert(Player player) {
			try {
				int id = (Integer) DbManager
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
			return select(new SqlQuery("select * from players where username = ?").addParameter(username, Types.VARCHAR));
		}

		public static Player findByFacebookId(String facebookId) {
			return select(new SqlQuery(
					"select * from players where facebook_id = ?").addParameter(
					facebookId, Types.VARCHAR));
		}

		public static Player[] selectPlayersById(Integer[] playerIds) {
			if (playerIds.length == 0)
				return new Player[0];

			SqlQuery sql = new SqlQuery("select * from players where id in (");

			for (int i = 0; i < playerIds.length; i++) {
				sql.append("?");
				sql.addParameter(playerIds[i], Types.INTEGER);
				if (i < playerIds.length - 1)
					sql.append(",");
			}

			sql.append(")");

			return selectMultiple(sql);
		}

		public static Player[] selectPlayersByFacebookId(String[] facebookIds) {
			if (facebookIds.length == 0)
				return new Player[0];

			SqlQuery sql = new SqlQuery("select * from players where facebook_id in (");

			for (int i = 0; i < facebookIds.length; i++) {
				sql.append("?");
				sql.addParameter(facebookIds[i], Types.INTEGER);
				if (i < facebookIds.length - 1)
					sql.append(",");
			}

			sql.append(")");

			return selectMultiple(sql);
		}

		private static Player select(SqlQuery sql) {
			try {
				return DbManager.executeSelectSingle(sql, factory);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

		private static Player[] selectMultiple(SqlQuery sql) {
			try {
				List<Player> players = DbManager.executeSelectList(sql, factory);
				return players.toArray(new Player[0]);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return new Player[0];
		}
	}

	public static final class LeadersBoards {
		public static LeadersBoard getLeadersBoard(int mode, int days, int playerId, int limit) {
			SqlQuery sql = prepareSql(mode, days, playerId, limit);

			List<Score> scores;
			try {
				scores = DbManager.executeSelectList(sql, Scores.factory);
			} catch (SQLException e) {
				e.printStackTrace();
				return new LeadersBoard();
			}

			List<Integer> playerIdsToSelect = new ArrayList<Integer>();

			for (Score score : scores) {
				if (!playerIdsToSelect.contains(score.getPlayerId()))
					playerIdsToSelect.add(score.getPlayerId());
			}
			
			Player[] players = Players.selectPlayersById(playerIdsToSelect.toArray(new Integer[0]));

			return createLeadersBoard(mode, days, playerId, scores, players);
		}

		public static LeadersBoard getFriendsLeadersBoard(int mode, int days, int playerId, int limit, String[] facebookIds) {
			SqlQuery sql = prepareSql(mode, days, facebookIds, limit);

			List<Score> scores;
			try {
				scores = DbManager.executeSelectList(sql, Scores.factory);
			} catch (SQLException e) {
				e.printStackTrace();
				return new LeadersBoard();
			}

			List<Integer> playerIdsToSelect = new ArrayList<Integer>();

			for (Score score : scores) {
				if (!playerIdsToSelect.contains(score.getPlayerId()))
					playerIdsToSelect.add(score.getPlayerId());
			}
			
			Player[] players = Players.selectPlayersById(playerIdsToSelect.toArray(new Integer[0]));

			return createLeadersBoard(mode, days, playerId, scores, players);
		}

		public static int getRank(int mode, int days, int score) {			
			SqlQuery sql = new SqlQuery(String.format("select count(*) + 1 rank from (select * from scores where score %s %d",
					mode == Score.ModeStandard ? "<" : ">", score));

			sql.append(" and mode = " + mode);

			if (days > 0) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -days);

				sql.append(" and time > ?").addParameter(cal.getTimeInMillis(), Types.BIGINT);
			}

			sql.append(" order by score");

			if (mode != Score.ModeStandard)
				sql.append(" desc");

			sql.append(", time) ss");

			Integer res = null;
			try {
				res = DbManager.executeSelectSingle(sql, new IEntityFactory<Integer>() {
					@Override
					public Integer create(ResultSet rs) throws SQLException {
						return rs.getInt("rank");
					}
				});
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return res == null ? -1 : res.intValue();
		}

		public static int getRank(int mode, int days, int score, String[] facebookIds) {
			SqlQuery sql = new SqlQuery(String.format("select count(*) + 1 rank from (select s.* from scores s, players p where s.player_id = p.id and s.score %s %d",
					mode == Score.ModeStandard ? "<" : ">", score));

			sql.append(" and s.mode = " + mode);

			if (days > 0) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -days);

				sql.append(" and s.time > ?").addParameter(cal.getTimeInMillis(), Types.BIGINT);
			}
			
			sql.append(" and p.facebook_id in (");
			for (int i = 0; i < facebookIds.length; i++) {
				sql.append("?");
				sql.addParameter(facebookIds[i], Types.VARCHAR);
				if (i < facebookIds.length - 1)
					sql.append(",");
			}
			sql.append(")");
			
			sql.append(" order by s.score");

			if (mode != Score.ModeStandard)
				sql.append(" desc");

			sql.append(", s.time) ss");

			Integer res = null;
			try {
				res = DbManager.executeSelectSingle(sql, new IEntityFactory<Integer>() {
					@Override
					public Integer create(ResultSet rs) throws SQLException {
						return rs.getInt("rank");
					}
				});
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return res == null ? -1 : res.intValue();
		}

		public static Score getPlayersHiScore(int mode, int days, int playerId) {
			SqlQuery sql = prepareSql(mode, days, playerId, 1);

			try {
				return DbManager.executeSelectSingle(sql, Scores.factory);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

		private static LeadersBoard createLeadersBoard(int mode, int days, int playerId, List<Score> scores, Player[] players) {
			LeadersBoard LeadersBoard = new LeadersBoard();

			LeadersBoard.setScoreMode(mode);
			LeadersBoard.setDays(days);
			LeadersBoard.setPlayerId(playerId);

			LeadersBoard.setPlayers(players);
			LeadersBoard.setScores(scores.toArray(new Score[0]));

			return LeadersBoard;
		}

		private static SqlQuery prepareSql(int mode, int days, int playerId, int limit) {
			SqlQuery sql = new SqlQuery("select * from scores where mode = " + mode);

			if (days > 0) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -days);

				sql.append(" and time > ?").addParameter(cal.getTimeInMillis(), Types.BIGINT);
			}

			if (playerId > 0) {
				sql.append(" and player_id = " + playerId);
			}

			sql.append(" order by score");

			if (mode != Score.ModeStandard)
				sql.append(" desc");

			sql.append(", time");
			sql.append(" limit " + limit);
			return sql;
		}

		private static SqlQuery prepareSql(int mode, int days, String[] facebookIds, int limit) {
			SqlQuery sql = new SqlQuery("select s.* from scores s, players p where s.player_id = p.id and s.mode = " + mode);

			if (days > 0) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -days);

				sql.append(" and s.time > ?").addParameter(cal.getTimeInMillis(), Types.BIGINT);
			}

			sql.append(" and p.facebook_id in (");

			for (int i = 0; i < facebookIds.length; i++) {
				sql.append("?");
				sql.addParameter(facebookIds[i], Types.VARCHAR);
				if (i < facebookIds.length - 1)
					sql.append(",");
			}

			sql.append(")");

			sql.append(" order by s.score");

			if (mode != Score.ModeStandard)
				sql.append(" desc");

			sql.append(", s.time");
			sql.append(" limit " + limit);
			return sql;
		}
	}

	public final static class Scores {
		public final static IEntityFactory<Score> factory = new IEntityFactory<Score>() {
			@Override
			public Score create(ResultSet rs) throws SQLException {
				Score score = new Score();

				score.setPlayerId(rs.getInt("player_id"));
				score.setMode(rs.getInt("mode"));
				score.setScore(rs.getInt("score"));
				score.setTime(rs.getLong("time"));

				return score;
			};
		};

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
	}
}
