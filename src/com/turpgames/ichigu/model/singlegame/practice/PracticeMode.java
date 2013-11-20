package com.turpgames.ichigu.model.singlegame.practice;

import com.turpgames.ichigu.model.display.TryAgainToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.singlegame.SingleGameCards;
import com.turpgames.ichigu.model.singlegame.SingleGameMode;

public class PracticeMode extends SingleGameMode {
	private PracticeModeHint hint;
	private TryAgainToast tryAgain;
	
	public PracticeMode() {
		hint = new PracticeModeHint();
		tryAgain = new TryAgainToast();
	}
	
	@Override
	public void onCardSelected(Card selectedCard) {
		int score = cards.checkScore(selectedCard);
		if (score == 0)
			tryAgain.show();
		super.onCardSelected(selectedCard);
	}
	
	@Override
	public void activateCards() {
		super.activateCards();

		Card[] allCards = cards.getAllCards();

		Card card3 = null;
		for (int i = 0; i < SingleGameCards.CardToSelectCount; i++) {
			if (Card.isIchigu(allCards[0], allCards[1], cards.getCardsToSelect(i))) {
				card3 = cards.getCardsToSelect(i);
				break;
			}
		}

		hint.update(allCards[0], allCards[1], card3);
		hint.activate();
	}

	@Override
	public void deactivateCards() {
		hint.deactivate();
		super.deactivateCards();
	}

	@Override
	protected void onStartMode() {
		
	}

	@Override
	protected void onEndMode() {
		
	}

	@Override
	protected boolean onExitMode() {
		hint.deactivate();
		isExitConfirmed = true;
		return super.onExitMode();
	}
	
	@Override
	public void deal() {
		tryAgain.hide();
		super.deal();
	}

	@Override
	protected void onDraw() {
		drawCards();
		hint.draw();
	}

	private void drawCards() {
		Card[] allCards = cards.getAllCards();
		for (int i = 0; i < allCards.length; i++)
			allCards[i].draw();
	}

	@Override
	protected void pauseTimer() {
		
	}

	@Override
	protected void startTimer() {
		
	}

	@Override
	protected void openCloseCards(boolean open) {
		
	}
}
