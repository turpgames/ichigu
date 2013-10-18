package com.turpgames.ichigu.controller.challenge;

import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.ChallengeMode;
import com.turpgames.ichigu.view.ChallengeModeScreen;

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
	public void onExitConfirmed() {
		view.onExitConfirmed();
	}

	@Override
	public void draw() {
		model.drawGame();
	}

	@Override
	public boolean onScreenDeactivated() {
		return model.exitMode();
	}
}