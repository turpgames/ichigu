package com.turpgames.ichigu.view;

import com.turpgames.ichigu.controller.learning.LearningModeController;

public class LearningModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new LearningModeController(this));
	}
}