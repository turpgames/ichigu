package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.model.game.Deck;
import com.turpgames.ichigu.model.game.dealer.SingleGameDealer;

public class SingleGameTable extends RegularGameTable {
	private Deck deck;

	private SingleGameHint hint;
	
	public SingleGameTable() {
		deck = new Deck(this);
		hint = new SingleGameHint();
	}

	@Override
	protected void setDealer() {
		dealer = new SingleGameDealer(this);
	}
	
	@Override
	protected IRegularTableListener getListener() {
		return (IRegularTableListener) listener;
	}

	@Override
	public void concreteDealEnded(List<Card> dealtIn, List<Card> dealtOut) {
		cardsOnTable.clear();
		cardsOnTable.addAll(dealtIn);
		for(Card card : dealtIn) {
			card.open(true);
		}
		for(int i = 2; i < 5; i++)
			dealtIn.get(i).activate();
		
		hint.update(getCardsForHints());
	}

	@Override
	public boolean isIchiguAttempted() {
		return selectedCards.size() == 1;
	}

	@Override
	public boolean isIchiguFound() {
		if (cardsOnTable.size() != 5 || selectedCards.size() != 1)
			return false;
		return Card.isIchigu(cardsOnTable.get(0), cardsOnTable.get(1), selectedCards.get(0));
	}
	
	@Override
	public List<Card> getCardsToDealIn() {
		Card card1, card2, card3, card4, card5 = null;
		CardAttributes third;
		do {
			do {
				if((card1 = deck.getRandomCard()) == null || (card2 = deck.getRandomCard()) == null) {
					deck.recycleDeck();
					card1 = deck.getRandomCard();
					card2 = deck.getRandomCard();
				}
	
				// third card that makes an ichigu with the first two
				third = CardAttributes.getThirdCardAttributes(card1.getAttributes(), card2.getAttributes());
			}
			while((card3 = deck.getCardWithAttributes(third)) == null);
			
			if((card4 = deck.getRandomCard()) == null || (card5 = deck.getRandomCard()) == null) {
				deck.recycleDeck();
			}
		}
		while(card4 == null || card5 == null);
		
		List<Card> toSelect = new ArrayList<Card>();
		toSelect.add(card3);
		toSelect.add(card4);
		toSelect.add(card5);
		Utils.shuffle(toSelect);
		
		List<Card> toDealIn = new ArrayList<Card>();
		toDealIn.add(card1);
		toDealIn.add(card2);
		toDealIn.addAll(toSelect);
		for(Card card : toDealIn)
			card.open(false);
		return toDealIn;
	}

	@Override
	public List<Card> getCardsToDealOut() {
		return cardsOnTable;
	}
	
	@Override
	public void openCloseCards(boolean open) {
		for (Card card : cardsOnTable) {
			card.open(open);
		}
	}

	@Override
	protected void concreteCardTapped(Card card) {
		card.deselect();
		selectedCards.clear();
		selectedCards.add(card);
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
		List<Card> hintCards = new ArrayList<Card>();
		hintCards.add(cardsOnTable.get(0));
		hintCards.add(cardsOnTable.get(1));
		
		for (int i = 2; i < cardsOnTable.size(); i++) {
			if (Card.isIchigu(hintCards.get(0), hintCards.get(1), cardsOnTable.get(i))) {
				hintCards.add(cardsOnTable.get(i));
				break;
			}
		}
		return hintCards;
	}

	@Override
	public void start() {
		deck.start();
	}

	@Override
	public void end() {
		deck.end();
		selectedCards.clear();
		cardsOnTable.clear();
	}

	@Override
	public void reset() {
		deck.reset();
		selectedCards.clear();
		cardsOnTable.clear();
	}
	
	@Override
	public void showHint() {
		hint.showNextHint();
	}
}