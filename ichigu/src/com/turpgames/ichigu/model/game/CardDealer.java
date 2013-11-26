package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.util.Utils;

public abstract class CardDealer {
	protected Card[] deck;
	protected ICardDealerListener listener;

	protected CardDealer() {
		deck = Card.newDeck();
	}

	public abstract void deal();

	public abstract void abortDeal();

	public void reset() {
		boolean hasNullCard = false;
		
		for (int i = 0; i < deck.length; i++) {
			if (deck[i] == null) {
				hasNullCard = true;
				continue;
			}
			deck[i].deselect();
			deck[i].deactivate();
			deck[i].getColor().a = 1;
		}

		if (hasNullCard)
			deck = Card.newDeck();
		else
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
