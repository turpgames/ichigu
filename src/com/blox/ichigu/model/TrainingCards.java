package com.blox.ichigu.model;

class TrainingCards {
	public static final int ReadyCardCount = 2;
	public static final int CardToSelectCount = 3;
	public static final int TotalCardCount = ReadyCardCount + CardToSelectCount;

	private Card[] cards;

	TrainingCards() {
		cards = new Card[ReadyCardCount + CardToSelectCount];
	}

	public boolean isEmpty() {
		return cards[0] == null;
	}

	public void empty() {
		for (int i = 0; i < cards.length; i++)
			cards[i] = null;
	}

	public int getLength() {
		return cards.length;
	}

	public Card get(int i) {
		return cards[i];
	}

	public Card[] getAllCards() {
		return cards;
	}

	public Card getCardsToSelect(int i) {
		return cards[ReadyCardCount + i];
	}

	public void setCardsOnTable(Card... cards) {
		for (int i = 0; i < ReadyCardCount; i++)
			this.cards[i] = cards[i];
	}

	public void setCardsToSelect(Card... cards) {
		for (int i = 0; i < CardToSelectCount; i++)
			this.cards[i + ReadyCardCount] = cards[i];
	}

	public int checkScore(Card selectedCard) {
		return Card.getIchiguScore(cards[0], cards[1], selectedCard);
	}
}