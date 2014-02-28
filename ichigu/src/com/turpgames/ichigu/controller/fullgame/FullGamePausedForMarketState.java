package com.turpgames.ichigu.controller.fullgame;

class FullGamePausedForMarketState extends FullGameState {
	public FullGamePausedForMarketState(FullGameController controller) {
		super(controller);
	}
	
	@Override
	protected void activated() {
		model.pauseForMarketMenu();
	}
	
	@Override
	public void onScreenActivated() {
		model.resumeFromMarketMenu();
		controller.setWaitingState();
	}
	
	@Override
	public boolean onScreenDeactivated() {
		return true;
	}
}
