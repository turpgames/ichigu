package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.Deck;
import com.turpgames.ichigu.model.game.dealer.FullGameCardsIn;
import com.turpgames.ichigu.model.game.dealer.FullGameDealer;

public class FullGameTable extends RegularGameTable {
	
	private List<Card> extraCards;
	private boolean areExtraCardsOpened;
	private FullGameHint hint;
	
	private List<Card> toDealOut;
	private FullGameCardsIn toDealIn;
	public FullGameTable() {
		super();
		deck = new Deck(this);
		hint = new FullGameHint(this);
		extraCards = new ArrayList<Card>();
		toDealOut = new ArrayList<Card>();
		toDealIn = new FullGameCardsIn();
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
		
		extraCards.clear();
		for(Card card : toDealIn.getExtras())
			extraCards.add(card);
		
		for(Card card : toDealIn.getOthers())
			card.open(true);

		// calculate dealt-in cards
		if (isFirstDeal) {
			cardsOnTable.addAll(toDealIn);
			dealtCardCount += toDealIn.size();
			isFirstDeal = false;
			for(Card card : toDealIn)
				card.activate();
		}
		else {
			dealtCardCount += toDealIn.getExtras().size();
			cardsOnTable.addAll(toDealIn.getExtras());
			for(Card card : toDealIn.getExtras())
				card.activate();
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
			if (Game.isDebug())
				discardFirst66();
			do {
				for(Card card : toDealIn.getOthers())
					deck.giveBackUnusedCard(card);
				toDealIn.clear();
				for (int i = 0; i < 12; i++)
					toDealIn.addOther(deck.getRandomCard());
				for (int i = 0; i < 3; i++)
					toDealIn.addExtra(deck.getRandomCard());
				ichiguCount = Card.getIchiguCount(toDealIn);
			}
			while(ichiguCount == 0);
		}
		else {
			toDealIn.addAllOthers(extraCards);
			toDealIn.removeAllOthers(selectedCards);
			int dealingIn = 0;
			do {
				for (Card card : toDealIn.subList(toDealIn.size() - dealingIn, toDealIn.size())) {
					deck.giveBackUnusedCard(card);
					toDealIn.removeExtra(card);
				}
				dealingIn = 0;
				for (int i = 0; i < 3; i++) {
					Card card = deck.getRandomCard();
					if (card != null) {
						dealingIn++;
						toDealIn.addExtra(card);
					}
				}
				ichiguCount = Card.getIchiguCount(toDealIn);
			}
			while(ichiguCount == 0 && dealingIn != 0);
			
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
		for(int i = 0; i < 66; i++) {
			deck.usedCard(deck.getRandomCard());
		}
		dealtCardCount = 66;
	}
	
	@Override
	protected IFullGameTableListener getListener() {
		return (IFullGameTableListener)listener; 
	}
}
