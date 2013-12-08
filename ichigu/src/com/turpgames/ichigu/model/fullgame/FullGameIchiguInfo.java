package com.turpgames.ichigu.model.fullgame;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;

class FullGameIchiguInfo {
	private final List<Integer> ichigus;

	FullGameIchiguInfo() {
		ichigus = new ArrayList<Integer>();
	}

	public int getIchiguCardIndex(int ichiguIndex, int cardIndex) {
		return ichigus.get(ichiguIndex * 3 + cardIndex);
	}

	public int getIchiguCount() {
		return ichigus.size() / 3;
	}
	
	public void update(List<Card> cards) {
		ichigus.clear();

		for (int i = 0; i < cards.size(); i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				for (int k = j + 1; k < cards.size(); k++) {
					if (cards.get(i).isOpened() && cards.get(j).isOpened() && cards.get(k).isOpened() &&
							Card.isIchigu(cards.get(i), cards.get(j), cards.get(k))) {
						ichigus.add(i);
						ichigus.add(j);
						ichigus.add(k);
					}
				}
			}
		}
	}
}
