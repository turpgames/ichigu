package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.effects.CompositeEffect;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.MoveEffect;
import com.turpgames.framework.v0.effects.ScaleEffect;
import com.turpgames.framework.v0.effects.blink.BlinkEffect;
import com.turpgames.framework.v0.effects.blink.IBlinkEffectSubject;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.effects.fading.IFadingEffectSubject;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.ichigu.utils.R;

public abstract class IchiguObject extends GameObject implements IFadingEffectSubject, IBlinkEffectSubject {
	private FadeOutEffect fadeEffect;
	private BlinkEffect blinkEffect;
	private MoveEffect moveEffect;
	private ScaleEffect scaleEffect;
	private CompositeEffect moveAndScaleEffect;

	public void blink(IEffectEndListener listener, boolean looping) {
		getBlinkEffect().setLooping(looping);
		getBlinkEffect().start(listener);
	}

	public void fadeOut(IEffectEndListener listener) {
		getFadeEffect().start(listener);
	}

	@Override
	public float getAlpha() {
		return getColor().a;
	}
	
	@Override
	public void setAlpha(float alpha) {
		getColor().a = alpha;	
	}
	
	public void stopBlinking() {
		getBlinkEffect().stop();
	}

	public void stopEffects() {
		getFadeEffect().stop();
		getBlinkEffect().stop();
		getMoveAndScaleEffect().stop();
	}
	
	private BlinkEffect getBlinkEffect() {
		if (blinkEffect == null) {
			blinkEffect = new BlinkEffect(this);
			blinkEffect.setDuration(R.durations.blinkDuration);
			blinkEffect.setBlinkPerSecond(R.counts.blinkPerSecond);
		}
		return blinkEffect;
	}

	private FadeOutEffect getFadeEffect() {
		if (fadeEffect == null) {
			fadeEffect = new FadeOutEffect(this);
			fadeEffect.setDuration(R.durations.fadingDuration);
		}
		return fadeEffect;
	}

	private CompositeEffect getMoveAndScaleEffect() {
		if (moveAndScaleEffect == null) {
			moveAndScaleEffect = new CompositeEffect(this, getMoveEffect(), getScaleEffect());
		}
		return moveAndScaleEffect;
	}
	
	private MoveEffect getMoveEffect() {
		if (moveEffect == null) {
			moveEffect = new MoveEffect(this);
			moveEffect.setLooping(false);
		}
		return moveEffect;
	}
	
	private ScaleEffect getScaleEffect() {
		if (scaleEffect == null) {
			scaleEffect = new ScaleEffect(this);
			scaleEffect.setMaxScale(R.sizes.maxScale);
		}
		return scaleEffect;
	}
}
