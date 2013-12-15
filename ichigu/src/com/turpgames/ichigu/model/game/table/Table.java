package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.ICardListener;
import com.turpgames.ichigu.model.game.dealer.Dealer;
import com.turpgames.ichigu.model.game.dealer.IDealerListener;

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
	
	public void deal() {
		List<Card> out = getCardsToDealOut();
		List<Card> in = getCardsToDealIn();
		for (int i = 0; i < out.size(); i++)
			if (in.contains(out.get(i))) {
				in = getCardsToDealIn();
				i = 0;
			}
		for(Card card : in)
			card.open(false);
		for(Card card : out)
			card.open(true);
		dealer.deal(in, out);
	}
	
	public void draw() {
		for (Card card : cardsOnTable)
			card.draw();
		dealer.drawCards();
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
		if (dealer.isWorking())
			return;
		concreteCardTapped(card);
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

	abstract public void openCloseCards(boolean open);

	abstract public void reset();

	public void setListener(ITableListener controller) {
		listener = controller;
	}
	
	public void showHint() {
		
	}
	
	abstract public void start();

	protected void checkIfTableFinished() {
		
	}
	
	abstract protected void concreteCardTapped(Card card);

	abstract protected void concreteDealEnded(List<Card> dealtIn, List<Card> dealtOut);

	protected List<Card> getCardsForHints() {
		return null;
	}

	abstract protected List<Card> getCardsToDealIn();

	abstract protected List<Card> getCardsToDealOut();

	protected ITableListener getListener() {
		return listener;
	}

	abstract protected void setDealer();
}
