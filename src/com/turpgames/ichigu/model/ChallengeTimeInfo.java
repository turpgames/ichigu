package com.turpgames.ichigu.model;

import com.turpgames.framework.v0.effects.flash.FlashEffect;
import com.turpgames.framework.v0.effects.flash.IFlashEffectSubject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.ichigu.utils.R;

public class ChallengeTimeInfo extends GameInfo implements IFlashEffectSubject {
	private FlashEffect flashEffect;
	
	public ChallengeTimeInfo() {
		super();
		flashEffect = new FlashEffect(this, R.colors.ichiguRed, 8);
		flashEffect.setDuration(10f);
	}
	
	public void startCountdown() {
		flashEffect.start();
	}

	@Override
	public Color getColor() {
		return text.getColor();
	}
}
