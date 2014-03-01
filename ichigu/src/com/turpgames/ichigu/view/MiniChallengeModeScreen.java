package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.ichigu.controller.singlegame.SingleGameController;
import com.turpgames.ichigu.model.game.mode.singlegame.MiniGame;
import com.turpgames.ichigu.utils.R;

public class MiniChallengeModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new SingleGameController(this, new MiniGame()));
	}

	@Override
	public void onExitConfirmed() {
		unregisterDrawable(screenListener);
		ScreenManager.instance.switchTo(R.game.screens.menu, true);		
	}
}
