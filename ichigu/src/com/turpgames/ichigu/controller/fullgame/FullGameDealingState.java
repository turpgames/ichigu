package com.turpgames.ichigu.controller.fullgame;

public class FullGameDealingState extends FullGameState {
	public FullGameDealingState(FullGameController controller) {
		super(controller);
	}

	@Override
	public void onDealEnded() {
		model.dealEnded();
		controller.setWaitingState();
	}

	@Override
	public boolean onScreenDeactivated() {
		return false;
	}

	@Override
	protected void activated() {
		model.deal();
	}
}
