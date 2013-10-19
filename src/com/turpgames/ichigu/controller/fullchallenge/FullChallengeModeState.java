package com.turpgames.ichigu.controller.fullchallenge;

import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.fullgame.fullchallenge.FullChallengeMode;
import com.turpgames.ichigu.view.FullChallengeModeScreen;

public abstract class FullChallengeModeState extends IchiguState implements IFullChallengeModeActionListener {
	final FullChallengeMode model;
	final FullChallengeModeScreen view;
	final FullChallengeModeController controller;

	public FullChallengeModeState(FullChallengeModeController controller) {
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