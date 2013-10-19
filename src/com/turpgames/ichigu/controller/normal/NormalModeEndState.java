package com.turpgames.ichigu.controller.normal;

public class NormalModeEndState extends NormalModeState {
	public NormalModeEndState(NormalModeController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		model.endMode();
	}

	@Override
	public void onNewGame() {
		model.startMode();
		controller.setDealingState();
	}

	@Override
	public void draw() {
		model.drawResult();
	}
}