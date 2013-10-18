package com.turpgames.ichigu.model;

import java.util.ArrayList;
import java.util.List;

class FullGameIchiguInfo {
	private final List<Integer> ichigus;

	FullGameIchiguInfo() {
		ichigus = new ArrayList<Integer>();
	}

	public void update(Card[] cards) {
		ichigus.clear();

		for (int i = 0; i < cards.length; i++) {
			for (int j = i + 1; j < cards.length; j++) {
				for (int k = j + 1; k < cards.length; k++) {
					if (cards[i] != null && cards[j] != null && cards[k] != null &&
							cards[i].isOpened() && cards[j].isOpened() && cards[k].isOpened() &&
							Card.isIchigu(cards[i], cards[j], cards[k])) {
						ichigus.add(i);
						ichigus.add(j);
						ichigus.add(k);
					}
				}
			}
		}
	}

	public int getIchiguCardIndex(int ichiguIndex, int cardIndex) {
		return ichigus.get(ichiguIndex * 3 + cardIndex);
	}

	public int getIchiguCount() {
		return ichigus.size() / 3;
	}
}
