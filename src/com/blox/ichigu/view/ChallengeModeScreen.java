package com.blox.ichigu.view;

import com.blox.framework.v0.impl.ScreenManager;
import com.blox.ichigu.controller.challenge.ChallengeModeController;
import com.blox.ichigu.utils.R;

public class ChallengeModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new ChallengeModeController(this));
	}
		
	public void onExitConfirmed() {
		unregisterDrawable(screenListener);
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
	}
}