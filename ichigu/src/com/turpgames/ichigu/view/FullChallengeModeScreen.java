package com.turpgames.ichigu.view;

import com.turpgames.ichigu.controller.fullgame.FullGameController;
import com.turpgames.ichigu.model.game.fullgame.modes.FullChallengeMode;

public class FullChallengeModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new FullGameController(this, new FullChallengeMode()));
	}
}