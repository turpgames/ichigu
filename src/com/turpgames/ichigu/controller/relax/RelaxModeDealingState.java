package com.turpgames.ichigu.controller.relax;

import com.turpgames.ichigu.model.ICardDealerListener;

public class RelaxModeDealingState extends RelaxModeState implements ICardDealerListener {
	public RelaxModeDealingState(RelaxModeController controller) {
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
