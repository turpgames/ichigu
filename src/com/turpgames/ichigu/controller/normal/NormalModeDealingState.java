package com.turpgames.ichigu.controller.normal;

import com.turpgames.ichigu.model.ICardDealerListener;

public class NormalModeDealingState extends NormalModeState implements ICardDealerListener {
	public NormalModeDealingState(NormalModeController controller) {
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
