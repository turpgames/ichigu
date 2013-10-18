package com.turpgames.ichigu.controller.practice;

import com.turpgames.framework.v0.util.Game;

public class PracticeModeEndState extends PracticeModeState {
	public PracticeModeEndState(PracticeModeController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		model.endMode();
		Game.vibrate(100);
	}

	@Override
	public void onNewGame() {
		model.startMode();
		controller.setDealingState();
	}

	@Override
	public void draw() {
		model.drawResults();
	}
}