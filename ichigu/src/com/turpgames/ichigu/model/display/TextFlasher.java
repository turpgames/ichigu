package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.flash.FlashEffect;
import com.turpgames.framework.v0.effects.flash.IFlashEffectSubject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.ichigu.utils.R;

class TextFlasher implements IFlashEffectSubject, IEffectEndListener {
	private final Text text;
	private FlashEffect effect;

	private IEffectEndListener listener;
	TextFlasher(Text text) {
		this(text, R.colors.ichiguRed, 5, 1);
	}
	
	TextFlasher(Text text, Color color, int flashPerSecond, float duration) {
		this.text = text;
		this.effect = new FlashEffect(this, color, flashPerSecond);
		this.effect.setDuration(duration);
		this.effect.setListener(this);
	}
	
	public void setListener(IEffectEndListener listener) {
		this.listener = listener;
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

	@Override
	public boolean onEffectEnd(Object obj) {
		if (listener != null)
			listener.onEffectEnd(obj);
		return false;
	}
}
