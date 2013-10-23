package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.effects.flash.FlashEffect;
import com.turpgames.framework.v0.effects.flash.IFlashEffectSubject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.ichigu.utils.R;

public class ScoreInfo extends GameInfo implements IFlashEffectSubject {
	int score;
	private FlashEffect flashEffect;
	
	public ScoreInfo() {
		super();
		init();
		flashEffect = new FlashEffect(this, R.colors.ichiguRed, 8);
		flashEffect.setDuration(0.5f);
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void increaseScore(int score) {
		this.score += score;
		updateText();
	}
	
	public void decreaseScore(int score) {
		this.score -= score;
		updateText();
		flashEffect.start();
	}

	public void init() {
		this.score = 0;
		updateText();
	}
	
	private void updateText() {
		setText("Score: " + this.score);
	}

	@Override
	public Color getColor() {
		return text.getColor();
	}
}
