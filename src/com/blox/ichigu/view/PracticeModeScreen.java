package com.blox.ichigu.view;

import com.blox.framework.v0.impl.ScreenManager;
import com.blox.ichigu.controller.practice.PracticeModeController;
import com.blox.ichigu.utils.R;

public class PracticeModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new PracticeModeController(this));
	}

	public void onExitConfirmed() {
		unregisterDrawable(screenListener);
		ScreenManager.instance.switchTo(R.game.screens.menu, true);		
	}
}
