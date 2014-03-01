package com.turpgames.ichigu.model.game;

import java.util.LinkedList;
import java.util.Queue;

import com.turpgames.framework.v0.util.Color;

public class CyclicDeck extends Deck {

	private static int cycleInterval = 30;
	
	private Queue<Card> used;
	
	public CyclicDeck(ICardListener table) {
		super(table);
		used = new LinkedList<Card>();
	}

	@Override
	public Card getRandomCard() {
		Card rndCard = super.getRandomCard();
		used.add(rndCard);
		if (used.size() >= cycleInterval) {
			Card unusing = used.remove();
			while (usingCards.contains(unusing)) {
				used.add(unusing);
				unusing = used.remove();
			}
			unusing.getColor().set(Color.white());
			unuseCard(unusing);
		}
		return rndCard;
	}
	
	@Override
	public void start() {
		super.start();
		used.clear();
	}
}
