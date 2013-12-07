package com.turpgames.ichigu.view;

import com.turpgames.ichigu.controller.minichallenge.PracticeModeController;
import com.turpgames.ichigu.model.singlegame.modes.PracticeMode;

public class PracticeModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new PracticeModeController(this, new PracticeMode()));
	}
}