package com.turpgames.ichigu.server.test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.turpgames.db.DbManager;
import com.turpgames.ichigu.db.Db;
import com.turpgames.ichigu.db.IchiguConnectionProvider;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.servlet.handlers.GetLeadersBoardActionHandler;
import com.turpgames.utils.Util;

public class TestMain {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSSZ");

	public static void main(String[] args) {
		try {
			DbManager.setConnectionProvider(new IchiguConnectionProvider());

			prepareFakeUserScripts();
			
			// testEncoding();
			// insertRandomData();
			// displayHiscores();

			System.out.println("OK!");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	protected static void prepareFakeUserScripts() {
		File dir = new File("/Users/mehmet/Downloads/fb");
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getName().endsWith(".jpg");
			}
		};
		File[] jpgFiles = dir.listFiles(filter);

		List<String> facebookIds = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		List<String> surnames = new ArrayList<String>();
		
		for (File file : jpgFiles) {
			String fileName = file.getName();
			
			String[] ss = fileName.split("_");

			facebookIds.add(ss[0]);
			names.add(ss[1]);
			surnames.add(ss[2].substring(0, ss[2].length() - 4));
		}
		
		while (names.size() > 0) {
			int idIndex = Util.Random.randInt(facebookIds.size());
			int nameIndex = Util.Random.randInt(names.size());
			int surnameIndex = Util.Random.randInt(surnames.size());
			
			System.out.println(String.format("insert into players (username, password, email, facebook_id)"
					+ " values ('%s %s', '', '', '%s');", 
					names.get(nameIndex), 
					surnames.get(surnameIndex), 
					facebookIds.get(idIndex)));

			facebookIds.remove(idIndex);
			names.remove(nameIndex);
			surnames.remove(surnameIndex);
		}
	}

	protected static void testEncoding() {
		HttpURLConnection conn = null;
		try {
			String uri = String.format("http://localhost:8080/ichigu-server/ichigu?p=%s", URLEncoder.encode("������������", "utf-8"));

			URL url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true);
			conn.setDoOutput(false);

			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);

			String resp = Util.IO.readUtf8String(conn.getInputStream());
			System.out.println(resp);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	protected static void displayHiscores() throws IOException {
		System.out.println("Mini All Time");
		displayHiScores(getGeneralLeadersBoard(Score.ModeMini, Score.AllTime, 1));
		System.out.println("Mini Last Month");
		displayHiScores(getGeneralLeadersBoard(Score.ModeMini, Score.Monthly, 1));
		System.out.println("Mini Last Week");
		displayHiScores(getGeneralLeadersBoard(Score.ModeMini, Score.Weekly, 1));
		System.out.println("Mini Today");
		displayHiScores(getGeneralLeadersBoard(Score.ModeMini, Score.Daily, 1));

		System.out.println("Standard All Time");
		displayHiScores(getGeneralLeadersBoard(Score.ModeStandard, Score.AllTime, 4));
		System.out.println("Standard Last Month");
		displayHiScores(getGeneralLeadersBoard(Score.ModeStandard, Score.Monthly, 1));
		System.out.println("Standard Last Week");
		displayHiScores(getGeneralLeadersBoard(Score.ModeStandard, Score.Weekly, 1));
		System.out.println("Standard Today");
		displayHiScores(getGeneralLeadersBoard(Score.ModeStandard, Score.Daily, 1));

		System.out.println("Time All Time");
		displayHiScores(getGeneralLeadersBoard(Score.ModeTime, Score.AllTime, 1));
		System.out.println("Time Last Month");
		displayHiScores(getGeneralLeadersBoard(Score.ModeTime, Score.Monthly, 1));
		System.out.println("Time Last Week");
		displayHiScores(getGeneralLeadersBoard(Score.ModeTime, Score.Weekly, 1));
		System.out.println("Time Today");
		displayHiScores(getGeneralLeadersBoard(Score.ModeTime, Score.Daily, 1));

		System.out.println("Mini All Time For Player 1");
		displayHiScores(getMyHiScores(Score.ModeMini, Score.AllTime, 1));
		System.out.println("Mini Last Month For Player 1");
		displayHiScores(getMyHiScores(Score.ModeMini, Score.Monthly, 1));
		System.out.println("Mini Last Week For Player 1");
		displayHiScores(getMyHiScores(Score.ModeMini, Score.Weekly, 1));
		System.out.println("Mini Today For Player 1");
		displayHiScores(getMyHiScores(Score.ModeMini, Score.Daily, 1));

		System.out.println("Standard All Time For Player 1");
		displayHiScores(getMyHiScores(Score.ModeStandard, Score.AllTime, 1));
		System.out.println("Standard Last Month For Player 1");
		displayHiScores(getMyHiScores(Score.ModeStandard, Score.Monthly, 1));
		System.out.println("Standard Last Week For Player 1");
		displayHiScores(getMyHiScores(Score.ModeStandard, Score.Weekly, 1));
		System.out.println("Standard Today For Player 1");
		displayHiScores(getMyHiScores(Score.ModeStandard, Score.Daily, 1));

		System.out.println("Time All Time For Player 1");
		displayHiScores(getMyHiScores(Score.ModeTime, Score.AllTime, 1));
		System.out.println("Time Last Month For Player 1");
		displayHiScores(getMyHiScores(Score.ModeTime, Score.Monthly, 1));
		System.out.println("Time Last Week For Player 1");
		displayHiScores(getMyHiScores(Score.ModeTime, Score.Weekly, 1));
		System.out.println("Time Today For Player 1");
		displayHiScores(getMyHiScores(Score.ModeTime, Score.Daily, 1));

		System.out.println("Standard Last Week Friends of Player 1");
		displayHiScores(getFriendsHiScores(Score.ModeStandard, Score.Weekly, 1, "718801914", "563625624"));		
		System.out.println("Standard Last Week Friends of Player 3");
		displayHiScores(getFriendsHiScores(Score.ModeStandard, Score.Weekly, 3, "600163886", "563625624"));
	}

