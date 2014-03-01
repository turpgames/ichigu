package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

class SingleGameHint {
	private List<String> hints;
	private int index;
	private Card thirdCard;
	private int colorIndex;
	private Table table;
	private List<Card> hintCards;
	
	public SingleGameHint(Table table) {
		this.hints = new ArrayList<String>();
		this.table= table;
		this.hintCards = new ArrayList<Card>();
	}

	public void showNextHint() {
		if (index == hints.size()) {
			index = 0;
			thirdCard.blink(null, true);
		}
		else {
			colorIndex++;

			String hint = hints.get(index++);

			IchiguToast.show(hint, getToastColor());
		}
	}

	public void update() {
		table.populateCardsForHints(hintCards);
		CardAttributes ca1 = hintCards.get(0).getAttributes();
		CardAttributes ca2 = hintCards.get(1).getAttributes();

		this.thirdCard = hintCards.get(2);

		index = 0;
		hints.clear();

		addShapeHint(ca1, ca2);
		addColorHint(ca1, ca2);
		addPatternHint(ca1, ca2);
		addCountHint(ca1, ca2);
	}

	private void addColorHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getColor() == ca2.getColor()) {
			hints.add(Ichigu.getString(R.strings.sameColors));
		}
		else {
			hints.add(Ichigu.getString(R.strings.differentColors));
		}
	}

	private void addCountHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getCount() == ca2.getCount()) {
			hints.add(Ichigu.getString(R.strings.sameCounts));
		}
		else
		{
			hints.add(Ichigu.getString(R.strings.differentCounts));
		}
	}

	private void addPatternHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getPattern() == ca2.getPattern()) {
			hints.add(Ichigu.getString(R.strings.samePatterns));
		}
		else {
			hints.add(Ichigu.getString(R.strings.differentPatterns));
		}
	}

	private void addShapeHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getShape() == ca2.getShape()) {
			hints.add(Ichigu.getString(R.strings.sameShapes));
		}
		else {
			hints.add(Ichigu.getString(R.strings.differentShapes));
		}
	}

	private Color getToastColor() {
		if (colorIndex % 4 == 0)
			return R.colors.ichiguRed;
		else if (colorIndex % 4 == 1)
			return R.colors.ichiguGreen;
		else if (colorIndex % 4 == 2)
			return R.colors.ichiguBlue;
		else
			return R.colors.ichiguYellow;
	}
}