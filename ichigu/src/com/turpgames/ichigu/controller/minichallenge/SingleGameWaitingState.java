package com.turpgames.ichigu.controller.minichallenge;

public class SingleGameWaitingState extends SingleGameState {
	public SingleGameWaitingState(SingleGameController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		model.activateCards();
	}

	@Override
	protected void deactivated() {
		model.deactivateCards();
	}

	@Override
	public void onIchiguFound() {
		super.onIchiguFound();
		model.ichiguFound();
		controller.setDealingState();
	}

	@Override
	public void onInvalidIchiguSelected() {
		super.onInvalidIchiguSelected();
		model.invalidIchiguSelected();
		controller.setBlockedState();
	}
}