	protected static LeadersBoard getGeneralLeadersBoard(int mode, int days, int playerId) {
		return GetLeadersBoardActionHandler.getGeneralLeadersBoard(mode, days, playerId);
	}

	protected static LeadersBoard getMyHiScores(int mode, int days, int playerId) {
		return GetLeadersBoardActionHandler.getMyHiScores(mode, days, playerId);
	}

	protected static LeadersBoard getFriendsHiScores(int mode, int days, int playerId, String... facebookIds) throws IOException {
		return GetLeadersBoardActionHandler.getFriendsHiScores(mode, days, playerId, facebookIds);
	}

	protected static void displayHiScores(LeadersBoard leadersBoard) {
		System.out.println("----------------------------------------");
		System.out.println(Util.Strings.padRight("Rank", 10) + Util.Strings.padRight("Player", 20) + Util.Strings.padRight("Score", 20) + "Date");
		System.out.println("----------------------------------------");
		
		int i = 1;
		for (Score hiscore : leadersBoard.getScores()) {
			Player player = leadersBoard.getPlayer(hiscore.getPlayerId());

			System.out.println(
					Util.Strings.padRight("" + i++ + ".", 10) +
							Util.Strings.padRight(player.getUsername(), 20) +
							Util.Strings.padRight("" + hiscore.getScore(), 10) +
							sdf.format(hiscore.getDate()));
		}
		
		if (leadersBoard.getOwnScore() != null && leadersBoard.getOwnRank() > 0) {
			Score hiscore = leadersBoard.getOwnScore();
			Player player = Db.Players.get(hiscore.getPlayerId());
			
			System.out.println(
					Util.Strings.padRight("" + leadersBoard.getOwnRank() + ".", 10) +
							Util.Strings.padRight(player.getUsername(), 20) +
							Util.Strings.padRight("" + hiscore.getScore(), 10) +
							sdf.format(hiscore.getDate()));
		}
		System.out.println();
	}

	protected static void insertRandomData() {
		System.out.println("inserting players...");

		for (int i = 0; i < 100; i++) {
			int r = Util.Random.randInt(100000);
			Player p = new Player();
			p.setEmail("mail" + r + "@mail.com");
			p.setUsername("player" + r);
			p.setFacebookId("" + r);
			p.setPassword("" + r);
			Db.Players.insert(p);
		}

		System.out.println("inserting scores...");

		for (int i = 0; i < 5000; i++) {
			int r = Util.Random.randInt(1, 101);
			Score s = new Score();
			s.setPlayerId(r);
			s.setMode(Util.Random.randInt(1, 4));
			s.setScore(Util.Random.randInt(10, 10000));
			Calendar time = Calendar.getInstance();
			time.add(Calendar.HOUR, Util.Random.randInt(-40 * 24, 1));
			s.setTime(time.getTimeInMillis());
			Db.Scores.insert(s);
		}
	}
}
