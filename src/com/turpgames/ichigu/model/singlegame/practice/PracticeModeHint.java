package com.turpgames.ichigu.model.singlegame.practice;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.Card;
import com.turpgames.ichigu.model.CardAttributes;
import com.turpgames.ichigu.model.IIchiguButtonListener;
import com.turpgames.ichigu.model.IchiguImageButton;
import com.turpgames.ichigu.utils.R;

class PracticeModeHint implements IDrawable, Toast.IToastListener, IEffectEndListener {
	private List<String> hints;
	private int index;
	private Toast toast;
	private Card thirdCard;
	private boolean isDisplayingHint;
	private IchiguImageButton hintButton;
	private int colorIndex;

	PracticeModeHint() {
		hints = new ArrayList<String>();

		hintButton = new IchiguImageButton();
		hintButton.setTexture(R.game.textures.hint);
		hintButton.getLocation().set((Game.getVirtualWidth() - hintButton.getWidth()) / 2, 50);
		hintButton.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				showNextHint();
			}
		});

		toast = new Toast();
		toast.setListener(this);
		toast.setFontScale(R.fontSize.small);
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
		isDisplayingHint = true;
		String hint = hints.get(index++);

		setToastColor();
		toast.show(hint, 5000);

		if (index == hints.size()) {
			index = 0;
			thirdCard.blink(this, true);
		}
	}

	private void setToastColor() {
		colorIndex++;

		if (colorIndex % 3 == 0)
			toast.getColor().set(R.colors.ichiguRed);
		else if (colorIndex % 3 == 1)
			toast.getColor().set(R.colors.ichiguGreen);
		else if (colorIndex % 3 == 2)
			toast.getColor().set(R.colors.ichiguBlue);
		
		toast.getColor().a = 0.85f;
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
		addFinalHint(ca1, ca2);
	}

	private void addShapeHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getShape() == ca2.getShape()) {
			hints.add(String.format(
					"Both first and second cards are %s. " +
							"So third card must have same shape with them, %s.",
					ca1.getShapeName(),
					ca1.getShapeName()));
			return;
		}

		int shape3 = CardAttributes.getCompleting(ca1.getShape(), ca2.getShape());
		hints.add(String.format(
				"First card is %s and second card is %s. " +
						"Since they have different shapes, third card should have a different shape than the others. " +
						"So third card must be %s.",
				ca1.getShapeName(),
				ca2.getShapeName(),
				CardAttributes.getShapeName(shape3)
				));
	}

	private void addColorHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getColor() == ca2.getColor()) {
			hints.add(String.format(
					"Both first and second cards are %s. " +
							"So third card must have same color with them, %s.",
					ca1.getColorName(),
					ca1.getColorName()));
			return;
		}

		int color3 = CardAttributes.getCompleting(ca1.getColor(), ca2.getColor());
		hints.add(String.format(
				"First card is %s and second card is %s. " +
						"Since they have different colors, third card should have a different color than the others. " +
						"So third card must be %s.",
				ca1.getColorName(),
				ca2.getColorName(),
				CardAttributes.getColorName(color3)
				));
	}

	private void addPatternHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getPattern() == ca2.getPattern()) {
			hints.add(String.format(
					"Both first and second cards are %s. " +
							"So third card must have same backfilling with them, %s.",
					ca1.getPatternName(),
					ca1.getPatternName()));
			return;
		}

		int pattern3 = CardAttributes.getCompleting(ca1.getPattern(), ca2.getPattern());
		hints.add(String.format(
				"First card is %s and second card is %s. " +
						"Since they have different backfillings, third card should have a different backfilling than the others. " +
						"So third card must be %s.",
				ca1.getPatternName(),
				ca2.getPatternName(),
				CardAttributes.getPatternName(pattern3)
				));
	}

	private void addCountHint(CardAttributes ca1, CardAttributes ca2) {
		if (ca1.getCount() == ca2.getCount()) {
			hints.add(String.format(
					"Both first and second cards have %d symbol%s. " +
							"So third card must have same symbol count with them, %d.",
					ca1.getCountValue(),
					ca1.getCountValue() > 1 ? "s" : "",
					ca1.getCountValue()
					));
			return;
		}

		int count3 = CardAttributes.getCompleting(ca1.getCount(), ca2.getCount());
		hints.add(String.format(
				"First card has %d symbol%s and second card has %d symbol%s. " +
						"Since they have different symbol counts, third card should have a different symbol count than the others. " +
						"So third card must have %d symbol%s.",
				ca1.getCountValue(),
				ca1.getCountValue() > 1 ? "s" : "",
				ca2.getCountValue(),
				ca2.getCountValue() > 1 ? "s" : "",
				CardAttributes.getCountValue(count3),
				CardAttributes.getCountValue(count3) > 1 ? "s" : ""
				));
	}

	private void addFinalHint(CardAttributes ca1, CardAttributes ca2) {
		int color3 = CardAttributes.getCompleting(ca1.getColor(), ca2.getColor());
		int count3 = CardAttributes.getCompleting(ca1.getCount(), ca2.getCount());
		int pattern3 = CardAttributes.getCompleting(ca1.getPattern(), ca2.getPattern());
		int shape3 = CardAttributes.getCompleting(ca1.getShape(), ca2.getShape());

		hints.add(String.format("Third card must have %d %s %s %s%s",
				CardAttributes.getCountValue(count3),
				CardAttributes.getPatternName(pattern3),
				CardAttributes.getColorName(color3),
				CardAttributes.getShapeName(shape3),
				CardAttributes.getCountValue(count3) > 1 ? "s" : ""
				));
	}
}