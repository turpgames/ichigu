package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

class SingleGameHint implements Toast.IToastListener, IEffectEndListener {
	private List<String> hints;
	private int index;
	private Toast toast;
	private Card thirdCard;
	private boolean isDisplayingHint;
	private int colorIndex;

	public SingleGameHint() {
		hints = new ArrayList<String>();

		toast = new Toast();
		toast.setListener(this);
		toast.setFontScale(R.fontSize.small);
		toast.setToastColor(R.colors.ichiguYellow);
	}

	@Override
	public boolean onEffectEnd(Object obj) {
		// Yazi kaymaya devam ediyorsa, kart da yanip sonmeye devam etsin
		return !isDisplayingHint;
	}

	@Override
	public void onToastHidden(Toast toast) {
		isDisplayingHint = false;
		thirdCard.stopBlinking();
	}

	public void showNextHint() {
		if (isDisplayingHint) {
			toast.hide();
			return;
		}
		
		if (index == hints.size()) {
			index = 0;
			thirdCard.blink(this, true);
		}
		else {
			isDisplayingHint = true;
			String hint = hints.get(index++);
		
			setToastColor();
			toast.show(hint, 5f);
		}
	}

	private void setToastColor() {
		colorIndex++;

		if (colorIndex % 3 == 0)
			toast.setToastColor(R.colors.ichiguRed);
		else if (colorIndex % 3 == 1)
			toast.setToastColor(R.colors.ichiguGreen);
		else if (colorIndex % 3 == 2)
			toast.setToastColor(R.colors.ichiguBlue);
	}

	public void update(List<Card> hintCards) {		
		CardAttributes ca1 = hintCards.get(0).getAttributes();
		CardAttributes ca2 = hintCards.get(1).getAttributes();

		this.thirdCard = hintCards.get(2);

		isDisplayingHint = false;
		index = 0;
		hints.clear();

		addShapeHint(ca1, ca2);
		addColorHint(ca1, ca2);
		addPatternHint(ca1, ca2);
		addCountHint(ca1, ca2);
//		addFinalHint(ca1, ca2);
	}

	private void addShapeHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getShape() == ca2.getShape()) {
			hints.add(Ichigu.getString(R.strings.sameShapes));
		}
		else {
			hints.add(Ichigu.getString(R.strings.differentShapes));
		}
	}

	private void addColorHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getColor() == ca2.getColor()) {
			hints.add(Ichigu.getString(R.strings.sameColors));
		}
		else {
			hints.add(Ichigu.getString(R.strings.differentColors));
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

	private void addCountHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getCount() == ca2.getCount()) {
			hints.add(Ichigu.getString(R.strings.sameCounts));
		}
		else 
		{
			hints.add(Ichigu.getString(R.strings.differentCounts));
		}
	}
}