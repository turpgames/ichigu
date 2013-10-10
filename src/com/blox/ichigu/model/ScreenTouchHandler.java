package com.blox.ichigu.model;

import com.blox.framework.v0.util.Game;

class ScreenTouchHandler extends IchiguObject {
	public ScreenTouchHandler() {
		setWidth(Game.getVirtualWidth());
		setHeight(Game.getVirtualHeight());
	}

	private IScreenTouchListener listener;

	void activate(IScreenTouchListener listener) {
		listenInput(true);
		this.listener = listener;
	}

	void deactivate() {
		listenInput(false);
		this.listener = null;
	}

	@Override
	public void draw() {

	}

	@Override
	protected boolean onTap() {
		if (listener != null) {
			listener.onScreenTouched();
			return true;
		}
		return false;
	}
}
