package com.turpgames.ichigu.server.test;

import java.util.Calendar;
import java.util.List;

import com.turpgames.framework.v0.db.DbManager;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.server.db.IchiguConnectionProvider;
import com.turpgames.ichigu.server.entity.Player;
import com.turpgames.ichigu.server.entity.Score;

public class TestMain {
	public static void main(String[] args) {
		DbManager.setConnectionProvider(new IchiguConnectionProvider());

		List<Score> hiscores = Score.getHiScores(Score.ModeTime);
		
		for (Score s:hiscores)
			System.out.println(s.getUsername() + " " + s.getScore());

		System.out.println("OK!");
	}

	private static void insertData() {
		System.out.println("inserting players...");

		for (int i = 0; i < 100; i++) {
			int r = Utils.randInt(100000);
			Player p = new Player();
			p.setEmail("mail" + r + "@mail.com");
			p.setUsername("player" + r);
			p.setFacebookId("" + r);
			p.setPassword("" + r);
			p.insert();
		}

		Calendar time = Calendar.getInstance();

		System.out.println("inserting scores...");

		for (int i = 0; i < 1000; i++) {
			int r = Utils.randInt(1, 101);
			Score s = new Score();
			s.setPlayerId(r);
			s.setMode(Utils.randInt(1, 4));
			s.setScore(Utils.randInt(10, 251));
			time.add(Calendar.SECOND, Utils.randInt(-500, 500));
			s.setTime(time.getTime());
			s.insert();
		}
	}
}
