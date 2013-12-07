package com.turpgames.ichigu.model.fullgame;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;

class FullGameCards {
	public static final int ActiveCardCount = 12;
	public static final int ExtraCardCount = 3;
	public static final int IchiguCardCount = 3;
	public static final int TotalCardsOnTable = ActiveCardCount + ExtraCardCount;

	private final Card[] allCards;
	private final Card[] extraCards;
	private final Card[] activeCards;
	private final List<Card> selectedCards;

	public FullGameCards() {
		allCards = new Card[ActiveCardCount + ExtraCardCount];
		extraCards = new Card[ExtraCardCount];
		activeCards = new Card[ActiveCardCount];
		selectedCards = new ArrayList<Card>();
	}

	Card getSelectedCard(int i) {
		return selectedCards.get(i);
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

	public boolean isCardOnTable(Card card) {
		for (int i = 0; i < TotalCardsOnTable; i++)
			if (allCards[i] == card)
				return true;
		return false;
	}
	
	public void empty() {
		emptySelectedCards();
		for (int i = 0; i < allCards.length; i++)
			setCard(i, null);
	}

	public void emptySelectedCards() {
		selectedCards.clear();
	}

	public int getScore() {
		return Card.getIchiguScore(selectedCards.get(0), selectedCards.get(1), selectedCards.get(2));
	}

	public void deselectCards() {
		for (Card card : selectedCards) {
			card.deselect();
		}
		emptySelectedCards();
	}

	public Card[] getCardsForHints() {
		return allCards;
	}

	public void draw() {
		for (int i = 0; i < allCards.length; i++) {
			if (allCards[i] != null)
				allCards[i].draw();
		}
	}

	public int getNumberOfSelected() {
		return selectedCards.size();
	}

	public void switchSelect(Card card) {
		if (selectedCards.contains(card))
			selectedCards.remove(card);
		else
			selectedCards.add(card);
	}
}