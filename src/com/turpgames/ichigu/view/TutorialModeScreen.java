package com.turpgames.ichigu.view;

import com.turpgames.ichigu.controller.tutorial.TutorialModeController;

public class TutorialModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new TutorialModeController(this));
	}
}