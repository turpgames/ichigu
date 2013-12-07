package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.util.Utils;

public abstract class CardDealer implements ICardListener {
	protected Card[] deck;
	protected ICardDealerListener listener;

	protected CardDealer() {
		deck = Card.newDeck(this);
	}
	
	abstract public void deal();

	abstract public void abortDeal();

	abstract public int getScore();

	abstract public void openCloseCards(boolean open);

	abstract public void drawCards();
	
	abstract public void activateCards();

	abstract public void deactivateCards();

	abstract public void deselectCards();

	abstract public void start();

	abstract public void end();
	
	abstract public void reset();

	abstract public boolean isIchiguAttempted();

	abstract public Card[] getCardsForHints();

	public void setListener(ICardDealerListener listener) {
		this.listener = listener;
	}
	
	public void onCardTapped(Card card) {
		if (isIchiguAttempted()) {
			if (getScore() > 0) {
				notifyIchiguFound();
			}
			else {
				deselectCards();
				notifyInvalidIchiguSelected();
			}
		}
		notifyCardTapped(card);
	}

	private void notifyCardTapped(Card card) {
		if (listener != null)
			listener.onCardTapped(card);
	}

	private void notifyIchiguFound() {
		if (listener != null)
			listener.onIchiguFound();
	}

	private void notifyInvalidIchiguSelected() {
		if (listener != null)
			listener.onInvalidIchiguSelected();
	}

	protected void notifyDealEnd() {
		if (listener != null)
			listener.onDealEnded();
		deselectCards();
	}
	
	public void resetDeck() {
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
			deck = Card.newDeck(this);
		else
			Utils.shuffle(deck);
	}

}
