package com.turpgames.ichigu.server.test;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.turpgames.db.DbManager;
import com.turpgames.ichigu.db.Db;
import com.turpgames.ichigu.db.IchiguConnectionProvider;
import com.turpgames.ichigu.db.IchiguHiScores;
import com.turpgames.ichigu.entity.HiScore;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.servlet.Utils;
import com.turpgames.utils.Util;

public class TestMain {
	public static void main(String[] args) {
		DbManager.setConnectionProvider(new IchiguConnectionProvider());
		
		testEncoding();
		// insertRandomData();
		// displayHiscores();

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
		displayHiScores(IchiguHiScores.miniModeAllTime());
		System.out.println("Mini Last Month");
		displayHiScores(IchiguHiScores.miniModeLastMonth());
		System.out.println("Mini Last Week");
		displayHiScores(IchiguHiScores.miniModeLastWeek());
		System.out.println("Mini Today");
		displayHiScores(IchiguHiScores.miniModeToday());

		System.out.println("Standard All Time");
		displayHiScores(IchiguHiScores.standartModeAllTime());
		System.out.println("Standard Last Month");
		displayHiScores(IchiguHiScores.standartModeLastMonth());
		System.out.println("Standard Last Week");
		displayHiScores(IchiguHiScores.standartModeLastWeek());
		System.out.println("Standard Today");
		displayHiScores(IchiguHiScores.standartModeToday());

		System.out.println("Time All Time");
		displayHiScores(IchiguHiScores.timeModeAllTime());
		System.out.println("Time Last Month");
		displayHiScores(IchiguHiScores.timeModeLastMonth());
		System.out.println("Time Last Week");
		displayHiScores(IchiguHiScores.timeModeLastWeek());
		System.out.println("Time Today");
		displayHiScores(IchiguHiScores.timeModeToday());

		System.out.println("Mini All Time For Player 42");
		displayHiScores(IchiguHiScores.miniModeAllTime(42));
		System.out.println("Mini Last Month For Player 42");
		displayHiScores(IchiguHiScores.miniModeLastMonth(42));
		System.out.println("Mini Last Week For Player 42");
		displayHiScores(IchiguHiScores.miniModeLastWeek(42));
		System.out.println("Mini Today For Player 42");
		displayHiScores(IchiguHiScores.miniModeToday(42));

		System.out.println("Standard All Time For Player 42");
		displayHiScores(IchiguHiScores.standartModeAllTime(42));
		System.out.println("Standard Last Month For Player 42");
		displayHiScores(IchiguHiScores.standartModeLastMonth(42));
		System.out.println("Standard Last Week For Player 42");
		displayHiScores(IchiguHiScores.standartModeLastWeek(42));
		System.out.println("Standard Today For Player 42");
		displayHiScores(IchiguHiScores.standartModeToday(42));

		System.out.println("Time All Time For Player 42");
		displayHiScores(IchiguHiScores.timeModeAllTime(42));
		System.out.println("Time Last Month For Player 42");
		displayHiScores(IchiguHiScores.timeModeLastMonth(42));
		System.out.println("Time Last Week For Player 42");
		displayHiScores(IchiguHiScores.timeModeLastWeek(42));
		System.out.println("Time Today For Player 42");
		displayHiScores(IchiguHiScores.timeModeToday(42));
	}

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSSZ");

	private static void displayHiScores(List<HiScore> hiscores) {
		System.out.println("----------------------------------------");
		System.out.println(Utils.padRight("Rank", 10) + Utils.padRight("Player", 20) + Utils.padRight("Score", 20) + "Date");
		System.out.println("----------------------------------------");
		int i = 1;
		for (HiScore hiscore : hiscores) {
			System.out.println(
					Utils.padRight("" + i++ + ".", 10) +
							Utils.padRight(hiscore.getPlayer().getUsername(), 20) +
							Utils.padRight("" + hiscore.getScore().getScore(), 10) +
							sdf.format(hiscore.getScore().getDate()));
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
			int r = Utils.randInt(100000);
			Player p = new Player();
			p.setEmail("mail" + r + "@mail.com");
			p.setUsername("player" + r);
			p.setFacebookId("" + r);
			p.setPassword("" + r);
			Db.Players.insert(p);
		}

		System.out.println("inserting scores...");

		for (int i = 0; i < 5000; i++) {
			int r = Utils.randInt(1, 101);
			Score s = new Score();
			s.setPlayerId(r);
			s.setMode(Utils.randInt(1, 4));
			s.setScore(Utils.randInt(10, 10000));
			Calendar time = Calendar.getInstance();
			time.add(Calendar.HOUR, Utils.randInt(-40 * 24, 1));
			s.setTime(time.getTimeInMillis());
			Db.Scores.insert(s);
		}
	}
}
