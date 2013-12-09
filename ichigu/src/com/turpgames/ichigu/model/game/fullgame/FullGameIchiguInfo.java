package com.turpgames.ichigu.model.game.fullgame;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;

class FullGameIchiguInfo {
	private final List<Card> ichiguCards;

	FullGameIchiguInfo() {
		ichiguCards = new ArrayList<Card>();
	}

	public Card getIchiguHintCard(int ichiguIndex, int cardIndex) {
		return ichiguCards.get(ichiguIndex * 3 + cardIndex);
	}

	public int getIchiguCount() {
		return ichiguCards.size() / 3;
	}
	
	public void update(List<Card> cards) {
		ichiguCards.clear();

		for (int i = 0; i < cards.size(); i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				for (int k = j + 1; k < cards.size(); k++) {
					if (Card.isIchigu(cards.get(i), cards.get(j), cards.get(k))) {
						ichiguCards.add(cards.get(i));
						ichiguCards.add(cards.get(j));
						ichiguCards.add(cards.get(k));
					}
				}
			}
		}
	}
}
