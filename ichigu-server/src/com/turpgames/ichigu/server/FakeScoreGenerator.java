package com.turpgames.ichigu.server;
import com.turpgames.ichigu.db.Db;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.utils.Util;

public class FakeScoreGenerator {
	private final static long insertInterval = (24 * 60 * 60 * 1000) / ServerConfig.getFakeScoresPerDay(); // 10 scores per day
	private final static int minFakeUserId = ServerConfig.getMinFakeUserId();
	private final static int maxFakeUserId = ServerConfig.getMaxFakeUserId();
	private final static int minMiniChallengeScore = ServerConfig.getMinMiniChallengeScore();
	private final static int maxMiniChallengeScore = ServerConfig.getMaxMiniChallengeScore();
	private final static int minStandardScore = ServerConfig.getMinStandardScore();
	private final static int maxStandardScore = ServerConfig.getMaxStandardScore();
	private final static int minTimeChallengeScore = ServerConfig.getMinTimeChallengeScore();
	private final static int maxTimeChallengeScore = ServerConfig.getMaxTimeChallengeScore();

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
		insertFakeScore(Score.ModeMini, minMiniChallengeScore, maxMiniChallengeScore);
	}

	private static void insertFakeStandardModeScore() {
		insertFakeScore(Score.ModeStandard, minStandardScore, maxStandardScore);
	}

	private static void insertFakeTimeChallengeScore() {
		insertFakeScore(Score.ModeTime, minTimeChallengeScore, maxTimeChallengeScore);
	}

	private static void insertFakeScore(int mode, int min, int max) {
		Score score = new Score();
		
		score.setMode(mode);
		score.setPlayerId(getRandom(minFakeUserId, maxFakeUserId));
		score.setTime(System.currentTimeMillis());
		score.setScore(getRandom(min, max));
		
		Db.Scores.insert(score);
	}

	private static int getRandom(int min, int max) {
		return Util.Random.randInt(min, max + 1);
	}
}
