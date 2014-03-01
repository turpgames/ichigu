package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.utils.R;

class FullGameHint {
	class FullGameIchiguInfo {
		private final List<Card> ichiguCards;

		FullGameIchiguInfo() {
			ichiguCards = new ArrayList<Card>();
		}

		public int getIchiguCount() {
			return ichiguCards.size() / 3;
		}

		public Card getIchiguHintCard(int ichiguIndex, int cardIndex) {
			return ichiguCards.get(ichiguIndex * 3 + cardIndex);
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

	private FullGameIchiguInfo ichiguInfo;
	private int hintIndex;
	private Table table;
	private List<Card> hintCards;

	FullGameHint(Table table) {
		ichiguInfo = new FullGameIchiguInfo();
		hintCards = new ArrayList<Card>();
		this.table = table;
	}

	public int getIchiguCount() {
		return ichiguInfo.getIchiguCount();
	}

	public void update() {
		table.populateCardsForHints(hintCards);
		ichiguInfo.update(hintCards);
		hintIndex = 0;
	}

	private void hintEnd() {
		if (ichiguInfo.getIchiguCount() > 0)
			hintIndex = (hintIndex + 1) % ichiguInfo.getIchiguCount();
	}

	public boolean hasHint() {
		return ichiguInfo.getIchiguCount() > 0;
	}

	public boolean showHint(boolean triple) {
		if (!hasHint()) {
			IchiguToast.showWarning(R.strings.noIchigu);
			return false;
		}

		if (triple) {
			// null: sadece biri hintEnd �a��rsa yeter,
			ichiguInfo.getIchiguHintCard(hintIndex, 0).blink(null, false);
			ichiguInfo.getIchiguHintCard(hintIndex, 1).blink(blinkEndListener, false);
			ichiguInfo.getIchiguHintCard(hintIndex, 2).blink(null, false);
		}
		else {
			ichiguInfo.getIchiguHintCard(hintIndex, 1).blink(blinkEndListener, false);
		}
		
		return true;
	}

	private IEffectEndListener blinkEndListener = new IEffectEndListener() {
		@Override
		public boolean onEffectEnd(Object card) {
			hintEnd();
			return true;
		}
	};
}