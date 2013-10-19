package com.turpgames.ichigu.controller.minichallenge;

import com.turpgames.framework.v0.util.Game;

public class MiniChallengeModeEndState extends MiniChallengeModeState {
	public MiniChallengeModeEndState(MiniChallengeModeController controller) {
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