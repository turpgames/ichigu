package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.effects.fading.IFadingEffectSubject;
import com.turpgames.framework.v0.impl.Text;

public class TextFadeOuter implements IFadingEffectSubject, IEffectEndListener {
	private final Text text;
	private FadeOutEffect effect;

	private IEffectEndListener listener;
	
	TextFadeOuter(Text text) {
		this(text, 1);
	}
	
	TextFadeOuter(Text text, float duration) {
		this.text = text;
		this.effect = new FadeOutEffect(this);
		this.effect.setDuration(duration);
		this.effect.setListener(this);
	}
	
	public void setListener(IEffectEndListener listener) {
		this.listener = listener;
	}
	
	public void fade() {
		this.effect.start();
	}

	@Override
	public void setAlpha(float alpha) {
		text.getColor().a = alpha;
	}

	@Override
	public boolean onEffectEnd(Object obj) {
		if (listener != null)
			listener.onEffectEnd(obj);
		return false;
	}

}
