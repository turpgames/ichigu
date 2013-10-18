package com.turpgames.ichigu.model;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.utils.R;

public class PracticeMode extends TrainingMode {
	private final static float blockDuration = 2f;
	private final static float timePerDeal = 5;
	private final static int totalDeals = 20;

	private final Timer blockTimer;
	private final Timer dealTimer;

	private int deals = 0;
	private int score = 0;

	private GameInfo timeInfo;
	private GameInfo waitInfo;
	private GameInfo scoreInfo;
	private GameInfo remainingCardsInfo;
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

	public PracticeMode() {
		timeInfo = new GameInfo();
		timeInfo.locate(Text.HAlignRight, Text.VAlignTop);
		timeInfo.setPadding(20, 125);

		waitInfo = new GameInfo();
		waitInfo.locate(Text.HAlignLeft, Text.VAlignTop);
		waitInfo.setPadding(20, 125);

		scoreInfo = new GameInfo();
		scoreInfo.locate(Text.HAlignLeft, Text.VAlignBottom);
		scoreInfo.setPadding(20, 75);

		remainingCardsInfo = new GameInfo();
		remainingCardsInfo.locate(Text.HAlignRight, Text.VAlignBottom);
		remainingCardsInfo.setPadding(20, 75);

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
		dealTimer = new Timer();

		blockTimer.setInterval(blockDuration);
		dealTimer.setInterval(timePerDeal);

		blockTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				onBlockTimerTick();
			}
		});

		dealTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				onDealTimerTick();
			}
		});
	}

	private void onExitConfirmed(boolean exit) {
		isExitConfirmed = exit;
		if (isExitConfirmed) {
			getModeListener().onExitConfirmed();
		}
		else {
			if (blockTimer.isPaused())
				blockTimer.start();
			dealTimer.start();
			openCloseCards(true);
		}
	}

	private void confirmModeExit() {
		if (blockTimer.isRunning())
			blockTimer.pause();
		dealTimer.pause();
		openCloseCards(false);
		confirmExitDialog.open("Are you sure you want to exit game? All progress will be lost!");
	}

	private void onBlockTimerTick() {
		blockTimer.stop();
		notifyUnblocked();
	}

	private void onDealTimerTick() {
		blockTimer.stop();
		dealTimer.stop();
		notifyDealTimeUp();
	}

	private IPracticeModeListener getModeListener() {
		return (IPracticeModeListener) super.modeListener;
	}

	protected void openCloseCards(boolean open) {
		for (int i = 0; i < TrainingCards.TotalCardCount; i++) {
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

	private void notifyDealTimeUp() {
		if (getModeListener() != null)
			getModeListener().onDealTimeUp();
	}

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
		return deals < totalDeals;
	}

	private void addScore(int score) {
		// -3: Score is in the range of min=6 and max=12
		// To increase max/min ratio we subtract 3 from the score
		this.score += (int) ((timePerDeal - dealTimer.getElapsedTime()) * (score - 3f));
	}

	private void block() {
		blockTimer.start();
	}

	public void dealEnd() {
		dealTimer.start();
	}

	public void startMode() {
		score = 0;
		deals = 0;
	}

	public void endMode() {
		deactivateCards();
		blockTimer.stop();
		dealTimer.stop();
		cards.empty();
		isExitConfirmed = true;
		int practiceHiScore = Settings.getInteger(R.settings.hiscores.practice, 0);
		if (score > practiceHiScore)
			Settings.putInteger(R.settings.hiscores.practice, score);
	}

	@Override
	public boolean exitMode() {
		if (isExitConfirmed) {
			blockTimer.stop();
			dealTimer.stop();
			touchHandler.deactivate();
			score = 0;
			deals = 0;
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
		selectedCard.deselect();
		int score = cards.checkScore(selectedCard);
		if (score > 0) {
			addScore(score);
			notifyIchiguFound();
		}
		else {
			block();
			notifyInvalidIchiguSelected();
		}
	}

	@Override
	public void deal() {
		if (!requiresNewDeal()) {
			notifyModeEnd();
			return;
		}

		deals++;
		blockTimer.stop();
		dealTimer.stop();
		super.deal();
	}

	public void drawGame() {
		drawCards();
		drawScore();
		drawRemainingDeals();
		drawRemainingTime();
		if (!blockTimer.isStopped())
			drawWaitMessage();
	}

	public void drawResults() {
		resultInfo.setText("Score: " + score + "\n\nTouch Screen To Continue");
		resultInfo.draw();
	}

	private void drawCards() {
		Card[] allCards = cards.getAllCards();
		for (int i = 0; i < allCards.length; i++)
			if (allCards[i] != null)
				allCards[i].draw();
	}

	private void drawRemainingTime() {
		int t = (int) Math.min(timePerDeal, (1 + timePerDeal - dealTimer.getElapsedTime()));
		timeInfo.setText("" + t);
		timeInfo.draw();
	}

	private void drawScore() {
		scoreInfo.setText("Score: " + score);
		scoreInfo.draw();
	}

	private void drawRemainingDeals() {
		remainingCardsInfo.setText(deals + "/" + totalDeals);
		remainingCardsInfo.draw();
	}

	private void drawWaitMessage() {
		waitInfo.setText("Wait: " + String.format("%.1f", blockDuration - blockTimer.getElapsedTime()));
		waitInfo.draw();
	}
}