package com.turpgames.ichigu.view;

import com.turpgames.ichigu.controller.fullgame.FullGameController;
import com.turpgames.ichigu.model.game.fullgame.modes.NormalMode;

public class NormalModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new FullGameController(this, new NormalMode()));
	}
}