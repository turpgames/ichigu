package com.blox.ichigu.controller.challenge;

import com.blox.ichigu.controller.IchiguState;
import com.blox.ichigu.model.ChallengeMode;
import com.blox.ichigu.view.ChallengeModeScreen;

public abstract class ChallengeModeState extends IchiguState implements IChallengeModeActionListener {
	final ChallengeMode model;
	final ChallengeModeScreen view;
	final ChallengeModeController controller;

	public ChallengeModeState(ChallengeModeController controller) {
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
	public void draw() {
		model.drawGame();
	}

	@Override
	public void onScreenDeactivated() {
		model.exitMode();
	}
}