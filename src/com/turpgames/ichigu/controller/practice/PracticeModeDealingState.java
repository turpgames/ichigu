package com.turpgames.ichigu.controller.practice;

import com.turpgames.ichigu.model.ICardDealerListener;

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
		controller.setWaitingState();
	}

	@Override
	public boolean onScreenDeactivated() {
		return false;
	}
}
