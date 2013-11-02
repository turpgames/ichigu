package com.turpgames.ichigu.model.singlegame.practice;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.utils.R;

class PracticeModeHint implements IDrawable, Toast.IToastListener, IEffectEndListener {
	private List<String> hints;
	private int index;
	private Toast toast;
	private Card thirdCard;
	private boolean isDisplayingHint;
	private ImageButton hintButton;
	private int colorIndex;

	PracticeModeHint() {
		hints = new ArrayList<String>();

		hintButton = new ImageButton(R.ui.imageButtonWidth, R.ui.imageButtonHeight, R.game.textures.hint, R.colors.buttonDefault, R.colors.buttonTouched);
		hintButton.getLocation().set((Game.getVirtualWidth() - hintButton.getWidth()) / 2, 50);
		hintButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				showNextHint();
			}
		});

		toast = new Toast();
		toast.setListener(this);
		toast.setFontScale(R.fontSize.small);
		toast.setToastColor(R.colors.ichiguYellow);
	}

	@Override
	public boolean onEffectEnd(Object obj) {
		// Yazý kaymaya devam ediyorsa, kart da yanýp sönmeye devam etsin
		return !isDisplayingHint;
	}

	@Override
	public void onToastHidden(Toast toast) {
		isDisplayingHint = false;
		thirdCard.stopBlinking();
	}

	public void activate() {
		hintButton.listenInput(true);
	}

	public void deactivate() {
		hintButton.listenInput(false);
		toast.dispose();
	}

	@Override
	public void draw() {
		drawButton();
	}

	private void drawButton() {
		hintButton.draw();
	}

	private void showNextHint() {
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
			toast.show(hint, 5000);
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

	public void update(Card card1, Card card2, Card card3) {
		CardAttributes ca1 = card1.getAttributes();
		CardAttributes ca2 = card2.getAttributes();

		this.thirdCard = card3;

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
			hints.add(Game.getLanguageManager().getString(R.strings.sameShapes));
		}
		else {
			hints.add(Game.getLanguageManager().getString(R.strings.differentShapes));
		}
	}

	private void addColorHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getColor() == ca2.getColor()) {
			hints.add(Game.getLanguageManager().getString(R.strings.sameColors));
		}
		else {
			hints.add(Game.getLanguageManager().getString(R.strings.differentColors));
		}
	}

	private void addPatternHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getPattern() == ca2.getPattern()) {
			hints.add(Game.getLanguageManager().getString(R.strings.samePatterns));
		}
		else {
			hints.add(Game.getLanguageManager().getString(R.strings.differentPatterns));
		}
	}

	private void addCountHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getCount() == ca2.getCount()) {
			hints.add(Game.getLanguageManager().getString(R.strings.sameCounts));
		}
		else 
		{
			hints.add(Game.getLanguageManager().getString(R.strings.differentCounts));
		}
	}

//	private void addFinalHint(CardAttributes ca1, CardAttributes ca2) {
//		int color3 = CardAttributes.getCompleting(ca1.getColor(), ca2.getColor());
//		int count3 = CardAttributes.getCompleting(ca1.getCount(), ca2.getCount());
//		int pattern3 = CardAttributes.getCompleting(ca1.getPattern(), ca2.getPattern());
//		int shape3 = CardAttributes.getCompleting(ca1.getShape(), ca2.getShape());
//
//		hints.add(String.format("Third card must have %d %s %s %s%s",
//				CardAttributes.getCountValue(count3),
//				CardAttributes.getPatternName(pattern3),
//				CardAttributes.getColorName(color3),
//				CardAttributes.getShapeName(shape3),
//				CardAttributes.getCountValue(count3) > 1 ? "s" : ""
//				));
//	}
}