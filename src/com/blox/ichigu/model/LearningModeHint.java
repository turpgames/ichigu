package com.blox.ichigu.model;

import java.util.ArrayList;
import java.util.List;

import com.blox.framework.v0.effects.IEffectEndListener;
import com.blox.framework.v0.util.FontManager;
import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.ITextSliderListener;
import com.blox.framework.v0.util.TextSlider;
import com.blox.ichigu.utils.R;

class LearningModeHint extends IchiguObject implements ITextSliderListener, IEffectEndListener {
	private List<String> hints;
	private int index;
	private TextSlider textSlider;
	private Card thirdCard;
	private boolean isActive;
	private IchiguImageButton hintButton;

	LearningModeHint() {
		hintButton = new IchiguImageButton();
		hintButton.setTexture(R.game.textures.hint);
		hintButton.getLocation().set((Game.getVirtualWidth() - hintButton.getWidth()) / 2, 50);
		hintButton.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				showNextHint();
			}
		});

		hints = new ArrayList<String>();
		textSlider = new TextSlider();
		textSlider.setListener(this);
	}

	@Override
	public boolean onEffectEnd(Object obj) {
		return !isActive;
	}

	@Override
	public void onTextSlideEnd(TextSlider slider) {
		isActive = false;
		thirdCard.stopBlinking();
	}

	@Override
	public void listenInput(boolean listen) {
		hintButton.listenInput(listen);
		if (!listen)
			textSlider.stop();
		super.listenInput(listen);
	}

	@Override
	public void draw() {
		drawButton();
		drawHint();
	}

	private void drawButton() {
		hintButton.draw();
	}

	private void drawHint() {
		if (isActive)
			textSlider.draw();
	}

	private void showNextHint() {
		if (isActive)
			return;
		isActive = true;
		String hint = hints.get(index++);
		textSlider.slide(FontManager.defaultFont, hint, Game.getVirtualHeight() - 100);
		if (index == hints.size()) {
			index = 0;
			thirdCard.blink(this, true);
		}
	}

	public void update(Card card1, Card card2, Card card3) {
		CardAttributes ca1 = card1.getAttributes();
		CardAttributes ca2 = card2.getAttributes();

		this.thirdCard = card3;

		isActive = false;
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