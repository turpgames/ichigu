package com.turpgames.ichigu.model.game;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;

public class Deck {
	private static int[] colors = new int[] { CardAttributes.colorRed, CardAttributes.colorGreen, CardAttributes.colorBlue };
	private static int[] shapes = new int[] { CardAttributes.shapeCircle, CardAttributes.shapeSquare, CardAttributes.shapeTriangle };
	private static int[] counts = new int[] { CardAttributes.count1, CardAttributes.count2, CardAttributes.count3 };
	private static int[] patterns = new int[] { CardAttributes.patternEmpty, CardAttributes.patternFilled, CardAttributes.patternStriped };
	
	private List<Card> unusedCards;
	private List<Card> usedCards;

	private boolean isInfinite;
		
	public Deck(ICardListener table) {
		unusedCards = new ArrayList<Card>();
		usedCards = new ArrayList<Card>();
		createDeck(table);
	}
	
	public void setInfinite(boolean isInfinite) {
		this.isInfinite = isInfinite;
	}
	
	public boolean isInfinite() {
		return isInfinite;
	}
	
	public void end() {
		finishDeck();
	}
	
	public Card getCardWithAttributes(CardAttributes att) {
		for (Card card : unusedCards)
			if (card.getAttributes().equals(att)) {
				useCard(card);
				return card;
			}
		return null;
	}

	public Card getRandomCard() {
		if (unusedCards.size() == 0) {
			if (!isInfinite)
				return null;
			recycleDeck();
		}
		int rIndex = Utils.randInt(unusedCards.size());
		if (Game.isDebug())
			rIndex = 0;
		Card card = unusedCards.get(rIndex);
		useCard(card);
		return card;
	}

	public void giveBackRandomCard(Card card) {
		unuseCard(card);
	}
	
	public void recycleDeck() {
		unusedCards.addAll(usedCards);
		for (Card card : unusedCards)
			card.reset();
		usedCards.clear();
	}
	
	public void reset() {
		end();
		recycleDeck();
	}
	
	public void start() {
		recycleDeck();
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

	private void finishDeck() {
		usedCards.addAll(unusedCards);
		for (Card card : usedCards)
			card.reset();
		unusedCards.clear();
	}
	
	private void unuseCard(Card card) {
		usedCards.remove(card);
		unusedCards.add(card);
	}

	private void useCard(Card card) {
		unusedCards.remove(card);
		usedCards.add(card);
	}
}
