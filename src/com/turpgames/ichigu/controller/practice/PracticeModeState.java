package com.turpgames.ichigu.controller.practice;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.PracticeMode;
import com.turpgames.ichigu.utils.IchiguResources;
import com.turpgames.ichigu.view.PracticeModeScreen;

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
	public boolean onScreenDeactivated() {
		return model.exitMode();
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
	public void onExitConfirmed() {
		view.onExitConfirmed();
	}	
	
	@Override
	public void draw() {
		model.drawGame();
	}
}