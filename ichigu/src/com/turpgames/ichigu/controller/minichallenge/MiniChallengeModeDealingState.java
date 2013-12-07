package com.turpgames.ichigu.controller.minichallenge;

public class MiniChallengeModeDealingState extends MiniChallengeModeState {
	public MiniChallengeModeDealingState(MiniChallengeModeController controller) {
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