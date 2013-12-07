package com.turpgames.ichigu.controller.fullgame;


public class FullGameDealingState extends FullGameState {
	public FullGameDealingState(FullGameController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		model.deal();
	}

	@Override
	public void onDealEnded() {
		controller.setWaitingState();
	}

	@Override
	public boolean onScreenDeactivated() {
		return false;
	}
}
