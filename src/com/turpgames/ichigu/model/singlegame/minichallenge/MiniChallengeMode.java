package com.turpgames.ichigu.model.singlegame.minichallenge;

import com.turpgames.framework.v0.component.info.GameInfo;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.DisplayTimer;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.model.display.WaitToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.game.ResultScreenButtons;
import com.turpgames.ichigu.model.singlegame.SingleGameCards;
import com.turpgames.ichigu.model.singlegame.SingleGameMode;
import com.turpgames.ichigu.utils.R;

public class MiniChallengeMode extends SingleGameMode implements IResultScreenButtonsListener {
	private final static float blockDuration = 2f;
	private final static int challengeTime = 60;

	private final Timer blockTimer;
	private final Timer challengeTimer;

	private int ichigusFound;
	private GameInfo ichigusFoundInfo;
	
	private DisplayTimer timeInfo;
	private WaitToast waitInfo;
	private GameInfo resultInfo;

	private ResultScreenButtons resultScreenButtons;

	private Dialog confirmExitDialog;
	private boolean isExitConfirmed;

	public MiniChallengeMode() {
		resultScreenButtons = new ResultScreenButtons(this); 
		
		timeInfo = new DisplayTimer(R.colors.ichiguRed, 5, 30);
		timeInfo.locate(Text.HAlignRight, Text.VAlignTop);
		timeInfo.setPadding(20, 125);
		
		waitInfo = new WaitToast();
		waitInfo.setToastListener(new Toast.IToastListener() {			
			@Override
			public void onToastHidden(Toast toast) {
				notifyUnblocked();
			}
		});
		
		ichigusFoundInfo = new GameInfo();
		ichigusFoundInfo.locate(Text.HAlignLeft, Text.VAlignTop);
		ichigusFoundInfo.setPadding(20, 125);

		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		confirmExitDialog = new IchiguDialog();
		confirmExitDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onExitConfirmed(R.strings.yes.equals(id));
			}
		});

		blockTimer = new Timer();
		blockTimer.setInterval(blockDuration);
		blockTimer.setTickListener(new Timer.ITimerTickListener() {			
			@Override
			public void timerTick(Timer timer) {
				timer.stop();			
			}
		});

		challengeTimer = new Timer();
		challengeTimer.setInterval(1);
		challengeTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				int elapsed = (int) timer.getTotalElapsedTime();
				int min = (challengeTime / 60 - 1) - elapsed / 60;
				int sec = 60 - elapsed % 60;

				timeInfo.setTimeText(challengeTime-elapsed);
				
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
			getModeListener().onExitConfirmed();
		}
		else {
			if (blockTimer.isPaused())
				blockTimer.start();
			challengeTimer.start();
			openCloseCards(true);
		}
	}

	private void confirmModeExit() {
		if (blockTimer.isRunning())
			blockTimer.pause();
		challengeTimer.pause();
		openCloseCards(false);
		confirmExitDialog.open(Game.getLanguageManager().getString(R.strings.exitConfirm));
	}

	private IMiniChallengeModeListener getModeListener() {
		return (IMiniChallengeModeListener) super.modeListener;
	}

	protected void openCloseCards(boolean open) {
		for (int i = 0; i < SingleGameCards.TotalCardCount; i++) {
			if (open)
				cards.get(i).open();
			else
				cards.get(i).close();
		}
	}

	private void notifyUnblocked() {
		if (getModeListener() != null)
			getModeListener().onUnblock();
	}

	private void notifyModeEnd() {
		resultScreenButtons.listenInput(true);
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	private void notifyNewGame() {
		resultScreenButtons.listenInput(false);
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	private void block() {
		blockTimer.start();
		waitInfo.show(blockDuration);
	}

	public void dealEnd() {
		challengeTimer.start();
	}

	public void startMode() {
		ichigusFound = 0;
		ichigusFoundInfo.setText(Game.getLanguageManager().getString(R.strings.found) + ": " + ichigusFound);
		timeInfo.setTimeText(challengeTime);
	}

	public void endMode() {
		deactivateCards();
		blockTimer.stop();
		challengeTimer.stop();
		cards.empty();
		isExitConfirmed = true;
		int hiScore = Settings.getInteger(R.settings.hiscores.minichallenge, 0);
		if (ichigusFound > hiScore)
			Settings.putInteger(R.settings.hiscores.minichallenge, ichigusFound);
		
		if (ichigusFound != 1)
			resultInfo.setText(String.format(Game.getLanguageManager().getString(R.strings.miniChallengeResultMultiple), 
				ichigusFound, (ichigusFound > hiScore ? Game.getLanguageManager().getString(R.strings.newHiscore) : "")));
		else
			resultInfo.setText(String.format(Game.getLanguageManager().getString(R.strings.miniChallengeResultSingle), 
					ichigusFound, (ichigusFound > hiScore ? Game.getLanguageManager().getString(R.strings.newHiscore) : "")));
	}

	@Override
	public boolean exitMode() {
		if (isExitConfirmed) {
			blockTimer.stop();
			challengeTimer.stop();
			resultScreenButtons.listenInput(false);
			confirmExitDialog.close();
			isExitConfirmed = false;
			return super.exitMode();
		}
		else {
			confirmModeExit();
			return false;
		}
	}

	@Override
	public void onCardSelected(Card selectedCard) {
		int ichiguScore = cards.checkScore(selectedCard);
		if (ichiguScore > 0) {
			ichigusFound++;
			ichigusFoundInfo.setText(Game.getLanguageManager().getString(R.strings.found) + ": " + ichigusFound);
		}
		else {
			block();
		}
		super.onCardSelected(selectedCard);
	}

	@Override
	public void deal() {
		blockTimer.stop();
		challengeTimer.pause();
		super.deal();
	}

	public void drawGame() {
		drawCards();
		drawRemainingTime();
		drawIchigusFound();
		if (!blockTimer.isStopped())
			drawWaitMessage();
	}

	public void drawResultScreen() {
		resultInfo.draw();
		resultScreenButtons.draw();
	}

	private void drawCards() {
		Card[] allCards = cards.getAllCards();
		for (int i = 0; i < allCards.length; i++)
			if (allCards[i] != null)
				allCards[i].draw();
	}

	private void drawRemainingTime() {
		timeInfo.draw();
	}

	private void drawWaitMessage() {
		waitInfo.setText(String.format("%.1f", blockDuration - blockTimer.getElapsedTime()));
	}
	
	protected void drawIchigusFound() {
		ichigusFoundInfo.draw();
	}

	@Override
	public void onBackToMenuTapped() {
		getModeListener().onExitConfirmed();
	}

	@Override
	public void onNewGameTapped() {
		notifyNewGame();
	}
}