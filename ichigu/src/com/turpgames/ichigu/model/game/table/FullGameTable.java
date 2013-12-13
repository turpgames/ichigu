package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.Deck;
import com.turpgames.ichigu.model.game.dealer.FullGameDealer;

public class FullGameTable extends RegularGameTable {

	public static final int ActiveCardCount = 12;
	public static final int ExtraCardCount = 3;
	public static final int IchiguCardCount = 3;
	public static final int TotalCardsOnTable = ActiveCardCount + ExtraCardCount;

	public final static int cols = 5;
	
	private Deck deck;
	private List<Card> extraCards;
	private boolean areExtraCardsOpened;
	private FullGameHint hint;
	
	public FullGameTable() {
		super();
		deck = new Deck(this);
		extraCards = new ArrayList<Card>();
		hint = new FullGameHint(this);
	}

	@Override
	protected void setDealer() {
		dealer = new FullGameDealer(this);
	}
	
	@Override
	protected IFullGameTableListener getListener() {
		return (IFullGameTableListener)listener; 
	}
	
	@Override
	public void onDeckFinished() {
		
	}

	@Override
	public void concreteDealEnded(List<Card> dealtIn, List<Card> dealtOut) {
		// remove and deactivate dealt-out cards
		cardsOnTable.removeAll(dealtOut);
		for(Card card : dealtOut)
			card.deactivate();

		// dealt-in cards are first removed than added back to prevent having two of each extra card
		cardsOnTable.removeAll(dealtIn);
		cardsOnTable.addAll(dealtIn);
		
		/*
		 * the null values come when the deck is already finished but there are ichigus on the table
		 * they cause problems with the existing operations so they are removed or checked for
		 * on some points in FullGameDealer and FullGameTable
		 */
		List<Card> nullList = new ArrayList<Card>();
		nullList.add(null);
		cardsOnTable.removeAll(nullList);
		
		for(Card card : cardsOnTable)
			card.activate();
		
		for(Card card : dealtIn.subList(3, dealtIn.size()))
			card.open(true);

		// calculate dealt-in cards
		if (isFirstDeal) {
			dealtCardCount += 15;
			isFirstDeal = false;
		}
		else {
			List<Card> tmp = new ArrayList<Card>();
			tmp.addAll(dealtIn);
			tmp.removeAll(nullList);
			tmp.removeAll(extraCards);
			dealtCardCount += tmp.size();
		}
		
		extraCards.clear();
		for(Card card : dealtIn.subList(0, 3)) {
			if (card != null) {
				extraCards.add(card);
			}
		}
		
		areExtraCardsOpened = false;

		hint.update(getCardsForHints());
	}

	@Override
	protected void checkIfTableFinished() {
		if (Card.getIchiguCount(cardsOnTable) == 0)
			listener.onTableFinished();
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

		int ichiguCount = 0;
		if (isFirstDeal) {
//			discardFirst66(); // TODO FOR TEST: uncomment to try full game end
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
			List<Card> tempAllCards = new ArrayList<Card>();
			tempAllCards.addAll(cardsOnTable);
			tempAllCards.removeAll(selectedCards);
			do {
				for (Card card : toDealIn) {
					deck.giveBackRandomCard(card);
					tempAllCards.remove(card);
				}
				for (int i = 0; i < 3; i++) {
					Card card = deck.getRandomCard();
					toDealIn.add(card);
					tempAllCards.add(card);
				}
				ichiguCount = Card.getIchiguCount(tempAllCards);
			}
			while(ichiguCount == 0);
			
			List<Card> tempExtraCards = new ArrayList<Card>();
			tempExtraCards.addAll(extraCards);
			for (Card card : selectedCards)
				tempExtraCards.remove(card);
			toDealIn.addAll(tempExtraCards);
		}
		return toDealIn;
	}

	protected void discardFirst66() {
		for(int i = 0; i < 66; i++)
			deck.getRandomCard();
		dealtCardCount = 66;
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

			if (hint.getIchiguCount() > 0)
				getListener().onOpenedExtraCardsWhileThereIsIchigu();
			hint.update(getCardsForHints());
		}
		// add card to selected
		else if (cardsOnTable.contains(card)){
			if (selectedCards.contains(card)) {
				selectedCards.remove(card);
				card.deselect();
			}
			else {
				selectedCards.add(card);
				card.select();
			}
		}
		
		if (isIchiguAttempted()) {
			if (isIchiguFound()) {
				getListener().onIchiguFound();
			}
			else {
				getListener().onInvalidIchiguSelected();
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
	
	@Override
	public void showHint() {
		hint.showHint();
	}

	@Override
	public void draw() {
		hint.draw();
		super.draw();
	}
}
