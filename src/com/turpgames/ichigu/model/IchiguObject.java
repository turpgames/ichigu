package com.turpgames.ichigu.model;

import com.turpgames.framework.v0.effects.CompositeEffect;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.MoveEffect;
import com.turpgames.framework.v0.effects.ScaleEffect;
import com.turpgames.framework.v0.effects.blink.BlinkEffect;
import com.turpgames.framework.v0.effects.blink.IBlinkEffectSubject;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.effects.fading.IFadingEffectSubject;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Vector;

public abstract class IchiguObject extends GameObject implements IFadingEffectSubject, IBlinkEffectSubject {
	public static final float FadingDuration = 0.25f;
	public static final float BlinkDuration = 1f;
	public static final int BlinkPerSecond = 10;
	public static final float MaxScale = 0.2f;
	
	private FadeOutEffect fadeEffect;
	private BlinkEffect blinkEffect;
	private MoveEffect moveEffect;
	private ScaleEffect scaleEffect;
	private CompositeEffect moveAndScaleEffect;

	private FadeOutEffect getFadeEffect() {
		if (fadeEffect == null) {
			fadeEffect = new FadeOutEffect(this);
			fadeEffect.setDuration(FadingDuration);
		}
		return fadeEffect;
	}

	private BlinkEffect getBlinkEffect() {
		if (blinkEffect == null) {
			blinkEffect = new BlinkEffect(this);
			blinkEffect.setDuration(BlinkDuration);
			blinkEffect.setBlinkPerSecond(BlinkPerSecond);
		}
		return blinkEffect;
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
			scaleEffect.setMaxScale(MaxScale);
		}
		return scaleEffect;
	}
	
	private CompositeEffect getMoveAndScaleEffect() {
		if (moveAndScaleEffect == null) {
			moveAndScaleEffect = new CompositeEffect(this, getMoveEffect(), getScaleEffect());
		}
		return moveAndScaleEffect;
	}
	
	public void fadeOut(IEffectEndListener listener) {
		getFadeEffect().start(listener);
	}

	public void moveTo(IEffectEndListener listener, Vector destination, float duration) {
		getMoveEffect().setDestination(destination);
		getMoveAndScaleEffect().setDuration(duration);
		getMoveAndScaleEffect().start(listener);
	}
	
	public void blink(IEffectEndListener listener, boolean looping) {
		getBlinkEffect().setLooping(looping);
		getBlinkEffect().start(listener);
	}

	public void stopBlinking() {
		getBlinkEffect().stop();
	}

	public void stopEffects() {
		getFadeEffect().stop();
		getBlinkEffect().stop();
		getMoveAndScaleEffect().stop();
	}
	
	@Override
	public void setAlpha(float alpha) {
		getColor().a = alpha;	
	}
	
	@Override
	public float getAlpha() {
		return getColor().a;
	}
}
