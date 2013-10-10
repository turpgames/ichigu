package com.blox.ichigu.model;

import com.blox.framework.v0.util.Game;
import com.blox.ichigu.utils.R;
import com.blox.ichigu.utils.IchiguResources;

abstract class IchiguButton extends IchiguObject {
	protected boolean isActive;
	protected IIchiguButtonListener listener;

	protected IchiguButton() {
		activate();
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
			getColor().set(R.colors.ichiguBlue.r, R.colors.ichiguBlue.g, R.colors.ichiguBlue.b, getColor().a);
			getScale().set(1.2f);
			getRotation().origin.set(getLocation().x + getWidth() / 2, getLocation().y + getHeight() / 2);
		}
		else {
			getColor().set(1, 1, 1, getColor().a);
			getScale().set(1f);
		}

		onDraw();
	}

	protected abstract void onDraw();
}