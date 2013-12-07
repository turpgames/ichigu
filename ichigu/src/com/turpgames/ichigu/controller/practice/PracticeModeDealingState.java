package com.turpgames.ichigu.controller.practice;

public class PracticeModeDealingState extends PracticeModeState {
	public PracticeModeDealingState(PracticeModeController controller) {
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
