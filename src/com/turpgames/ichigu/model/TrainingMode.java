package com.turpgames.ichigu.model;

import com.turpgames.ichigu.utils.R;

public abstract class TrainingMode extends IchiguMode {
	protected TrainingCards cards;

	protected TrainingMode() {
		cards = new TrainingCards();
		dealer = new TrainingCardDealer(cards);
	}

	public TrainingCards getCards() {
		return cards;
	}

	public void onCardSelected(Card selectedCard) {
		selectedCard.deselect();
		int score = cards.checkScore(selectedCard);
		if (score > 0)
			notifyIchiguFound();
		else
			notifyInvalidIchiguSelected();
	}

	public void activateCards() {
		for (int i = 0; i < TrainingCards.CardToSelectCount; i++)
			cards.getCardsToSelect(i).activate(modeListener);
	}

	public void deactivateCards() {
		for (int i = 0; i < TrainingCards.CardToSelectCount; i++) {
			if (cards.getCardsToSelect(i) != null)
				cards.getCardsToSelect(i).deactivate();
		}
	}

	public void updateCardLocations() {
		for (int i = 0; i < cards.getLength(); i++)
			cards.get(i).getLocation().set(R.learningModeScreen.layout.positions[i]);
	}

	public boolean exitMode() {
		((TrainingCardDealer) dealer).abortDeal();
		deactivateCards();
		cards.empty();
		return true;
	}
}