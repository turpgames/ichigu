package com.turpgames.ichigu.view;

import com.turpgames.ichigu.controller.practice.PracticeModeController;

public class PracticeModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new PracticeModeController(this));
	}
}