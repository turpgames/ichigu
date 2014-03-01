package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.Deck;
import com.turpgames.ichigu.model.game.ICardListener;
import com.turpgames.ichigu.model.game.dealer.Dealer;
import com.turpgames.ichigu.model.game.dealer.IDealerListener;

public abstract class Table implements IDealerListener, ICardListener {
	protected Dealer dealer;
	protected Deck deck;
	
	protected List<Card> cardsOnTable;
	protected List<Card> selectedCards;
	protected ITableListener listener;

	protected int dealtCardCount;

	protected boolean isFirstDeal;
	
	public Table() {
		dealtCardCount = 0;
		cardsOnTable = new ArrayList<Card>();
		selectedCards = new ArrayList<Card>();
		setDealer();
	}

	abstract protected void setDealer();
	
	public void deal() {
		List<Card> out = getCardsToDealOut();
		List<Card> in = getCardsToDealIn();
		if (in == null)
			return;
		for (int i = 0; i < out.size(); i++) {
			if (in.contains(out.get(i))) {
				in = getCardsToDealIn();
				i = 0;
			}
		}
		dealer.deal(in, out);
	}
	
	public void draw() {
		for (Card card : cardsOnTable)
			card.draw();
		dealer.draw();
	}
	
	abstract public void end();

	public int getDealtCardCount() {
		return dealtCardCount;
	}

	public boolean isFirstDeal() {
		return isFirstDeal;
	}
	
	@Override
	public final void onCardTapped(Card card) {
		if (dealer.isDealing())
			return;
		concreteCardTapped(card);
		listener.onCardTapped(card);
	}
	
	@Override
	public final void onDealEnded(List<Card> out) {
		for (Card used : out) {
			deck.usedCard(used);
		}
		concreteDealEnded();
		listener.onDealEnded();
		for(Card card : selectedCards)
			card.deselect();
		selectedCards.clear();
	}
	
	@Override
	public void onDeckFinished() {
		
	}

	abstract public void openCloseCards(boolean open);

	abstract public void reset();

	public void setListener(ITableListener controller) {
		listener = controller;
	}
		
	abstract public void start();

	abstract protected void concreteCardTapped(Card card);

	abstract protected void concreteDealEnded();

	public void populateCardsForHints(List<Card> cards) {
		return;
	}

	abstract protected List<Card> getCardsToDealIn();

	abstract protected List<Card> getCardsToDealOut();

	protected ITableListener getListener() {
		return listener;
	}
}
