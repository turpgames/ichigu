package com.blox.ichigu.controller.practice;

import com.blox.framework.v0.util.Game;
import com.blox.ichigu.controller.IchiguState;
import com.blox.ichigu.model.PracticeMode;
import com.blox.ichigu.utils.IchiguResources;
import com.blox.ichigu.view.PracticeModeScreen;

public abstract class PracticeModeState extends IchiguState implements IPracticeModeActionListener {
	final PracticeMode model;
	final PracticeModeScreen view;
	final PracticeModeController controller;

	public PracticeModeState(PracticeModeController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}

	@Override
	public void onScreenDeactivated() {
		model.exitMode();
	}
	
	@Override
	public void onUnblock() {
		controller.setWaitingState();
	}

	@Override
	public void onDealTimeUp() {
		IchiguResources.playSoundTimeUp();
		Game.vibrate(100);
		controller.setDealingState();
	}

	@Override
	public void onModeEnd() {
		controller.setModeEndState();
	}

	@Override
	public void onNewGame() {

	}

	@Override
	public void draw() {
		model.drawGame();
	}
}