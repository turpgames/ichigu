package com.turpgames.ichigu.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.utils.Util;

public class Deck {
	private static int[] colors = new int[] { CardAttributes.colorRed, CardAttributes.colorGreen, CardAttributes.colorBlue };
	private static int[] shapes = new int[] { CardAttributes.shapeCircle, CardAttributes.shapeSquare, CardAttributes.shapeTriangle };
	private static int[] counts = new int[] { CardAttributes.count1, CardAttributes.count2, CardAttributes.count3 };
	private static int[] patterns = new int[] { CardAttributes.patternEmpty, CardAttributes.patternFilled, CardAttributes.patternStriped };
	
	public static int numberOfCards = colors.length * shapes.length * counts.length * patterns.length;
	
	private List<Card> unusedCards;
	protected List<Card> usingCards;
	private List<Card> usedCards;
	private ICardListener table;
	
	public Deck(ICardListener table) {
		this.table = table;
		unusedCards = new ArrayList<Card>();
		usingCards = new ArrayList<Card>();
		usedCards = new ArrayList<Card>();
	}
	
	public Card getCardWithAttributes(CardAttributes att) {
		for (Card card : unusedCards)
			if (card.getAttributes().equals(att)) {
				usingCard(card);
				return card;
			}
		return null;
	}

	public Card getRandomCard() {
		if (unusedCards.size() == 0) {
			return null;
		}
		int rIndex = Util.Random.randInt(unusedCards.size());
		if (Game.isDebug())
			rIndex = 0;
		Card card = unusedCards.get(rIndex);
		usingCard(card);
		return card;
	}

	public void giveBackUnusedCard(Card card) {
		unuseCard(card);
	}
	
	public void giveBackUsedCard(Card card) {
		usedCard(card);
	}
	
	public void start() {
		unusedCards.clear();
		usingCards.clear();
		usedCards.clear();
		createDeck(table);
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
		unusedCards.addAll(usingCards);
		for (Card card : unusedCards)
			card.reset();
		usedCards.clear();
		usingCards.clear();
	}
	
	protected void unuseCard(Card card) {
		usingCards.remove(card);
		usedCards.remove(card);
		unusedCards.add(card);
		card.reset();
	}
	
	protected void usingCard(Card card) {
		unusedCards.remove(card);
		usingCards.add(card);
	}
	
	public void usedCard(Card card) {
		usingCards.remove(card);
		usedCards.add(card);
		card.reset();
	}
	
	public List<Card> getUnusedCards() {
		return Collections.unmodifiableList(unusedCards);
	}
}
