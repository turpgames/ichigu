package com.blox.ichigu.controller.challenge;

import com.blox.ichigu.model.ICardDealerListener;

public class ChallengeModeDealingState extends ChallengeModeState implements ICardDealerListener {
	public ChallengeModeDealingState(ChallengeModeController controller) {
		super(controller);
		model.setDealerListener(this);
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
