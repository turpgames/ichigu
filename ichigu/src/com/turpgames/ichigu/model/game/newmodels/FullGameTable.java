package com.turpgames.ichigu.model.game.newmodels;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;

public class FullGameTable extends Table {

	public static final int ActiveCardCount = 12;
	public static final int ExtraCardCount = 3;
	public static final int IchiguCardCount = 3;
	public static final int TotalCardsOnTable = ActiveCardCount + ExtraCardCount;

	public final static int cols = 5;
	
	private Deck deck;
	private List<Card> extraCards;
	private boolean areExtraCardsOpened;
	
	public FullGameTable() {
		super();
		deck = new Deck(this);
		extraCards = new ArrayList<Card>();
	}

	@Override
	protected void setDealer() {
		dealer = new FullGameDealer(this);
	}
	
	@Override
	public void onDeckFinished() {
		
	}

	@Override
	public void concreteDealEnded(List<Card> dealtIn, List<Card> dealtOut) {
		dealtCardCount += dealtIn.size();
		cardsOnTable.removeAll(dealtOut);
		cardsOnTable.removeAll(dealtIn);
		cardsOnTable.addAll(dealtIn);
		for(Card card : cardsOnTable)
			card.activate();
		for(Card card : dealtIn.subList(3, dealtIn.size())) {
			card.open(true);
		}
		
		extraCards.clear();
		for(Card card : dealtIn.subList(0, 3)) {
			extraCards.add(card);
		}

		isFirstDeal = false;
		areExtraCardsOpened = false;
	}

	@Override
	public void afterIchiguFound() {
		
	}

	@Override
	public boolean isIchiguAttempted() {
		return selectedCards.size() == 3;
	}

	@Override
	public boolean isIchiguFound() {
		return Card.isIchigu(selectedCards.get(0), selectedCards.get(1), selectedCards.get(2));
	}

	@Override
	public List<Card> getCardsToDealIn() {
		List<Card> toDealIn = new ArrayList<Card>();
		
		if (isFirstDeal) {
			int ichiguCount = 0;
			do {
				for(Card card : toDealIn)
					deck.giveBackRandomCard(card);
				for (int i = 0; i < 15; i++)
					toDealIn.add(deck.getRandomCard());
				ichiguCount = Card.getIchiguCount(toDealIn);
			}
			while(ichiguCount == 0);
		}
		else {
			toDealIn.add(deck.getRandomCard());
			toDealIn.add(deck.getRandomCard());
			toDealIn.add(deck.getRandomCard());
			List<Card> tempExtraCards = new ArrayList<Card>();
			tempExtraCards.addAll(extraCards);
			for (Card card : selectedCards)
				tempExtraCards.remove(card);
			toDealIn.addAll(tempExtraCards);
		}
		return toDealIn;
	}

	@Override
	public List<Card> getCardsToDealOut() {
		if (isFirstDeal()) {
			return new ArrayList<Card>();
		}
		// the selected extra cards are put to end
		List<Card> toDealOut = new ArrayList<Card>();
		List<Card> temp = new ArrayList<Card>();
		for (Card card : selectedCards)
			if (!extraCards.contains(card))
				toDealOut.add(card);
			else
				temp.add(card);
		toDealOut.addAll(temp);
		return toDealOut;
	}

	@Override
	public void afterInvalidIchiguSelected() {
		for(Card card : selectedCards)
			card.deselect();
		selectedCards.clear();
	}

	@Override
	public void openCloseCards(boolean open) {
		for (Card card : cardsOnTable)
			card.open(open);

		for (Card card : extraCards)
			card.open(open && areExtraCardsOpened);
	}

	@Override
	protected void concreteCardTapped(Card card) {
		// open extra cards
		if (areExtraCardsOpened == false && extraCards.contains(card)) {
			card.deselect();
			for (Card extra : extraCards)
				extra.open(true);
			areExtraCardsOpened = true;
			cardsOnTable.addAll(extraCards);
			return;
		}
		
		// add card to selected
		if (cardsOnTable.contains(card)){ 
			if (selectedCards.contains(card)) {
				selectedCards.remove(card);
				card.deselect();
			}
			else {
				selectedCards.add(card);
				card.select();
			}
		}
	}

	@Override
	public List<Card> getCardsForHints() {
		List<Card> hintList = new ArrayList<Card>();
		hintList.addAll(cardsOnTable);
		if (!areExtraCardsOpened)
			hintList.removeAll(extraCards);
		return hintList;
	}

	@Override
	public void start() {
		deck.start();
		dealer = new FullGameDealer(this);
		isFirstDeal = true;
		dealtCardCount = 0;
	}

	@Override
	public void end() {
		deck.end();
		cardsOnTable.clear();
		selectedCards.clear();
		extraCards.clear();
		dealtCardCount = 0;
	}

	@Override
	public void reset() {
		deck.reset();
		cardsOnTable.clear();
		selectedCards.clear();
		extraCards.clear();
		dealer = new FullGameDealer(this);
		isFirstDeal = true;
		dealtCardCount = 0;
	}
}
