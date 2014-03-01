package com.turpgames.ichigu.controller.singlegame;

public class SingleGameDealingState extends SingleGameState {
	public SingleGameDealingState(SingleGameController controller) {
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