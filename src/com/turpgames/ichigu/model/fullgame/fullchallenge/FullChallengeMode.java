package com.turpgames.ichigu.model.fullgame.fullchallenge;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.model.game.GameInfo;
import com.turpgames.ichigu.model.game.IchiguDialog;
import com.turpgames.ichigu.model.game.ScoreInfo;
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
		scoreInfo.setPadding(7, 110);

		confirmExitDialog = new IchiguDialog();
		confirmExitDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onExitConfirmed(R.strings.yes.equals(id));
			}
		});

		timer = new Timer();
		timer.setInterval(1);
		timer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				int elapsed = (int) timer.getTotalElapsedTime();
				int min = 4 - elapsed / 60;
				int sec = 60 - elapsed % 60;

				timeInfo.setText(Utils.getTimeString(5*60-elapsed));
				
				if (min < 0 || sec < 0)
					timerFinished();
				if (min == 0 && sec == 10)
					timeInfo.start();
			}
		});
	}

	protected void timerFinished() {
		notifyModeEnd();
		timeInfo.stop();
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
		confirmExitDialog.open(Game.getResourceManager().getString(R.strings.exitConfirm));
	}

	@Override
	public void startMode() {
		super.startMode();
		scoreInfo.init();
//		ichiguTimer.restart();
		isExitConfirmed = false;
		timeInfo.setText("05:00");
	}

	@Override
	public void endMode() {
		super.endMode();
//		ichiguTimer.stop();
		int hiScore = Settings.getInteger(R.settings.hiscores.fullchallenge, 0);
		int score = scoreInfo.getScore();
		if (score > hiScore)
			Settings.putInteger(R.settings.hiscores.fullchallenge, score);

		if (ichigusFound != 1)
			resultInfo.setText(String.format(Game.getResourceManager().getString(R.strings.fullChallengeResultMultiple), 
				ichigusFound, scoreInfo.getText(), 
						(score > hiScore ? Game.getResourceManager().getString(R.strings.newHiscore) : "")));
		else 
			resultInfo.setText(String.format(Game.getResourceManager().getString(R.strings.fullChallengeResultSingle), 
				ichigusFound, scoreInfo.getText(), 
						(score > hiScore ? Game.getResourceManager().getString(R.strings.newHiscore) : "")));
		
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
		resultScreenButtons.draw();
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

	@Override
	public void onBackToMenuTapped() {
		getChallengeModeListener().onExitConfirmed();
	}

	@Override
	public void onNewGameTapped() {
		notifyNewGame();
	}
}
