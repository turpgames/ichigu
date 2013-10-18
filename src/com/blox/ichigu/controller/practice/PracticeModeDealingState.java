package com.blox.ichigu.controller.practice;

import com.blox.ichigu.model.ICardDealerListener;

public class PracticeModeDealingState extends PracticeModeState implements ICardDealerListener {
	public PracticeModeDealingState(PracticeModeController controller) {
		super(controller);
		model.setDealerListener(this);
	}

	@Override
	protected void activated() {
		model.deal();
	}

	@Override
	public void onDealEnd() {
		model.dealEnd();
		controller.setWaitingState();
	}

	@Override
	public boolean onScreenDeactivated() {
		return false;
	}
}