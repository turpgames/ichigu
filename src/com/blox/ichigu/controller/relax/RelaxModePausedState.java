package com.blox.ichigu.controller.relax;

public class RelaxModePausedState extends RelaxModeState {
	public RelaxModePausedState(RelaxModeController controller) {
		super(controller);
	}
	
	@Override
	protected void activated() {
		model.pause();
	}
	
	@Override
	protected void deactivated() {
		model.resume();
	}
	
	@Override
	public void onScreenActivated() {
		controller.setWaitingState();
	}
}
