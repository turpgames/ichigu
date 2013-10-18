package com.blox.ichigu.view;

import com.blox.framework.v0.impl.Screen;
import com.blox.framework.v0.impl.ScreenManager;
import com.blox.ichigu.model.Background;
import com.blox.ichigu.model.Toolbar;
import com.blox.ichigu.utils.R;

public abstract class IchiguScreen extends Screen {
	protected IIchiguViewListener screenListener = IIchiguViewListener.NULL;

	protected void notifyScreenActivated() {
		registerDrawable(screenListener, 2);
		screenListener.onScreenActivated();
	}

	protected boolean notifyScreenDeactivated() {
		if (screenListener.onScreenDeactivated()) {
			unregisterDrawable(screenListener);
			return true;
		}
		return false;
	}

	protected void setScreenListener(IIchiguViewListener listener) {
		this.screenListener = listener;
	}

	@Override
	protected void onAfterActivate() {
		notifyScreenActivated();
		Toolbar.getInstance().activateBackButton();
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		return notifyScreenDeactivated();
	}

	@Override
	public void init() {
		super.init();

		registerDrawable(new Background(), 1);
		registerDrawable(IchiguGame.getToolbar(), 2);

		registerInputListener(this);
	}

	void back() {
		onBack();
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}
}