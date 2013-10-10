package com.blox.ichigu.view;

import com.blox.ichigu.controller.learning.LearningModeController;

public class LearningModeScreen extends IchiguScreen {
	@Override
	protected String getTitle() {
		return "How To Play";
	}
	
	@Override
	public void init() {
		super.init();
		setScreenListener(new LearningModeController(this));
	}
}