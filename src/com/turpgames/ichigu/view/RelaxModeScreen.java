package com.turpgames.ichigu.view;

import com.turpgames.ichigu.controller.relax.RelaxModeController;

public class RelaxModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new RelaxModeController(this));
	}
}