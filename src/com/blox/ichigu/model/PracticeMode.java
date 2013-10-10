package com.blox.ichigu.model;

import com.blox.framework.v0.impl.Settings;
import com.blox.framework.v0.util.TextDrawer;
import com.blox.framework.v0.util.Timer;
import com.blox.ichigu.utils.R;

public class PracticeMode extends TrainingMode {
	private final static float blockDuration = 2f;
	private final static float timePerDeal = 5;
	private final static int totalDeals = 20;

	private final Timer blockTimer;
	private final Timer dealTimer;

	private int deals = 0;
	private int score = 0;
	private GameInfo info;

	private ScreenTouchHandler touchHandler;

	private final IScreenTouchListener touchListener = new IScreenTouchListener() {
		@Override
		public void onScreenTouched() {
			notifyNewGame();
		}
	};

	private IPracticeModeListener getModeListener() {
		return (IPracticeModeListener)super.modeListener;
	}

	public int getScore() {
		return score;
	}

	public PracticeMode() {
		info = new GameInfo(50, 25);

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

	private void onBlockTimerTick() {
		blockTimer.stop();
		notifyUnblocked();
	}

	private void onDealTimerTick() {
		blockTimer.stop();
		dealTimer.stop();
		notifyDealTimeUp();
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
		this.score += (int) (timePerDeal - dealTimer.getElapsedTime()) * score;
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
		int practiceHiScore = Settings.getInteger(R.settings.hiscores.practice, 0);
		if (score > practiceHiScore)
			Settings.putInteger(R.settings.hiscores.practice, score);
	}

	@Override
	public void exitMode() {
		blockTimer.stop();
		dealTimer.stop();
		score = 0;
		deals = 0;
		super.exitMode();
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
		if (blockTimer.isRunning())
			drawWaitMessage();
	}

	public void drawResults() {
		info.draw("Score: " + score, TextDrawer.AlignCentered, 50);
		info.draw("Touch Screen", TextDrawer.AlignCentered, -50);
		info.draw("To Continue", TextDrawer.AlignCentered, -100);
	}

	private void drawCards() {
		Card[] allCards = cards.getAllCards();
		for (int i = 0; i < allCards.length; i++)
			allCards[i].draw();
	}

	private void drawRemainingTime() {
		int t = (int) Math.min(timePerDeal, (1 + timePerDeal - dealTimer.getElapsedTime()));
		info.draw("" + t, TextDrawer.AlignNE, -70);
	}

	private void drawScore() {
		info.draw("Score: " + score, TextDrawer.AlignSW, 100);
	}
	
	private void drawRemainingDeals() {
		info.draw("Deals: " + deals + "/" + totalDeals, TextDrawer.AlignSW, 50);
	}

	private void drawWaitMessage() {
		info.draw("Wait: " + String.format("%.1f", blockDuration - blockTimer.getElapsedTime()), TextDrawer.AlignNW, -70);
	}
}