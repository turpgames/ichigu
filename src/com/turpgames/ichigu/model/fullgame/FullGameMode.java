package com.turpgames.ichigu.model.fullgame;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.Card;
import com.turpgames.ichigu.model.GameInfo;
import com.turpgames.ichigu.model.IScreenTouchListener;
import com.turpgames.ichigu.model.IchiguMode;
import com.turpgames.ichigu.model.ScreenTouchHandler;
import com.turpgames.ichigu.utils.IchiguResources;

public abstract class FullGameMode extends IchiguMode {

	protected FullGameCards cards;
	protected FullGameHint hint;
	protected ScreenTouchHandler touchHandler;
	protected int selectedCardCount;
	
	protected int ichigusFound;
	protected GameInfo ichigusFoundInfo;
	
	protected int deckCount;	
	protected GameInfo remaingCardInfo;
	
	protected Timer timer;
	protected GameInfo timeInfo;
	protected String modeCompleteTime;
	
	private boolean areExtraCardsOpened;

	protected void openCloseCards(boolean open) {
		for (int i = 0; i < FullGameCards.ActiveCardCount; i++) {
			if (!cards.isActiveCardEmpty(i)) {
				if (open)
					cards.getActiveCard(i).open();
				else
					cards.getActiveCard(i).close();
			}
		}

		if (areExtraCardsOpened) {
			for (int i = 0; i < FullGameCards.ExtraCardCount; i++) {
				if (!cards.isExtraCardEmpty(i)) {
					if (open)
						cards.getExtraCard(i).open();
					else
						cards.getExtraCard(i).close();

				}
			}
		}
	}

	protected final IScreenTouchListener touchListener = new IScreenTouchListener() {
		@Override
		public void onScreenTouched() {
			notifyNewGame();
		}
	};

	public FullGameMode() {
		cards = new FullGameCards();
		dealer = new FullGameCardDealer(cards);
		touchHandler = new ScreenTouchHandler();
		
		ichigusFoundInfo = new GameInfo();
		ichigusFoundInfo.locate(Text.HAlignRight, Text.VAlignTop);
		ichigusFoundInfo.setPadding(10, 90);
		
		remaingCardInfo = new GameInfo();
		remaingCardInfo.locate(Text.HAlignCenter, Text.VAlignBottom);
		remaingCardInfo.setPadding(0, 75);
		
		timeInfo = new GameInfo();
		timeInfo.locate(Text.HAlignCenter, Text.VAlignBottom);
		timeInfo.setPadding(0, 30);
		
		timer = new Timer();
		timer.setInterval(1);
		timer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				int elapsed = (int) timer.getTotalElapsedTime();
				int min = elapsed / 60;
				int sec = elapsed % 60;

				timeInfo.setText((min < 10 ? ("0" + min) : ("" + min)) +
						":" +
						(sec < 10 ? ("0" + sec) : ("" + sec)));
			}
		});

		hint = new FullGameHint();
	}

	public FullGameCards getCards() {
		return cards;
	}

	protected IFullGameModeListener getModeListener() {
		return (IFullGameModeListener) super.modeListener;
	}

	protected FullGameCardDealer getDealer() {
		return (FullGameCardDealer) dealer;
	}

	protected void notifyModeEnd() {
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	protected void notifyNewGame() {
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	public void cardTapped(Card card) {
		hint.restartNotificationTimer();

		if (!card.isOpened()) {
			card.deselect();
			openExtraCards();
			updateHints();
			return;
		}

		if (card.isSelected())
			selectedCardCount++;
		else
			selectedCardCount--;

		if (selectedCardCount == FullGameCards.IchiguCardCount) {
			checkIchigu();
			selectedCardCount = 0;
		}
	}

	protected int checkIchigu() {
		int score = cards.getScore();
		if (score > 0) {
			ichigusFound++;
			notifyIchiguFound();
			areExtraCardsOpened = false;
		}
		else {
			notifyInvalidIchiguSelected();
		}
		return score;
	}

	public void activateCards() {
		for (int i = 0; i < FullGameCards.TotalCardsOnTable; i++) {
			if (!cards.isEmpty(i)) {
				cards.getCard(i).activate(modeListener);
			}
		}
		updateHints();
	}

	public void deactivateCards() {
		for (int i = 0; i < FullGameCards.TotalCardsOnTable; i++) {
			if (!cards.isEmpty(i)) {
				cards.getCard(i).deactivate();
			}
		}
	}

	public void deselectCards() {
		cards.deselectCards();
	}

	protected void openExtraCards() {
		areExtraCardsOpened = true;
		for (int i = 0; i < FullGameCards.ExtraCardCount; i++)
			cards.getExtraCard(i).open();
	}

	protected void updateHints() {
		cards.updateHint(hint);

		boolean thereIsNoIchigu = hint.getIchiguCount() == 0;
		boolean extraCardsAreOpened = cards.isExtraCardEmpty(0) || cards.getExtraCard(0).isOpened();
		boolean hasMoreCardsInDeck = getDealer().getIndex() < Card.CardsInDeck;

		if (thereIsNoIchigu && extraCardsAreOpened) {
			if (hasMoreCardsInDeck) {
				deactivateCards();
				getDealer().dealExtraCards();
				openExtraCards();
				activateCards();
			}
			else {
				deckFinished();
			}
		}
	}

	public void deckFinished() {
		notifyModeEnd();
	}
	
	public void startMode() {
		cards.empty();
		dealer.reset();
		touchHandler.deactivate();
		deckCount = 1;
		ichigusFound = 0;
		selectedCardCount = 0;
		timer.start();
		hint.activate();
		timeInfo.setText("00:00");
	}

	public void endMode() {
		IchiguResources.playSoundTimeUp();
		modeCompleteTime = timeInfo.getText();
		timer.stop();
		cards.empty();
		deactivateCards();
		hint.deactivate();
		touchHandler.activate(touchListener);
	}

	public boolean exitMode() {
		touchHandler.deactivate();
		deactivateCards();
		cards.empty();
		timer.stop();
		hint.deactivate();
		return true;
	}

	public void drawGame() {
		drawTime();
		drawCards();
		drawRemainingCards();
		drawIchigusFound();
	}

	public abstract void drawResult();

	protected void drawCards() {
		cards.draw();
	}

	protected void drawRemainingCards() {
		remaingCardInfo.setText(getDealer().getIndex() + "/" + Card.CardsInDeck + " | Deck " + deckCount);
		remaingCardInfo.draw();
	}

	protected void drawTime() {
		timeInfo.draw();
	}
	
	protected void drawIchigusFound() {
		ichigusFoundInfo.setText("Found: " + ichigusFound);
		ichigusFoundInfo.draw();
	}
}
