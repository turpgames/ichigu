package com.blox.ichigu.controller.relax;

import com.blox.ichigu.controller.IchiguState;
import com.blox.ichigu.model.RelaxMode;
import com.blox.ichigu.view.RelaxModeScreen;

public abstract class RelaxModeState extends IchiguState implements IRelaxModeActionListener {
	final RelaxMode model;
	final RelaxModeScreen view;
	final RelaxModeController controller;

	public RelaxModeState(RelaxModeController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}

	@Override
	public void onModeEnd() {
		controller.setEndState();
	}

	@Override
	public void onNewGame() {

	}
	
	@Override
	public void onScreenDeactivated() {
		controller.setPausedState();
	}

	@Override
	public void draw() {
		model.drawGame();
	}
}
