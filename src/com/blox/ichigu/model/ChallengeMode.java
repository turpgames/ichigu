package com.blox.ichigu.model;

import com.blox.framework.v0.forms.xml.Dialog;
import com.blox.framework.v0.impl.Settings;
import com.blox.framework.v0.impl.Text;
import com.blox.framework.v0.util.Timer;
import com.blox.ichigu.utils.R;

public class ChallengeMode extends FullGameMode {
	private static final int maxTimeToFindIchigu = 100;

	private int totalScore;
	private Timer challengeTimer;
	private boolean deductScore;

	private GameInfo resultInfo;
	private GameInfo scoreInfo;

	private Dialog confirmExitDialog;
	private boolean isExitConfirmed;

	private IChallengeModeListener getChallengeModeListener() {
		return (IChallengeModeListener) super.modeListener;
	}

	public ChallengeMode() {
		hint.deactivate();

		challengeTimer = new Timer();

		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		scoreInfo = new GameInfo();
		scoreInfo.locate(Text.HAlignLeft, Text.VAlignTop);
		scoreInfo.setPadding(7, 90);

		confirmExitDialog = new Dialog();
		confirmExitDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onExitConfirmed("Yes".equals(id));
			}
		});

		updateScoreText();
	}

	private void updateScoreText() {
		scoreInfo.setText("Score: " + totalScore);
	}

	private void onExitConfirmed(boolean exit) {
		isExitConfirmed = exit;
		if (isExitConfirmed) {
			getChallengeModeListener().onExitConfirmed();
		}
		else {
			timer.start();
			openCloseCards(true);
		}
	}

	private void confirmModeExit() {
		timer.pause();
		openCloseCards(false);
		confirmExitDialog.open("Are you sure you want to exit game? All progress will be lost!");
	}

	@Override
	public void startMode() {
		super.startMode();
		totalScore = 0;
		challengeTimer.restart();
		updateScoreText();
		isExitConfirmed = false;
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
						String.format("You found %d ichigu%s!", ichigusFound, ichigusFound != 1 ? "s" : "") +
						"\n\nTotal Time " + modeCompleteTime +
						"\n\n" + scoreInfo.getText() +
						"\n\nTouch Screen To Continue");
		
		isExitConfirmed = true;
	}

	@Override
	public boolean exitMode() {
		if (isExitConfirmed) {
			super.exitMode();
			confirmExitDialog.close();
			totalScore = 0;
			challengeTimer.stop();
			isExitConfirmed = false;
			return true;
		}
		else {
			confirmModeExit();
			return false;
		}
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
			// -3: Score is in the range of min=6 and max=12
			// To increase max/min ratio we subtract 3 from the score
			totalScore += (int) (((score - 3) * ((maxTimeToFindIchigu - elapsed) / 10f) * (deductScore ? 0.5f : 1)));
			updateScoreText();
			challengeTimer.restart();
			deductScore = false;
		}
		return score;
	}

	@Override
	protected void openExtraCards() {
		super.openExtraCards();
		deductScore = hint.getIchiguCount() != 0;
	}

	protected void drawScore() {
		scoreInfo.draw();
	}
}
