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
	private static final float buttonSize = Game.scale(R.ui.imageButtonWidth);
	
	private List<String> hints;
	private int index;
	private Toast toast;
	private Card thirdCard;
	private boolean isDisplayingHint;
	private ImageButton hintButton;
	private int colorIndex;

	PracticeModeHint() {
		hints = new ArrayList<String>();

		hintButton = new ImageButton(buttonSize, buttonSize, R.game.textures.hint, R.colors.buttonDefault, R.colors.buttonTouched);
		hintButton.getLocation().set((Game.getScreenWidth() - hintButton.getWidth()) / 2, Game.viewportToScreenY(50));
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
		// Yazi kaymaya devam ediyorsa, kart da yanip sonmeye devam etsin
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
}