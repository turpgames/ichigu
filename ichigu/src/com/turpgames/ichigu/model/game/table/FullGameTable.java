package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.Deck;
import com.turpgames.ichigu.model.game.dealer.FullGameDealer;

public class FullGameTable extends RegularGameTable {
	
	private Deck deck;
	private List<Card> extraCards;
	private boolean areExtraCardsOpened;
	private FullGameHint hint;
	
	private List<Card> toDealOut;
	private List<Card> toDealIn;
	public FullGameTable() {
		super();
		deck = new Deck(this);
		hint = new FullGameHint(this);
		extraCards = new ArrayList<Card>();
		toDealOut = new ArrayList<Card>();
		toDealIn = new ArrayList<Card>();
	}
	
	@Override
	protected void setDealer() {
		dealer = new FullGameDealer(this);
	}

	@Override
	public void afterInvalidIchiguSelected() {
		for(Card card : selectedCards)
			card.deselect();
		selectedCards.clear();
	}
	
	@Override
	public void concreteDealEnded() {
		// remove and deactivate dealt-out cards
		cardsOnTable.removeAll(toDealOut);
		for(Card card : toDealOut)
			card.deactivate();

		// dealt-in cards are first removed than added back to prevent having two of each extra card
		cardsOnTable.removeAll(toDealIn);
		cardsOnTable.addAll(toDealIn);
		
		
		for(Card card : cardsOnTable)
			card.activate();
		
		extraCards.clear();
		
		if (toDealIn.size() > 3) {
			for(Card card : toDealIn.subList(0, toDealIn.size() - 3))
				card.open(true);

			// calculate dealt-in cards
			if (isFirstDeal) {
				dealtCardCount += 15;
				isFirstDeal = false;
			}
			else {
				dealtCardCount += 3;
			}
			
			for(Card card : toDealIn.subList(toDealIn.size() - 3, toDealIn.size())) {
				if (card != null) {
					extraCards.add(card);
				}
			}	
		}
		else if (toDealIn.size() == 3){
			for(Card card : toDealIn)
				card.open(true);
		}
		
		areExtraCardsOpened = false;
		
		hint.update();
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
	public void populateCardsForHints(List<Card> cards) {
		cards.clear();
		cards.addAll(cardsOnTable);
		if (!areExtraCardsOpened)
			cards.removeAll(extraCards);
	}
	
	@Override
	public List<Card> getCardsToDealIn() {
		toDealIn.clear();

		int ichiguCount = 0;
		if (isFirstDeal) {
			discardFirst66(); // TODO FOR TEST: uncomment to try full game end
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
			toDealIn.addAll(cardsOnTable);
			toDealIn.removeAll(selectedCards);
			
			int dealingIn = 0;
			do {
				for (Card card : toDealIn.subList(toDealIn.size() - dealingIn, toDealIn.size())) {
					deck.giveBackRandomCard(card);
					toDealIn.remove(card);
				}
				dealingIn = 0;
				for (int i = 0; i < 3; i++) {
					Card card = deck.getRandomCard();
					if (card != null) {
						dealingIn++;
						toDealIn.add(card);
					}
				}
				ichiguCount = Card.getIchiguCount(toDealIn);
			}
			while(ichiguCount == 0 && dealingIn != 0);
			
			toDealIn.removeAll(cardsOnTable);
			toDealIn.addAll(0, extraCards);
			toDealIn.removeAll(selectedCards);
		}
		return toDealIn;
	}

	@Override
	public List<Card> getCardsToDealOut() {
		toDealOut.clear();
		if (!isFirstDeal()) {
			// the selected extra cards are put to end
			for (Card card : selectedCards)
				if (!extraCards.contains(card))
					toDealOut.add(card);
			
			for (Card card : selectedCards)
				if (extraCards.contains(card))
					toDealOut.add(card);
		}
		return toDealOut;
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
	public void onDeckFinished() {
		
	}

	@Override
	public void openCloseCards(boolean open) {
		for (Card card : cardsOnTable)
			card.open(open);

		for (Card card : extraCards)
			card.open(open && areExtraCardsOpened);
	}

	@Override
	public void reset() {
		deck.reset();
		cardsOnTable.clear();
		selectedCards.clear();
		extraCards.clear();
		isFirstDeal = true;
		dealtCardCount = 0;
	}

	public boolean showHint(boolean triple) {
		return hint.showHint(triple);
	}

	@Override
	public void start() {
		deck.start();
		isFirstDeal = true;
		dealtCardCount = 0;
	}

	@Override
	protected void checkIfTableFinished() {
		if (Card.getIchiguCount(cardsOnTable) == 0)
			listener.onTableFinished();
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
			hint.update();
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

	protected void discardFirst66() {
		for(int i = 0; i < 66; i++)
			deck.getRandomCard();
		dealtCardCount = 66;
	}
	
	@Override
	protected IFullGameTableListener getListener() {
		return (IFullGameTableListener)listener; 
	}
}
