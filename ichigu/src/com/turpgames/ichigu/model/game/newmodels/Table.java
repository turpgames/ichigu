package com.turpgames.ichigu.model.game.newmodels;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.ICardListener;

public abstract class Table implements IDealerListener, ICardListener {
	protected Dealer dealer;
	
	protected List<Card> cardsOnTable;
	protected List<Card> selectedCards;
	private ITableListener listener;

	protected int dealtCardCount;

	protected boolean isFirstDeal;
	
	public Table() {
		dealtCardCount = 0;
		cardsOnTable = new ArrayList<Card>();
		selectedCards = new ArrayList<Card>();
		setDealer();
	}
	
	abstract protected void setDealer();
	
	abstract public boolean isIchiguAttempted();
	
	abstract public boolean isIchiguFound();
	
	public void afterInvalidIchiguSelected() {
		
	}

	public void afterIchiguFound() {
		
	}
	
	abstract public List<Card> getCardsToDealIn();
	
	abstract public List<Card> getCardsToDealOut();
	

	@Override
	public final void onCardTapped(Card card) {
		if (dealer.isWorking())
			return;
		concreteCardTapped(card);
		if (isIchiguAttempted()) {
			if (isIchiguFound()) {
				listener.onIchiguFound();
			}
			else {
				listener.onInvalidIchiguSelected();
			}
		}
		listener.onCardTapped(card);
	}

	@Override
	public final void onDealEnded(List<Card> dealtIn, List<Card> dealtOut) {
		listener.onDealEnded();
		concreteDealEnded(dealtIn, dealtOut);
		for(Card card : selectedCards)
			card.deselect();
		selectedCards.clear();
	}
	
	abstract protected void concreteDealEnded(List<Card> dealtIn, List<Card> dealtOut);
	
	abstract protected void concreteCardTapped(Card card);

	abstract public void openCloseCards(boolean open);

	public void deal() {
		dealer.deal(getCardsToDealIn(), getCardsToDealOut());
	}

	abstract public void start();

	abstract public void end();
	
	abstract public void reset();

	public void setListener(ITableListener controller) {
		listener = controller;
	}

	public void drawCards() {
		for (Card card : cardsOnTable)
			card.draw();
		dealer.drawCards();
	}

	abstract public List<Card> getCardsForHints();

	public int getDealtCardCount() {
		return dealtCardCount;
	}

	public boolean isFirstDeal() {
		return isFirstDeal;
	}
}
