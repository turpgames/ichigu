package com.turpgames.ichigu.controller.minichallenge;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.singlegame.minichallenge.MiniChallengeMode;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.view.MiniChallengeModeScreen;

public abstract class MiniChallengeModeState extends IchiguState implements IMiniChallengeModeActionListener {
	final MiniChallengeMode model;
	final MiniChallengeModeScreen view;
	final MiniChallengeModeController controller;

	public MiniChallengeModeState(MiniChallengeModeController controller) {
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
		Ichigu.playSoundTimeUp();
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
		model.draw();
	}
}