package com.blox.ichigu.model;

import com.blox.framework.v0.effects.BlinkEffect;
import com.blox.framework.v0.effects.CompositeEffect;
import com.blox.framework.v0.effects.FadeOutEffect;
import com.blox.framework.v0.effects.IEffectEndListener;
import com.blox.framework.v0.effects.MoveEffect;
import com.blox.framework.v0.effects.ScaleEffect;
import com.blox.framework.v0.impl.GameObject;
import com.blox.framework.v0.util.Vector;

abstract class IchiguObject extends GameObject {
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
	
	void fadeOut(IEffectEndListener listener) {
		getFadeEffect().start(listener);
	}

	void moveTo(IEffectEndListener listener, Vector destination, float duration) {
		getMoveEffect().setDestination(destination);
		getMoveAndScaleEffect().setDuration(duration);
		getMoveAndScaleEffect().start(listener);
	}
	
	void blink(IEffectEndListener listener, boolean looping) {
		getBlinkEffect().setLooping(looping);
		getBlinkEffect().start(listener);
	}

	void stopBlinking() {
		getBlinkEffect().stop();
	}

	void stopEffects() {
		getFadeEffect().stop();
		getBlinkEffect().stop();
		getMoveAndScaleEffect().stop();
	}
}
