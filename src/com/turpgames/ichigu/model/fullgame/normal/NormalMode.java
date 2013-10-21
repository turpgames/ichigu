package com.turpgames.ichigu.model.fullgame.normal;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.Card;
import com.turpgames.ichigu.model.GameInfo;
import com.turpgames.ichigu.model.ScoreInfo;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.model.fullgame.IHintListener;
import com.turpgames.ichigu.utils.R;

public class NormalMode extends FullGameMode implements IHintListener {
//	private static final int maxTimeToFindIchigu = 100;

//	private Timer challengeTimer;
//	private boolean deductScore;

	private GameInfo resultInfo;
	private ScoreInfo scoreInfo;

	private Dialog confirmExitDialog;
	private boolean isExitConfirmed;

	private INormalModeListener getChallengeModeListener() {
		return (INormalModeListener) super.modeListener;
	}

	public NormalMode() {
		super();
		hint.getLocation().set(Game.getVirtualWidth() - hint.getWidth() - 10, 50);
		hint.activate();
		hint.setHintListener(this);
		
//		challengeTimer = new Timer();

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

		updateScoreText();
	}

	private void updateScoreText() {
		
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
//		challengeTimer.restart();
		updateScoreText();
		isExitConfirmed = false;
	}

	@Override
	public void endMode() {
		super.endMode();
//		challengeTimer.stop();
		timer.stop();
		int time = (int) timer.getElapsedTime();
		int min = time / 60;
		int sec = time % 60;
		
		int hiScore = Settings.getInteger(R.settings.hiscores.normal, 0);
		int hiTime = Settings.getInteger(R.settings.hiscores.normaltime, 0);
		int score = scoreInfo.getScore();
		if (score > hiScore || time < hiTime) {
			Settings.putInteger(R.settings.hiscores.normal, score);
			Settings.putInteger(R.settings.hiscores.normaltime, time);
		}

		resultInfo.setText(
				"Game over!\n\nCongratulations,\n" +
						String.format("You found %d ichigu%s!", ichigusFound, ichigusFound != 1 ? "s" : "") +
						"\n\nTotal Time " + modeCompleteTime +
						"\n\n" + scoreInfo.getText() +
						"\n" + (min < 10 ? ("0" + min) : ("" + min)) + ":" + (sec < 10 ? ("0" + sec) : ("" + sec)) + 
						(score > hiScore || time < hiTime ? "\n\nNew High Score!!!" : "") + 
						"\n\nTouch Screen To Continue");
		
		isExitConfirmed = true;
	}

	@Override
	public boolean exitMode() {
		if (isExitConfirmed) {
			confirmExitDialog.close();
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
		drawHint();
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
//			// -3: Score is in the range of min=6 and max=12
//			// To increase max/min ratio we subtract 3 from the score
//			totalScore += (int) (((score - 3) * ((maxTimeToFindIchigu - elapsed) / 10f) * (deductScore ? 0.5f : 1)));
			scoreInfo.increaseScore(score);
//			challengeTimer.restart();
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
	
	@Override
	protected void drawRemainingCards() {
		remaingCardInfo.setText(getDealer().getIndex() + "/" + Card.CardsInDeck);
		remaingCardInfo.draw();
	}
	
	protected void drawScore() {
		scoreInfo.draw();
	}
	
	protected void drawHint() {
		hint.draw();
	}

	@Override
	public void onHintShowed() {
		scoreInfo.decreaseScore(10);
	}
}
