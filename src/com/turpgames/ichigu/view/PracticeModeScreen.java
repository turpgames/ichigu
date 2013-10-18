package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.ichigu.controller.practice.PracticeModeController;
import com.turpgames.ichigu.utils.R;

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
