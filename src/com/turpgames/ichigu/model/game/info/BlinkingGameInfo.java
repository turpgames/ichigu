package com.turpgames.ichigu.model.game.info;

import com.turpgames.framework.v0.effects.flash.FlashEffect;
import com.turpgames.framework.v0.effects.flash.IFlashEffectSubject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.ichigu.utils.R;

public class BlinkingGameInfo extends GameInfo implements IFlashEffectSubject {
	private FlashEffect flashEffect;
	
	public BlinkingGameInfo() {
		super();
		flashEffect = new FlashEffect(this, R.colors.ichiguRed, 5);
		flashEffect.setDuration(30f);
	}
	
	public void start() {
		flashEffect.start();
	}
	
	public void stop() {
		flashEffect.stop();
	}

	public void setDuration(float f) {
		flashEffect.setDuration(f);
	}
	
	@Override
	public Color getColor() {
		return text.getColor();
	}
}
