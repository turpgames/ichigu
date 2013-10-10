package com.blox.ichigu.model;

import com.blox.framework.v0.util.TextDrawer;
import com.blox.framework.v0.util.Timer;
import com.blox.ichigu.utils.IchiguResources;

public abstract class FullGameMode extends IchiguMode {

	protected FullGameCards cards;
	protected GameInfo info;
	protected FullGameHint hint;
	protected Timer timer;
	protected ScreenTouchHandler touchHandler;
	protected String modeCompleteTime;
	protected int ichigusFound;
	protected int selectedCardCount;

	protected final IScreenTouchListener touchListener = new IScreenTouchListener() {
		@Override
		public void onScreenTouched() {
			notifyNewGame();
		}
	};

	public FullGameMode() {
		cards = new FullGameCards();
		dealer = new FullGameCardDealer(cards);
		info = new GameInfo(7, 25);
		touchHandler = new ScreenTouchHandler();
		timer = new Timer();
		timer.setInterval(1);

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

	protected String getTimeString() {
		int elapsed = (int) timer.getTotalElapsedTime();
		int min = elapsed / 60;
		int sec = elapsed % 60;

		return (min < 10 ? ("0" + min) : ("" + min)) +
				":" +
				(sec < 10 ? ("0" + sec) : ("" + sec));
	}

	protected void notifyModeEnd() {
		touchHandler.activate(touchListener);
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	protected void notifyNewGame() {
		touchHandler.deactivate();
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
				notifyModeEnd();
			}
		}
	}

	public void startMode() {
		cards.empty();
		dealer.reset();
		touchHandler.deactivate();
		ichigusFound = 0;
		selectedCardCount = 0;
		timer.start();
		hint.activate();
	}

	public void endMode() {
		IchiguResources.playSoundTimeUp();
		modeCompleteTime = getTimeString();
		timer.stop();
		cards.empty();
		deactivateCards();
		hint.deactivate();
	}

	public void exitMode() {
		deactivateCards();
		cards.empty();
		timer.stop();
		hint.deactivate();
	}

	public void drawGame() {
		drawTime();
		drawCards();
		drawRemainingCards();
	}

	public abstract void drawResult();

	protected void drawCards() {
		cards.draw();
	}

	protected void drawRemainingCards() {
		info.draw(getDealer().getIndex() + "/" + Card.CardsInDeck, TextDrawer.AlignS, 55);
	}

	protected void drawTime() {
		info.draw(getTimeString(), TextDrawer.AlignS, 5);
	}

}
