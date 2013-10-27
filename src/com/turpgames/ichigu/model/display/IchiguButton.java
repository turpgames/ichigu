package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.IchiguObject;
import com.turpgames.ichigu.utils.IchiguResources;
import com.turpgames.ichigu.utils.R;

abstract class IchiguButton extends IchiguObject {
	protected boolean isActive;
	protected IIchiguButtonListener listener;
	protected Color defaultColor;
	protected Color touchedColor;
	
	protected IchiguButton() {
		activate();
		defaultColor = new Color();
		touchedColor = new Color();
		setDefaultColor(Color.white());
		setTouchedColor(R.colors.ichiguYellow);
	}

	public void activate() {
		isActive = true;
		listenInput(true);
	}

	public void deactivate() {
		isActive = false;
		listenInput(false);
	}

	public boolean isActive() {
		return isActive;
	}

	public IIchiguButtonListener getListener() {
		return listener;
	}

	public void setListener(IIchiguButtonListener listener) {
		this.listener = listener;
	}

	public void setDefaultColor(Color color) {
		defaultColor.set(color.r, color.g, color.b, color.a);
	}
	
	public void setTouchedColor(Color color) {
		touchedColor.set(color.r, color.g, color.b, color.a);
	}
	
	@Override
	protected boolean onTap() {
		IchiguResources.playSoundFlip();
		Game.vibrate(50);

		if (listener != null)
			listener.onButtonTapped();

		return true;
	}

	@Override
	public void draw() {
		if (!isActive)
			return;

		if (isTouched()) {
			getColor().set(touchedColor.r, touchedColor.g, touchedColor.b, getColor().a);
			getScale().set(1.2f);
			getRotation().origin.set(getLocation().x + getWidth() / 2, getLocation().y + getHeight() / 2);
		}
		else {
			getColor().set(defaultColor.r, defaultColor.g, defaultColor.b, getColor().a);
			getScale().set(1f);
		}

		onDraw();
	}

	protected abstract void onDraw();
}