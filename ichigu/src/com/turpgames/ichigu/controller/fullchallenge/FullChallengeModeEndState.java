package com.turpgames.ichigu.controller.fullchallenge;

public class FullChallengeModeEndState extends FullChallengeModeState {
	public FullChallengeModeEndState(FullChallengeModeController controller) {
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