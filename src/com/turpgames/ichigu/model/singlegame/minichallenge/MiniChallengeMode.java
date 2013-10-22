package com.turpgames.ichigu.model.singlegame.minichallenge;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.Card;
import com.turpgames.ichigu.model.ChallengeTimeInfo;
import com.turpgames.ichigu.model.GameInfo;
import com.turpgames.ichigu.model.IScreenTouchListener;
import com.turpgames.ichigu.model.ScreenTouchHandler;
import com.turpgames.ichigu.model.singlegame.SingleGameCards;
import com.turpgames.ichigu.model.singlegame.SingleGameMode;
import com.turpgames.ichigu.utils.R;

public class MiniChallengeMode extends SingleGameMode {
	private final static float blockDuration = 1f;
//	private final static float timePerDeal = 5;
//	private final static int totalDeals = 20;

	private final Timer blockTimer;
//	private final Timer dealTimer;
	private final Timer challengeTimer;

//	private int deals = 0;
	private int score = 0;

	private int ichigusFound;
	private GameInfo ichigusFoundInfo;
	
	private ChallengeTimeInfo timeInfo;
	private GameInfo waitInfo;
	private GameInfo scoreInfo;
//	private GameInfo remainingCardsInfo;
	private GameInfo resultInfo;

	private ScreenTouchHandler touchHandler;

	private Dialog confirmExitDialog;
	private boolean isExitConfirmed;

	private final IScreenTouchListener touchListener = new IScreenTouchListener() {
		@Override
		public void onScreenTouched() {
			notifyNewGame();
		}
	};

	public MiniChallengeMode() {
		pointsInfo.getLocation().set(0, Game.getVirtualHeight() - Game.scale(pointsInfo.getHeight() - 35));
		pointsInfo.initPointInfos();

		timeInfo = new ChallengeTimeInfo();
		timeInfo.locate(Text.HAlignCenter, Text.VAlignBottom);
		timeInfo.setPadding(0, 75);
		
		waitInfo = new GameInfo();
		waitInfo.locate(Text.HAlignCenter, Text.VAlignTop);
		waitInfo.setPadding(0, 170);
		waitInfo.setColor(R.colors.ichiguRed);
		
		ichigusFoundInfo = new GameInfo();
		ichigusFoundInfo.locate(Text.HAlignRight, Text.VAlignTop);
		ichigusFoundInfo.setPadding(20, 125);

		scoreInfo = new GameInfo();
		scoreInfo.locate(Text.HAlignLeft, Text.VAlignTop);
		scoreInfo.setPadding(20, 125);

//		remainingCardsInfo = new GameInfo();
//		remainingCardsInfo.locate(Text.HAlignRight, Text.VAlignBottom);
//		remainingCardsInfo.setPadding(20, 75);

		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		confirmExitDialog = new Dialog();
		confirmExitDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onExitConfirmed("Yes".equals(id));
			}
		});

		touchHandler = new ScreenTouchHandler();

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

				timeInfo.setText((min < 10 ? ("0" + min) : ("" + min)) +
						":" +
						(sec < 10 ? ("0" + sec) : ("" + sec)));
				
				if (min < 0 || sec < 0)
					timerFinished();
				if (min == 0 && sec == 10)
					timeInfo.startCountdown();
			}
		});
	}

	protected void timerFinished() {
		notifyModeEnd();
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
		confirmExitDialog.open("Are you sure you want to exit game? All progress will be lost!");
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

	public int getScore() {
		return score;
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
		touchHandler.activate(touchListener);
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	private void notifyNewGame() {
		touchHandler.deactivate();
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	private boolean requiresNewDeal() {
//		return deals < totalDeals;
		return true;
	}

	private void addScore(int score) {
		// -3: Score is in the range of min=6 and max=12
		// To increase max/min ratio we subtract 3 from the score
//		this.score += (int) ((timePerDeal - dealTimer.getElapsedTime()) * (score - 3f));
		this.score += score;
	}

	private void block() {
		blockTimer.start();
	}

	public void dealEnd() {
//		dealTimer.start();
		challengeTimer.start();
	}

	public void startMode() {
		score = 0;
		ichigusFound = 0;
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
		if (score > hiScore)
			Settings.putInteger(R.settings.hiscores.minichallenge, score);
		

		resultInfo.setText(
				"Game over!\n\nCongratulations,\n" +
						String.format("You found %d ichigu%s!", ichigusFound, ichigusFound != 1 ? "s" : "") +
						"\n\n" + scoreInfo.getText() +
						(score > hiScore ? "\n\nNew High Score!!!" : "") + 
						"\n\nTouch Screen To Continue");
	}

	@Override
	public boolean exitMode() {
		if (isExitConfirmed) {
			blockTimer.stop();
//			dealTimer.stop();
			challengeTimer.stop();
			touchHandler.deactivate();
			score = 0;
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
		int score = cards.checkScore(selectedCard);
		if (score > 0) {
			addScore(score);
			ichigusFound++;
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
		drawScore();
//		drawRemainingDeals();
		drawRemainingTime();
		drawIchigusFound();
		if (!blockTimer.isStopped())
			drawWaitMessage();
	}

	public void drawResults() {
		resultInfo.draw();
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

	private void drawScore() {
		scoreInfo.setText("Score: " + score);
		scoreInfo.draw();
	}

//	private void drawRemainingDeals() {
//		remainingCardsInfo.setText(deals + "/" + totalDeals);
//		remainingCardsInfo.draw();
//	}

	private void drawWaitMessage() {
		waitInfo.setText("Wait: " + String.format("%.1f", blockDuration - blockTimer.getElapsedTime()));
		waitInfo.draw();
	}
	
	protected void drawIchigusFound() {
		ichigusFoundInfo.setText("Found: " + ichigusFound);
		ichigusFoundInfo.draw();
	}
}