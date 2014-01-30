package com.turpgames.ichigu.utils;

import java.util.ArrayList;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.utils.Util;

public class ScoreManager {

	public static interface ILeadersBoardCallback {
		void onSuccess(LeadersBoard leadersBoard);

		void onFail(Throwable t);
	}

	public final static ScoreManager instance = new ScoreManager();

	private int scoreToSendIndex;
	private ArrayList<Score> scoresToSend;

	private ScoreManager() {
		loadScoresToSend();
	}

	public void sendScore(int mode, int score) {
		addScore(mode, score);
		if (Facebook.isLoggedIn())
			sendScores();
	}

	public void getLeadersBoard(int mode, int days, int whose, ILeadersBoardCallback callback) {
		IchiguClient.getLeadersBoard(mode, days, whose, callback);
	}

	public void sendScores() {
		Util.Threading.runInBackground(new Runnable() {
			@Override
			public void run() {
				sendScoresThread();
			}
		});
	}

	private void sendScoresThread() {
		try {
			loadScoresToSend();
			addOldHiScores();
			scoreToSendIndex = 0;
			sendNextScore();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private synchronized void sendNextScore() {
		if (scoresToSend.size() == 0 || scoreToSendIndex >= scoresToSend.size())
			return;

		Score score = scoresToSend.get(scoreToSendIndex);
		scoreToSendIndex++;
		sendScoreImpl(score);
	}

	private synchronized void sendScoreImpl(final Score score) {
		IchiguClient.sendScore(score.getMode(), score.getScore(), new ICallback() {
			@Override
			public void onSuccess() {
				removeScore(score);
				scoreToSendIndex--;
				sendNextScore();
			}

			@Override
			public void onFail(Throwable t) {
				sendNextScore();
			}
		});
	}

	private synchronized void addScore(int mode, int score) {
		Score scr = new Score();
		scr.setMode(mode);
		scr.setScore(score);

		scoresToSend.add(scr);
		saveScoresToSend();
	}

	private synchronized void removeScore(Score score) {
		scoresToSend.remove(score);
		saveScoresToSend();
	}

	private synchronized void saveScoresToSend() {
		IchiguSettings.setScoresToSend(scoresToSend);
	}

	private synchronized void loadScoresToSend() {
		scoresToSend = IchiguSettings.getScoresToSend();
	}

	private void addOldHiScores() {
		final String flagSettingsKey = "old-hiscores-sent-to-server";

		boolean scoreAlreadySent = Settings.getBoolean(flagSettingsKey, false);
		if (scoreAlreadySent)
			return;

		addOldHiScore(Score.ModeMini, R.settings.hiscores.miniChallenge);
		addOldHiScore(Score.ModeStandard, R.settings.hiscores.standard);
		addOldHiScore(Score.ModeTime, R.settings.hiscores.timeChallenge);

		Settings.putBoolean(flagSettingsKey, true);
	}

	private void addOldHiScore(int mode, String settingsKey) {
		int score = Settings.getInteger(settingsKey, 0);
		if (score > 0)
			addScore(mode, score);
	}
}
