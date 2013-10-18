package com.turpgames.ichigu.model;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.IchiguResources;
import com.turpgames.ichigu.utils.R;

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
			getColor().set(R.colors.ichiguYellow.r, R.colors.ichiguYellow.g, R.colors.ichiguYellow.b, getColor().a);
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