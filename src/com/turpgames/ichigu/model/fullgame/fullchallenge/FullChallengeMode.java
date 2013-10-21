package com.turpgames.ichigu.model.fullgame.fullchallenge;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.GameInfo;
import com.turpgames.ichigu.model.ScoreInfo;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.utils.R;

public class FullChallengeMode extends FullGameMode {
//	private static final int maxTimeToFindIchigu = 100;

//	private Timer ichiguTimer;
//	private boolean deductScore;
	
	private GameInfo resultInfo;
	private ScoreInfo scoreInfo;

	private Dialog confirmExitDialog;
	private boolean isExitConfirmed;

	
	private IFullChallengeModeListener getChallengeModeListener() {
		return (IFullChallengeModeListener) super.modeListener;
	}

	public FullChallengeMode() {
		hint.deactivate();

//		ichiguTimer = new Timer();

		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		scoreInfo = new ScoreInfo();
		scoreInfo.locate(Text.HAlignLeft, Text.VAlignTop);
		scoreInfo.setPadding(7, 90);

		confirmExitDialog = new Dialog();
		confirmExitDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onExitConfirmed("Yes".equals(id));
			}
		});

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
				
				if (min < 0)
					timerFinished();
			}
		});
	}

	protected void timerFinished() {
		notifyModeEnd();
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
		scoreInfo.init();
//		ichiguTimer.restart();
		isExitConfirmed = false;
		timeInfo.setText("03:00");
	}

	@Override
	public void endMode() {
		super.endMode();
//		ichiguTimer.stop();
		int hiScore = Settings.getInteger(R.settings.hiscores.fullchallenge, 0);
		int score = scoreInfo.getScore();
		if (score > hiScore)
			Settings.putInteger(R.settings.hiscores.fullchallenge, score);

		resultInfo.setText(
				"Game over!\n\nCongratulations,\n" +
						String.format("You found %d ichigu%s!", ichigusFound, ichigusFound != 1 ? "s" : "") +
						"\n\n" + scoreInfo.getText() +
						(score > hiScore ? "\n\nNew High Score!!!" : "") + 
						"\n\nTouch Screen To Continue");
		
		isExitConfirmed = true;
	}

	@Override
	public boolean exitMode() {
		if (isExitConfirmed) {
			confirmExitDialog.close();
//			ichiguTimer.stop();
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
//			float elapsed = ichiguTimer.getElapsedTime();
//			if (elapsed > maxTimeToFindIchigu)
//				elapsed = maxTimeToFindIchigu;
			// -3: Score is in the range of min=6 and max=12
			// To increase max/min ratio we subtract 3 from the score
//			totalScore += (int) (((score - 3) * ((maxTimeToFindIchigu - elapsed) / 10f) * (deductScore ? 0.5f : 1)));
			scoreInfo.increaseScore(score);
//			ichiguTimer.restart();
//			deductScore = false;
		}
		return score;
	}

	@Override
	protected void openExtraCards() {
		super.openExtraCards();
		if (hint.getIchiguCount() != 0)
			scoreInfo.decreaseScore(10);
	}

//	@Override
//	public void deckFinished() {
//		dealer.reset();
//		deckCount++;
//	}
	
	protected void drawScore() {
		scoreInfo.draw();
	}
}
