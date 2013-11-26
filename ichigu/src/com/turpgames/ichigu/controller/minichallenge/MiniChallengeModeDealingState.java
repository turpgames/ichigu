package com.turpgames.ichigu.controller.minichallenge;

import com.turpgames.ichigu.model.game.ICardDealerListener;

public class MiniChallengeModeDealingState extends MiniChallengeModeState implements ICardDealerListener {
	public MiniChallengeModeDealingState(MiniChallengeModeController controller) {
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