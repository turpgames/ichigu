package com.turpgames.ichigu.controller.minichallenge;

public class SingleGameDealingState extends SingleGameState {
	public SingleGameDealingState(SingleGameController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		model.deal();
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
}