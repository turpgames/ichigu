package com.blox.ichigu.view;

import com.blox.ichigu.controller.practice.PracticeModeController;

public class PracticeModeScreen extends IchiguScreen {
	@Override
	protected String getTitle() {
		return "Practice";
	}
	
	@Override
	public void init() {
		super.init();
		setScreenListener(new PracticeModeController(this));
	}
}
