package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.game.Background;
import com.turpgames.ichigu.utils.R;

public abstract class IchiguScreen extends Screen {
	protected IIchiguViewListener screenListener = IIchiguViewListener.NULL;

	protected void notifyScreenActivated() {
		registerDrawable(screenListener, Utils.LAYER_SCREEN);
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
		IchiguToolbar.getInstance().activateBackButton();
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		return notifyScreenDeactivated();
	}

	@Override
	public void init() {
		super.init();

		registerDrawable(new Background(), Utils.LAYER_BACKGROUND);
		registerDrawable(IchiguGame.getToolbar(), Utils.LAYER_INFO);

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