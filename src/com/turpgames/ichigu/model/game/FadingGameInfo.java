package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.effects.fading.IFadingEffectSubject;
import com.turpgames.framework.v0.util.Drawer;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.utils.R;

public class FadingGameInfo extends GameInfo implements IFadingEffectSubject {
	private Timer timer;
	private FadeOutEffect fadeOutEffect;
	private boolean isActive;
	private float millis = 2500;

	public FadingGameInfo() {
		timer = new Timer();
		timer.setTickListener(timerListener);

		fadeOutEffect = new FadeOutEffect(this);
		fadeOutEffect.setMinAlpha(0);
		fadeOutEffect.setMaxAlpha(1);
		fadeOutEffect.setListener(effectListener);

		this.text.getColor().set(R.colors.ichiguRed);
	}

	public void setDuration(float millis) {
		this.millis = millis; 
	}
	
	public void show() {
		if (isActive) {
			timer.start();
			fadeOutEffect.stop();
			setAlpha(1);
		}

		this.isActive = true;

		timer.setInterval(millis / 1000f);

		fadeOutEffect.setDuration(millis / 1000f);
		fadeOutEffect.start();

		Drawer.getCurrent().register(this, 2000);
	}

	public void dispose() {
		hide();
		fadeOutEffect.stop();
	}

	public void hide() {
		if (!isActive)
			return;

		this.isActive = false;
		timer.stop();
	}

	private void effectEnd() {
		if (isActive)
			timer.start();
		else if (Drawer.getCurrent() != null) // If mode is exited before the effect is finished, current drawer becomes null
			Drawer.getCurrent().unregister(this);
	}

	private Timer.ITimerTickListener timerListener = new Timer.ITimerTickListener() {
		@Override
		public void timerTick(Timer timer) {
			hide();
		}
	};

	private IEffectEndListener effectListener = new IEffectEndListener() {
		@Override
		public boolean onEffectEnd(Object obj) {
			effectEnd();
			return true;
		}
	};

	@Override
	public void setAlpha(float alpha) {
		this.text.getColor().a = alpha;
	}
}
