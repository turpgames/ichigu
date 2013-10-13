package com.blox.ichigu.model;

import com.blox.framework.v0.impl.Settings;
import com.blox.framework.v0.impl.Text;
import com.blox.framework.v0.util.Timer;
import com.blox.ichigu.utils.R;

public class ChallengeMode extends FullGameMode {
	private static final int maxTimeToFindIchigu = 100;

	private int totalScore;
	private Timer challengeTimer;
	private boolean extraCardsOpened;

	private GameInfo resultInfo;
	private GameInfo scoreInfo;

	public ChallengeMode() {
		hint.deactivate();

		challengeTimer = new Timer();

		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		scoreInfo = new GameInfo();
		scoreInfo.locate(Text.HAlignLeft, Text.VAlignTop);
		scoreInfo.setPadding(7, 90);
		
		updateScoreText();
	}

	private void updateScoreText() {
		scoreInfo.setText("Score: " + totalScore);
	}

	@Override
	public void startMode() {
		super.startMode();
		totalScore = 0;
		challengeTimer.restart();
		updateScoreText();
	}

	@Override
	public void endMode() {
		super.endMode();
		challengeTimer.stop();
		int challengeHiScore = Settings.getInteger(R.settings.hiscores.challenge, 0);
		if (totalScore > challengeHiScore)
			Settings.putInteger(R.settings.hiscores.challenge, totalScore);

		resultInfo.setText(
				"Congratulations,\n" +
						String.format("You found %d ichigu%s!\n", ichigusFound, ichigusFound != 1 ? "s" : "") +
						"Total Time " + modeCompleteTime + "\n" +
						scoreInfo.getText() + "\n" +
						"Touch Screen\nTo Continue");
	}

	@Override
	public void exitMode() {
		super.exitMode();
		totalScore = 0;
		challengeTimer.stop();
	}

	@Override
	public void drawGame() {
		drawScore();
		super.drawGame();
	}

	@Override
	public void drawResult() {
		resultInfo.draw();
	}

	@Override
	protected int checkIchigu() {
		int score = super.checkIchigu();
		if (score > 0) {
			float elapsed = challengeTimer.getElapsedTime();
			if (elapsed > maxTimeToFindIchigu)
				elapsed = maxTimeToFindIchigu;
			totalScore += (int) ((score * ((maxTimeToFindIchigu - elapsed) / 10) * (extraCardsOpened ? 0.5f : 1)));
			updateScoreText();
			challengeTimer.restart();
			extraCardsOpened = false;
		}
		return score;
	}

	@Override
	protected void openExtraCards() {
		super.openExtraCards();
		extraCardsOpened = true;
	}

	protected void drawScore() {
		scoreInfo.draw();
	}
}
