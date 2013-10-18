package com.blox.ichigu.view;

import com.blox.ichigu.controller.relax.RelaxModeController;

public class RelaxModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new RelaxModeController(this));
	}
}