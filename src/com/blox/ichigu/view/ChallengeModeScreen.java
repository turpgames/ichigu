package com.blox.ichigu.view;

import com.blox.ichigu.controller.challenge.ChallengeModeController;

public class ChallengeModeScreen extends IchiguScreen {
	@Override
	protected String getTitle() {
		return "Challenge";
	}
	
	@Override
	public void init() {
		super.init();
		setScreenListener(new ChallengeModeController(this));
	}
}
