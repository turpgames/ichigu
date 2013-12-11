package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.ICardListener;
import com.turpgames.ichigu.model.game.dealer.Dealer;
import com.turpgames.ichigu.model.game.dealer.IDealerListener;
import com.turpgames.ichigu.model.game.dealer.ITableListener;

public abstract class Table implements IDealerListener, ICardListener {
	protected Dealer dealer;
	
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
	
	abstract public boolean isIchiguAttempted();
	
	abstract public boolean isIchiguFound();
	
	public void afterInvalidIchiguSelected() {
		
	}

	public void afterIchiguFound() {
		
	}
	
	abstract protected List<Card> getCardsToDealIn();
	
	abstract protected List<Card> getCardsToDealOut();
	

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
		concreteDealEnded(dealtIn, dealtOut);
		listener.onDealEnded();
		for(Card card : selectedCards)
			card.deselect();
		selectedCards.clear();
		checkIfTableFinished();
	}

	@Override
	public void onDeckFinished() {
		
	}
	
	protected void checkIfTableFinished() {
		
	}
	
	abstract protected void concreteDealEnded(List<Card> dealtIn, List<Card> dealtOut);
	
	abstract protected void concreteCardTapped(Card card);

	abstract public void openCloseCards(boolean open);

	public void deal() {
		List<Card> out = getCardsToDealOut();
		List<Card> in = getCardsToDealIn();
		for (int i = 0; i < out.size(); i++)
			if (in.contains(out.get(i))) {
				in = getCardsToDealIn();
				i = 0;
			}
		dealer.deal(in, out);
	}

	abstract public void start();

	abstract public void end();
	
	abstract public void reset();

	public void setListener(ITableListener controller) {
		listener = controller;
	}

	public void draw() {
		for (Card card : cardsOnTable)
			card.draw();
		dealer.drawCards();
	}

	abstract protected List<Card> getCardsForHints();

	public int getDealtCardCount() {
		return dealtCardCount;
	}

	public boolean isFirstDeal() {
		return isFirstDeal;
	}

	public void showHint() {
		
	}
}
