package com.turpgames.ichigu.controller.normal;


public class NormalModeDealingState extends NormalModeState {
	public NormalModeDealingState(NormalModeController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		model.deal();
	}

	@Override
	public void onDealEnd() {
		controller.setWaitingState();
	}

	@Override
	public boolean onScreenDeactivated() {
		return false;
	}
}
