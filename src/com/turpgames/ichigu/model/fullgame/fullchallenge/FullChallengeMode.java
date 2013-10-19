package com.turpgames.ichigu.model.fullgame.fullchallenge;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.GameInfo;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.utils.R;

public class FullChallengeMode extends FullGameMode {
//	private static final int maxTimeToFindIchigu = 100;

	private int totalScore;
//	private Timer challengeTimer;
//	private boolean deductScore;
	
	private GameInfo resultInfo;
	private GameInfo scoreInfo;

	private Dialog confirmExitDialog;
	private boolean isExitConfirmed;

	
	private IFullChallengeModeListener getChallengeModeListener() {
		return (IFullChallengeModeListener) super.modeListener;
	}

	public FullChallengeMode() {
		hint.deactivate();

//		challengeTimer = new Timer();

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

		timer = new Timer();
		timer.setInterval(1);
		timer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				int elapsed = (int) timer.getTotalElapsedTime();
				int min = 2 - elapsed / 60;
				int sec = 60 - elapsed % 60;

				timeInfo.setText((min < 10 ? ("0" + min) : ("" + min)) +
						":" +
						(sec < 10 ? ("0" + sec) : ("" + sec)));
				
				if (min <= 0 && sec == 0)
					timerFinished();
			}
		});
	}

	protected void timerFinished() {
		notifyModeEnd();
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
//		challengeTimer.restart();
		updateScoreText();
		isExitConfirmed = false;
		timeInfo.setText("03:00");
	}

	@Override
	public void endMode() {
		super.endMode();
//		challengeTimer.stop();
		int challengeHiScore = Settings.getInteger(R.settings.hiscores.challenge, 0);
		if (totalScore > challengeHiScore)
			Settings.putInteger(R.settings.hiscores.challenge, totalScore);

		resultInfo.setText(
				"Game over!\n\nCongratulations,\n" +
						String.format("You found %d ichigu%s!", ichigusFound, ichigusFound != 1 ? "s" : "") +
						"\n\n" + scoreInfo.getText() +
						"\n\nTouch Screen To Continue");
		
		isExitConfirmed = true;
	}

	@Override
	public boolean exitMode() {
		if (isExitConfirmed) {
			confirmExitDialog.close();
			totalScore = 0;
//			challengeTimer.stop();
			isExitConfirmed = false;
			return super.exitMode();
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
//			float elapsed = challengeTimer.getElapsedTime();
//			if (elapsed > maxTimeToFindIchigu)
//				elapsed = maxTimeToFindIchigu;
			// -3: Score is in the range of min=6 and max=12
			// To increase max/min ratio we subtract 3 from the score
//			totalScore += (int) (((score - 3) * ((maxTimeToFindIchigu - elapsed) / 10f) * (deductScore ? 0.5f : 1)));
			totalScore += score;
			updateScoreText();
//			challengeTimer.restart();
//			deductScore = false;
		}
		return score;
	}

	@Override
	protected void openExtraCards() {
		super.openExtraCards();
//		deductScore = hint.getIchiguCount() != 0;
	}

	@Override
	public void deckFinished() {
		deal();
		deckCount++;
	}
	
	protected void drawScore() {
		scoreInfo.draw();
	}
}
