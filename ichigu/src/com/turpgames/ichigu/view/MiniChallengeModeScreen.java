package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.ichigu.controller.minichallenge.SingleGameController;
import com.turpgames.ichigu.model.singlegame.modes.MiniChallengeMode;
import com.turpgames.ichigu.utils.R;

public class MiniChallengeModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new SingleGameController(this, new MiniChallengeMode()));
	}

	public void onExitConfirmed() {
		unregisterDrawable(screenListener);
		ScreenManager.instance.switchTo(R.game.screens.menu, true);		
	}
}
