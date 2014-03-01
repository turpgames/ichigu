package com.turpgames.ichigu.view;

import com.turpgames.ichigu.controller.singlegame.PracticeModeController;
import com.turpgames.ichigu.model.game.mode.singlegame.Practice;

public class PracticeModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new PracticeModeController(this, new Practice()));
	}
}