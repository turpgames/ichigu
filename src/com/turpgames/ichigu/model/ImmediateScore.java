package com.turpgames.ichigu.model;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.effects.fading.IFadingEffectSubject;
import com.turpgames.framework.v0.impl.AttachedText;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Drawer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.utils.R;

public class ImmediateScore extends GameObject implements IFadingEffectSubject {
	private Timer timer;
	private Text text;
	private FadeOutEffect fadeOutEffect;
	private boolean isActive;

	public ImmediateScore() {
		text = new AttachedText(this);
		text.setHorizontalAlignment(Text.HAlignCenter);
		text.setVerticalAlignment(Text.VAlignTop);
		text.setPadX(Game.scale(30));
		text.setPadY(Game.scale(30));

		this.setWidth(Game.getScreenWidth());
		this.getColor().set(R.colors.ichiguRed);

		timer = new Timer();
		timer.setTickListener(timerListener);
		
		fadeOutEffect = new FadeOutEffect(this);
		fadeOutEffect.setMinAlpha(0);
		fadeOutEffect.setMaxAlpha(1);
		fadeOutEffect.setListener(effectListener);
	}

	public void setPadX(float padX) {
		text.setPadX(Game.scale(padX));
	}

	public void setPadY(float padY) {
		text.setPadY(Game.scale(padY));
	}

	public void setFontScale(float scale) {
		text.setFontScale(scale);
	}

	public void setTextColor(Color color) {
		text.getColor().set(color);
	}

	public void show(String message, float millis) {
		if (isActive) {
			timer.start();
			fadeOutEffect.stop();
			setAlpha(1);
		}

		this.isActive = true;

		text.setText(message);

		this.setHeight(text.getTextAreaHeight() + 2 * text.getPadY());
		this.getLocation().set(0, Game.getScreenHeight() - this.getHeight());
		
		timer.setInterval(millis / 1000f);

		fadeOutEffect.setDuration(millis / 1000f);
		fadeOutEffect.start();
		
		this.listenInput(true);
		Drawer.getCurrent().register(this, 1000);
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

		this.listenInput(false);
	}

	private void effectEnd() {
		if (isActive)
			timer.start();
		else
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
	public boolean ignoreViewport() {
		return true;
	}

	@Override
	public void draw() {
		ShapeDrawer.drawRect(this, true);
		text.draw();
	}

	@Override
	protected boolean onTap() {
		hide();
		return true;
	}
	
	@Override
	public void setAlpha(float alpha) {
		getColor().a = alpha;
		text.getColor().a = alpha;
	}
}
