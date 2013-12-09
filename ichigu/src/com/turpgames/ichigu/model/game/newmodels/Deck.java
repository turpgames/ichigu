package com.turpgames.ichigu.model.game.newmodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.model.game.ICardListener;

public class Deck {
	private static int[] colors = new int[] { CardAttributes.Color_Red, CardAttributes.Color_Green, CardAttributes.Color_Blue };
	private static int[] shapes = new int[] { CardAttributes.Shape_Circle, CardAttributes.Shape_Square, CardAttributes.Shape_Triangle };
	private static int[] counts = new int[] { CardAttributes.Count_1, CardAttributes.Count_2, CardAttributes.Count_3 };
	private static int[] patterns = new int[] { CardAttributes.Pattern_Empty, CardAttributes.Pattern_Filled, CardAttributes.Pattern_Striped };
	
	private List<Card> unusedCards;
	private List<Card> usedCards;
	private Random r;
	
	public Deck(ICardListener table) {
		unusedCards = new ArrayList<Card>();
		usedCards = new ArrayList<Card>();
		createDeck(table);
		r = new Random(3);
	}
	
	private void createDeck(ICardListener table) {
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < shapes.length; j++) {
				for (int k = 0; k < counts.length; k++) {
					for (int n = 0; n < patterns.length; n++) {
						Card card = new Card(new CardAttributes(colors[i], shapes[j], counts[k], patterns[n]), table);
						unusedCards.add(card);
					}
				}
			}
		}
	}
	
	public void recycleDeck() {
		unusedCards.addAll(usedCards);
		usedCards.clear();
	}
	
	public Card getRandomCard() {
		if (unusedCards.size() == 0)
			return null;
		int rIndex = r.nextInt(unusedCards.size());
		Card card = unusedCards.get(rIndex);
		useCard(card);
		return card;
	}
	
	public void giveBackRandomCard(Card card) {
		unuseCard(card);
	}
	
	private void useCard(Card card) {
		unusedCards.remove(card);
		usedCards.add(card);
	}
	
	private void unuseCard(Card card) {
		usedCards.remove(card);
		unusedCards.add(card);
	}
	
	public Card getCardWithAttributes(CardAttributes third) {
		for (Card card : unusedCards)
			if (card.getAttributes().equals(third)) {
				useCard(card);
				return card;
			}
		return null;
	}

	public void start() {
		recycleDeck();
	}
	
	public void end() {
		usedCards.addAll(unusedCards);
		for (Card card : usedCards)
			card.deactivate();
	}

	public void reset() {
		usedCards.addAll(unusedCards);
		for (Card card : usedCards)
			card.deactivate();
		recycleDeck();
	}
}
