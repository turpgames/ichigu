package com.turpgames.ichigu.server;

import com.turpgames.ichigu.db.Db;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.utils.Util;

public class FakeScoreGenerator {
	private final static long insertInterval = (24 * 60 * 60 * 1000) / 10; // 10 scores per day
	private final static int minFakeUserId = 1;
	private final static int maxFakeUserId = 736;
	private final static int minMiniChallengeScore = 1;
	private final static int maxMiniChallengeScore = 40;
	private final static int minStadardScore = 300;
	private final static int maxStadardScore = 1500;
	private final static int minTimeChallengeScore = 1;
	private final static int maxTimeChallengeScore = 20;

	private static Thread thread;

	public static void start() {
		if (!ServerConfig.isFakeScoreGeneratorEnabled())
			return;
		
		if (thread != null && thread.isAlive())
			return;

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});
		
		thread.start();
	}

	private static void process() {
		try {
			while (true) {
				Util.Threading.threadSleep(insertInterval);
				insertFakeScores();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static void insertFakeScores() {
		insertFakeMiniChallengeScore();
		insertFakeStandardModeScore();
		insertFakeTimeChallengeScore();
	}

	private static void insertFakeMiniChallengeScore() {
		Score score = createScore(Score.ModeMini, minMiniChallengeScore, maxMiniChallengeScore);
		Db.Scores.insert(score);
	}

	private static void insertFakeStandardModeScore() {
		Score score = createScore(Score.ModeStandard, minStadardScore, maxStadardScore);
		Db.Scores.insert(score);
	}

	private static void insertFakeTimeChallengeScore() {
		Score score = createScore(Score.ModeTime, minTimeChallengeScore, maxTimeChallengeScore);
		Db.Scores.insert(score);
	}

	private static Score createScore(int mode, int min, int max) {
		Score score = new Score();
		score.setMode(mode);
		score.setPlayerId(getRandom(minFakeUserId, maxFakeUserId));
		score.setTime(System.currentTimeMillis());
		score.setScore(getRandom(min, max));
		return score;
	}

	private static int getRandom(int min, int max) {
		return Util.Random.randInt(min, max + 1);
	}
}
