package com.blox.ichigu.controller.practice;

import com.blox.framework.v0.util.Game;
import com.blox.ichigu.model.Card;
import com.blox.ichigu.utils.IchiguResources;

public class PracticeModeWaitingState extends PracticeModeState {
	public PracticeModeWaitingState(PracticeModeController controller) {
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
	public void onCardTapped(Card card) {
		model.onCardSelected(card);
	}

	@Override
	public void onIchiguFound() {
		IchiguResources.playSoundSuccess();
		Game.vibrate(50);
		controller.setDealingState();
	}

	@Override
	public void onInvalidIchiguSelected() {
		IchiguResources.playSoundError();
		Game.vibrate(100);
		controller.setBlockedState();
	}
}