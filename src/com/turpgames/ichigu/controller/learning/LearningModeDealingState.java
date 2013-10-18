package com.turpgames.ichigu.controller.learning;

import com.turpgames.ichigu.model.ICardDealerListener;

public class LearningModeDealingState extends LearningModeState implements ICardDealerListener {
	public LearningModeDealingState(LearningModeController controller) {
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
