package com.turpgames.ichigu.model.singlegame.practice;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.TryAgainToast;
import com.turpgames.ichigu.model.singlegame.SingleGameCards;
import com.turpgames.ichigu.model.singlegame.SingleGameMode;

public class PracticeMode extends SingleGameMode {
	private PracticeModeHint hint;
	private TryAgainToast tryAgain;
	
	public PracticeMode() {
		super();
		hint = new PracticeModeHint();
		tryAgain = new TryAgainToast();
		
		pointsInfo.getLocation().set(0, Game.getVirtualHeight() - pointsInfo.getHeight() - 45);
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
	public boolean exitMode() {
		hint.deactivate();
		return super.exitMode();
	}

	@Override
	protected void wrongCardSelected() {
		tryAgain.show(1000, 200);
		super.wrongCardSelected();
	}
	
	public void drawGame() {
		drawCards();
		hint.draw();
	}

	private void drawCards() {
		Card[] allCards = cards.getAllCards();
		for (int i = 0; i < allCards.length; i++)
			allCards[i].draw();
	}
}
