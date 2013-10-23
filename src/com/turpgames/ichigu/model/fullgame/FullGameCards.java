package com.turpgames.ichigu.model.fullgame;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;

class FullGameCards {
	public static final int ActiveCardCount = 12;
	public static final int ExtraCardCount = 3;
	public static final int IchiguCardCount = 3;
	public static final int TotalCardsOnTable = ActiveCardCount + ExtraCardCount;

	private final Card[] allCards;
	private final Card[] extraCards;
	private final Card[] activeCards;
	private final Card[] selectedCards;

	public FullGameCards() {
		allCards = new Card[ActiveCardCount + ExtraCardCount];
		extraCards = new Card[ExtraCardCount];
		activeCards = new Card[ActiveCardCount];
		selectedCards = new Card[IchiguCardCount];
	}

	Card getSelectedCard(int i) {
		return selectedCards[i];
	}

	public Card getCard(int i) {
		return allCards[i];
	}

	public void setCard(int i, Card card) {
		if (i < ActiveCardCount)
			setActiveCard(i, card);
		else
			setExtraCard(i - ActiveCardCount, card);
	}

	public Card getActiveCard(int i) {
		return activeCards[i];
	}

	public void setActiveCard(int i, Card card) {
		activeCards[i] = card;
		allCards[i] = card;
	}

	public Card getExtraCard(int i) {
		return extraCards[i];
	}

	public void setExtraCard(int i, Card card) {
		extraCards[i] = card;
		allCards[i + ActiveCardCount] = card;
	}

	public boolean isEmpty(int i) {
		return allCards[i] == null;
	}

	public boolean isActiveCardEmpty(int i) {
		return activeCards[i] == null;
	}

	public boolean isExtraCardEmpty(int i) {
		return extraCards[i] == null;
	}

	public void empty() {
		emptySelectedCards();
		for (int i = 0; i < allCards.length; i++)
			setCard(i, null);
	}

	public void emptySelectedCards() {
		updateSelectedCards();
		int x = 0;
		for (int i = 0; i < allCards.length; i++) {
			if (allCards[i] == null || !allCards[i].isSelected())
				continue;

			selectedCards[x++] = null;
			setCard(i, null);
		}
	}

	public int getScore() {
		updateSelectedCards();
		return Card.getIchiguScore(selectedCards[0], selectedCards[1], selectedCards[2]);
	}

	public void deselectCards() {
		updateSelectedCards();
		for (int i = 0; i < selectedCards.length; i++)
			selectedCards[i].deselect();
	}

	private void updateSelectedCards() {
		int x = 0;
		for (int i = 0; i < allCards.length; i++) {
			selectedCards[x] = null;
			if (allCards[i] != null && allCards[i].isSelected())
				selectedCards[x++] = allCards[i];
			if (x == IchiguCardCount)
				break;
		}
	}

	void updateHint(FullGameHint hint) {
		hint.update(allCards);
	}

	public void draw() {
		for (int i = 0; i < allCards.length; i++) {
			if (allCards[i] != null)
				allCards[i].draw();
		}
	}
	
	public CardAttributes[] getIchiguCards() {
		CardAttributes[] ready = new CardAttributes[3];
		ready[0] = selectedCards[0].getAttributes();
		ready[1] = selectedCards[1].getAttributes();
		ready[2] = selectedCards[2].getAttributes();
		return ready;
	}
}