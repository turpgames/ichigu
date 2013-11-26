package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.effects.flash.FlashEffect;
import com.turpgames.framework.v0.effects.flash.IFlashEffectSubject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.ichigu.utils.R;

class TextFlasher implements IFlashEffectSubject {
	private final Text text;
	private FlashEffect effect;

	TextFlasher(Text text) {
		this.text = text;
		this.effect = new FlashEffect(this, R.colors.ichiguRed, 5);
		this.effect.setDuration(1);
	}
	
	public void flash() {
		this.effect.start();
	}

	@Override
	public Color getColor() {
		return text.getColor();
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		text.getColor().set(r, g, b, a);
	}
}
