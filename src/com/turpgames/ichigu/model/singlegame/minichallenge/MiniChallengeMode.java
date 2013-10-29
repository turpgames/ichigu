package com.turpgames.ichigu.model.singlegame.minichallenge;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.game.ResultScreenButtons;
import com.turpgames.ichigu.model.game.info.BlinkingGameInfo;
import com.turpgames.ichigu.model.game.info.GameInfo;
import com.turpgames.ichigu.model.game.info.WaitToast;
import com.turpgames.ichigu.model.singlegame.SingleGameCards;
import com.turpgames.ichigu.model.singlegame.SingleGameMode;
import com.turpgames.ichigu.utils.R;

public class MiniChallengeMode extends SingleGameMode implements IResultScreenButtonsListener {
	private final static float blockDuration = 2f;
//	private final static float timePerDeal = 5;
//	private final static int totalDeals = 20;

	private final Timer blockTimer;
//	private final Timer dealTimer;
	private final Timer challengeTimer;

//	private int deals = 0;
//	private int score = 0;

	private int ichigusFound;
	private GameInfo ichigusFoundInfo;
	
	private BlinkingGameInfo timeInfo;
	private WaitToast waitInfo;
//	private ScoreInfo scoreInfo;
//	private GameInfo remainingCardsInfo;
	private GameInfo resultInfo;

	private ResultScreenButtons resultScreenButtons;

	private Dialog confirmExitDialog;
	private boolean isExitConfirmed;

	public MiniChallengeMode() {
//		pointsInfo.getLocation().set(0, Game.getVirtualHeight() - pointsInfo.getHeight() - 35));
//		pointsInfo.initPointInfos();
		resultScreenButtons = new ResultScreenButtons(this); 
		
		timeInfo = new BlinkingGameInfo();
		timeInfo.locate(Text.HAlignCenter, Text.VAlignBottom);
		timeInfo.setPadding(0, 75);
		
		waitInfo = new WaitToast();
		
		ichigusFoundInfo = new GameInfo();
		ichigusFoundInfo.locate(Text.HAlignLeft, Text.VAlignTop);
		ichigusFoundInfo.setPadding(20, 125);

//		scoreInfo = new ScoreInfo();
//		scoreInfo.locate(Text.HAlignLeft, Text.VAlignTop);
//		scoreInfo.setPadding(20, 125);

//		remainingCardsInfo = new GameInfo();
//		remainingCardsInfo.locate(Text.HAlignRight, Text.VAlignBottom);
//		remainingCardsInfo.setPadding(20, 75);

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
//		dealTimer = new Timer();

		blockTimer.setInterval(blockDuration);
//		dealTimer.setInterval(timePerDeal);

		blockTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				onBlockTimerTick();
			}
		});

//		dealTimer.setTickListener(new Timer.ITimerTickListener() {
//			@Override
//			public void timerTick(Timer timer) {
//				onDealTimerTick();
//			}
//		});
		
		challengeTimer = new Timer();
		challengeTimer.setInterval(1);
		challengeTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				int elapsed = (int) timer.getTotalElapsedTime();
				int min = 0 - elapsed / 60;
				int sec = 60 - elapsed % 60;

				timeInfo.setText(Utils.getTimeString(60-elapsed));
				
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
//			dealTimer.start();
			challengeTimer.start();
			openCloseCards(true);
		}
	}

	private void confirmModeExit() {
		if (blockTimer.isRunning())
			blockTimer.pause();
//		dealTimer.pause();
		challengeTimer.pause();
		openCloseCards(false);
		confirmExitDialog.open(Game.getLanguageManager().getString(R.strings.exitConfirm));
	}

	private void onBlockTimerTick() {
		blockTimer.stop();
		notifyUnblocked();
	}

//	private void onDealTimerTick() {
//		blockTimer.stop();
//		dealTimer.stop();
//		notifyDealTimeUp();
//	}

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

//	private void notifyDealTimeUp() {
//		if (getModeListener() != null)
//			getModeListener().onDealTimeUp();
//	}

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

	private boolean requiresNewDeal() {
//		return deals < totalDeals;
		return true;
	}

	private void block() {
		blockTimer.start();
		waitInfo.show(blockDuration*1000);
	}

	public void dealEnd() {
//		dealTimer.start();
		challengeTimer.start();
	}

	public void startMode() {
//		score = 0;
		ichigusFound = 0;
		ichigusFoundInfo.setText(Game.getLanguageManager().getString(R.strings.found) + ": " + ichigusFound);
//		deals = 0;
		timeInfo.setText("01:00");
	}

	public void endMode() {
		deactivateCards();
		blockTimer.stop();
		challengeTimer.stop();
//		dealTimer.stop();
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
//			dealTimer.stop();
			challengeTimer.stop();
			resultScreenButtons.listenInput(false);
//			score = 0;
//			deals = 0;
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
//			scoreInfo.increaseScore(ichiguScore);
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
		if (!requiresNewDeal()) {
			notifyModeEnd();
			return;
		}

//		deals++;
		blockTimer.stop();
//		dealTimer.stop();
		challengeTimer.pause();
		super.deal();
	}

	public void drawGame() {
		drawCards();
//		drawScore();
//		drawRemainingDeals();
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
//		int t = (int) Math.min(timePerDeal, (1 + timePerDeal - dealTimer.getElapsedTime()));
//		timeInfo.setText("" + t);
		timeInfo.draw();
	}

//	private void drawScore() {
//		scoreInfo.draw();
//	}

//	private void drawRemainingDeals() {
//		remainingCardsInfo.setText(deals + "/" + totalDeals);
//		remainingCardsInfo.draw();
//	}

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