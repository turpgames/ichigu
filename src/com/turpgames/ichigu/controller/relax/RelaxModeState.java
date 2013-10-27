package com.turpgames.ichigu.controller.relax;

import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.fullgame.relax.RelaxMode;
import com.turpgames.ichigu.view.RelaxModeScreen;

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
		model.startMode();
		controller.setDealingState();
	}
	
	@Override
	public void onScreenActivated() {
		onNewGame();
	}
	
	@Override
	public boolean onScreenDeactivated() {
		controller.setPausedState();
		return true;
	}

	@Override
	public void draw() {
		model.drawGame();
	}
}
