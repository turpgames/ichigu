package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.Background;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.utils.R;

public abstract class IchiguScreen extends Screen {
	protected IIchiguViewListener screenListener = IIchiguViewListener.NULL;

	@Override
	public void init() {
		super.init();

		registerDrawable(new Background(), Game.LAYER_BACKGROUND);
		registerDrawable(IchiguGame.getToolbar(), Game.LAYER_INFO);

		registerInputListener(this);
	}

	public void onExitConfirmed() {
		unregisterDrawable(screenListener);
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
	}

	protected void notifyScreenActivated() {
		registerDrawable(screenListener, Game.LAYER_SCREEN);
		screenListener.onScreenActivated();
	}

	protected boolean notifyScreenDeactivated() {
		if (screenListener.onScreenDeactivated()) {
			unregisterDrawable(screenListener);
			return true;
		}
		return false;
	}
	
	@Override
	protected void onAfterActivate() {
		IchiguToolbar.getInstance().activateBackButton();
		notifyScreenActivated();
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}

	@Override
	protected boolean onBeforeDeactivate() {
		return notifyScreenDeactivated();
	}

	protected void setScreenListener(IIchiguViewListener listener) {
		this.screenListener = listener;
	}

	void back() {
		onBack();
	}
}