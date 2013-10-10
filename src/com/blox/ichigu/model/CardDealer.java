package com.blox.ichigu.model;

import com.blox.framework.v0.util.Utils;

abstract class CardDealer {
	protected final Card[] deck;
	protected ICardDealerListener listener;

	protected CardDealer() {
		deck = Card.newDeck();
	}

	public abstract void deal();

	public void reset() {
		for (int i = 0; i < deck.length; i++) {
			deck[i].deselect();
			deck[i].deactivate();
			deck[i].getColor().a = 1;
		}
		Utils.shuffle(deck);
	}

	public void setListener(ICardDealerListener listener) {
		this.listener = listener;
	}

	protected void notifyDealEnd() {
		if (listener != null)
			listener.onDealEnd();
	}
}
