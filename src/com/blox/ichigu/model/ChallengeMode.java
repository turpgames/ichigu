package com.blox.ichigu.model;

import com.blox.framework.v0.impl.Settings;
import com.blox.framework.v0.util.TextDrawer;
import com.blox.framework.v0.util.Timer;
import com.blox.ichigu.utils.R;

public class ChallengeMode extends FullGameMode {
	private static final int maxTimeToFindIchigu = 100;

	private int totalScore;
	private String scoreText;
	private Timer challengeTimer;
	private boolean extraCardsOpened;

	public ChallengeMode() {
		hint.deactivate();
		updateScoreText();

		challengeTimer = new Timer();
	}

	private void updateScoreText() {
		scoreText = "Score: " + totalScore;
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
		info.draw("Congratulations,", TextDrawer.AlignCentered, 200);
		info.draw(String.format("You found %d ichigu%s!", ichigusFound, ichigusFound != 1 ? "s" : ""), TextDrawer.AlignCentered, 150);
		info.draw("Total Time " + modeCompleteTime, TextDrawer.AlignCentered, 50);
		info.draw(scoreText, TextDrawer.AlignCentered, 0);
		info.draw("Touch Screen", TextDrawer.AlignCentered, -100);
		info.draw("To Continue", TextDrawer.AlignCentered, -150);
	}

	@Override
	protected int checkIchigu() {
		int score = super.checkIchigu();
		if (score > 0) {
			float elapsed = challengeTimer.getElapsedTime();
			if (elapsed > maxTimeToFindIchigu)
				elapsed = maxTimeToFindIchigu;
			totalScore += (int) ((score * ((maxTimeToFindIchigu - elapsed) / 10) * (extraCardsOpened ? 0.5f : 1)));
			scoreText = "Score: " + totalScore;
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
		info.draw(scoreText, TextDrawer.AlignNW, -60);
	}
}
