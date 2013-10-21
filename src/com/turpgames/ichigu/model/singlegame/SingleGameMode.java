package com.turpgames.ichigu.model.singlegame;

import com.turpgames.ichigu.model.Card;
import com.turpgames.ichigu.model.IchiguMode;
import com.turpgames.ichigu.model.ImmediateScore;
import com.turpgames.ichigu.utils.R;

public abstract class SingleGameMode extends IchiguMode {
	protected SingleGameCards cards;
	protected ImmediateScore immediateScore;
	
	protected SingleGameMode() {
		cards = new SingleGameCards();
		dealer = new SingleGameCardDealer(cards);
		immediateScore = new ImmediateScore();
	}

	public SingleGameCards getCards() {
		return cards;
	}

	public void onCardSelected(Card selectedCard) {
		selectedCard.deselect();
		int score = cards.checkScore(selectedCard);
		if (score > 0) {
			notifyIchiguFound();
			immediateScore.show("test", 1000);
		}
		else
			notifyInvalidIchiguSelected();
	}

	public void activateCards() {
		for (int i = 0; i < SingleGameCards.CardToSelectCount; i++)
			cards.getCardsToSelect(i).activate(modeListener);
	}

	public void deactivateCards() {
		for (int i = 0; i < SingleGameCards.CardToSelectCount; i++) {
			if (cards.getCardsToSelect(i) != null)
				cards.getCardsToSelect(i).deactivate();
		}
	}

	public void updateCardLocations() {
		for (int i = 0; i < cards.getLength(); i++)
			cards.get(i).getLocation().set(R.learningModeScreen.layout.positions[i]);
	}

	public boolean exitMode() {
		((SingleGameCardDealer) dealer).abortDeal();
		deactivateCards();
		cards.empty();
		return true;
	}
}