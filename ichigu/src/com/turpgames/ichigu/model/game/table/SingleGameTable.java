package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.model.game.Deck;
import com.turpgames.ichigu.model.game.dealer.SingleGameDealer;
import com.turpgames.utils.Util;

public class SingleGameTable extends RegularGameTable {
	private SingleGameHint hint;
	
	private List<Card> toDealIn;
	public SingleGameTable() {
		deck = new Deck(this);
		hint = new SingleGameHint(this);
		toDealIn = new ArrayList<Card>();
	}

	@Override
	public void concreteDealEnded() {
		cardsOnTable.clear();
		cardsOnTable.addAll(toDealIn);
		for(Card card : toDealIn) {
			card.open(true);
		}
		for(int i = 2; i < 5; i++)
			toDealIn.get(i).activate();
		
		hint.update();
	}
	
	@Override
	public void end() {
		deck.start();
		selectedCards.clear();
		cardsOnTable.clear();
	}

	@Override
	public void populateCardsForHints(List<Card> cards) {
		cards.clear();
		cards.add(cardsOnTable.get(0));
		cards.add(cardsOnTable.get(1));
		
		for (int i = 2; i < cardsOnTable.size(); i++) {
			if (Card.isIchigu(cards.get(0), cards.get(1), cardsOnTable.get(i))) {
				cards.add(cardsOnTable.get(i));
				break;
			}
		}
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
		Util.Random.shuffle(toSelect);
		
		toDealIn.clear();
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
	public void openCloseCards(boolean open) {
		for (Card card : cardsOnTable) {
			card.open(open);
		}
	}

	@Override
	public void reset() {
		deck.start();
		selectedCards.clear();
		cardsOnTable.clear();
	}

	public void showHint() {
		hint.showNextHint();
	}

	@Override
	public void start() {
		deck.start();
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
	protected IRegularTableListener getListener() {
		return (IRegularTableListener) listener;
	}
	
	@Override
	protected void setDealer() {
		dealer = new SingleGameDealer(this);
	}
}
