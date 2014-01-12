package com.turpgames.ichigu.server.test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.turpgames.db.DbManager;
import com.turpgames.ichigu.db.Db;
import com.turpgames.ichigu.db.IchiguConnectionProvider;
import com.turpgames.ichigu.db.LeadersBoardJsonCache;
import com.turpgames.ichigu.entity.JsonEncoders;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.utils.Util;

public class TestMain {
	private static int threadsToComplete = 1;
	
	public static void main(String[] args) {
		DbManager.setConnectionProvider(new IchiguConnectionProvider());
				
		// testEncoding();
		// insertRandomData();
		displayHiscores();

		System.out.println("OK!");
	}
	
	private static void testEncoding() {
		HttpURLConnection conn = null;
		try {
			String uri = String.format("http://localhost:8080/ichigu-server/ichigu?p=%s", URLEncoder.encode("ýðüþöçÖÇÞÝÐÜ", "utf-8"));
			
			URL url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true);
			conn.setDoOutput(false);

			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			
			String resp = Util.IO.readUtf8String(conn.getInputStream());
			System.out.println(resp);
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static void displayHiscores() {
		System.out.println("Mini All Time");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeMini, Score.AllTime, -1));
		System.out.println("Mini Last Month");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeMini, Score.Monthly, -1));
		System.out.println("Mini Last Week");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeMini, Score.Weekly, -1));
		System.out.println("Mini Today");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeMini, Score.Daily, -1));

		System.out.println("Standard All Time");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeStandard, Score.AllTime, -1));
		System.out.println("Standard Last Month");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeStandard, Score.Monthly, -1));
		System.out.println("Standard Last Week");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeStandard, Score.Weekly, -1));
		System.out.println("Standard Today");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeStandard, Score.Daily, -1));

		System.out.println("Time All Time");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeTime, Score.AllTime, -1));
		System.out.println("Time Last Month");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeTime, Score.Monthly, -1));
		System.out.println("Time Last Week");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeTime, Score.Weekly, -1));
		System.out.println("Time Today");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeTime, Score.Daily, -1));

		System.out.println("Mini All Time For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeMini, Score.AllTime, 1));
		System.out.println("Mini Last Month For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeMini, Score.Monthly, 1));
		System.out.println("Mini Last Week For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeMini, Score.Weekly, 1));
		System.out.println("Mini Today For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeMini, Score.Daily, 1));

		System.out.println("Standard All Time For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeStandard, Score.AllTime, 1));
		System.out.println("Standard Last Month For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeStandard, Score.Monthly, 1));
		System.out.println("Standard Last Week For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeStandard, Score.Weekly, 1));
		System.out.println("Standard Today For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeStandard, Score.Daily, 1));

		System.out.println("Time All Time For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeTime, Score.AllTime, 1));
		System.out.println("Time Last Month For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeTime, Score.Monthly, 1));
		System.out.println("Time Last Week For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeTime, Score.Weekly, 1));
		System.out.println("Time Today For Player 1");
		displayHiScores(LeadersBoardJsonCache.getLeadersBoard(Score.ModeTime, Score.Daily, 1));
	}

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSSZ");

	private static void displayHiScores(String LeadersBoardJson) {
		
		LeadersBoard LeadersBoard = JsonEncoders.leadersBoard.decode(LeadersBoardJson);
		
		System.out.println("----------------------------------------");
		System.out.println(Util.Strings.padRight("Rank", 10) + Util.Strings.padRight("Player", 20) + Util.Strings.padRight("Score", 20) + "Date");
		System.out.println("----------------------------------------");
		int i = 1;
		for (Score hiscore : LeadersBoard.getScores()) {
			Player player = LeadersBoard.getPlayer(hiscore.getPlayerId());
			
			System.out.println(
					Util.Strings.padRight("" + i++ + ".", 10) +
							Util.Strings.padRight(player.getUsername(), 20) +
							Util.Strings.padRight("" + hiscore.getScore(), 10) +
							sdf.format(hiscore.getDate()));
		}
		System.out.println();
	}

	private static String getModeName(int mode) {
		switch (mode) {
		case Score.ModeMini:
			return "Mini";
		case Score.ModeStandard:
			return "Standard";
		default:
			return "Time";
		}
	}

	private static void insertRandomData() {
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
