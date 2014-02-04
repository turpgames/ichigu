package com.turpgames.ichigu.model.game;

import java.util.ArrayList;
import java.util.List;

public class SudokuDeck extends Deck {

	public SudokuDeck(ICardListener table) {
		super(table);
	}

	public List<Card> get9Cards(List<Card> last9Cards) {
		List<Card> list = new ArrayList<Card>();
		Card card1, card2, card3;
		Card card4;
		Card card7;
		CardAttributes third, seventh;

		Card card5, card6 = null, card8 = null, card9 = null;
		CardAttributes sixth, eighth, ninth;
		do {
			do {
				recycleDeck();
				
				card1 = getRandomCard();
				card2 = getRandomCard();
	
				card4 = getRandomCard();
				
				third = CardAttributes.getThirdCardAttributes(card1.getAttributes(), card2.getAttributes());
				seventh = CardAttributes.getThirdCardAttributes(card1.getAttributes(), card4.getAttributes());
			}
			while((card3 = getCardWithAttributes(third)) == null ||
					(card7 = getCardWithAttributes(seventh)) == null);
	
			do {
				card5 = getRandomCard();
				
				sixth = CardAttributes.getThirdCardAttributes(card4.getAttributes(), card5.getAttributes());
				eighth = CardAttributes.getThirdCardAttributes(card2.getAttributes(), card5.getAttributes());
				ninth = CardAttributes.getThirdCardAttributes(card7.getAttributes(), eighth);
			}
			while(card5 != null && (!CardAttributes.isIchigu(card3.getAttributes(), sixth, ninth) ||
				(card6 = getCardWithAttributes(sixth)) == null ||
				(card8 = getCardWithAttributes(eighth)) == null ||
				(card9 = getCardWithAttributes(ninth)) == null));
		}
		while(card5 == null);
		
		list.add(card1);
		list.add(card2);
		list.add(card3);
		list.add(card4);
		list.add(card5);
		list.add(card6);
		list.add(card7);
		list.add(card8);
		list.add(card9);
		
		for (Card cardCheck : list) {
			if (last9Cards.contains(cardCheck)) {
				for (Card cardBack : list)
					giveBackUnusedCard(cardBack);
				return get9Cards(last9Cards);
			}
		}
		
		return list;
	}
}
