package com.turpgames.ichigu.controller.normal;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.utils.Ichigu;

public class NormalModeWaitingState extends NormalModeState {
	public NormalModeWaitingState(NormalModeController controller) {
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
		Game.vibrate(50);
		Ichigu.playSoundFlip();
		model.cardTapped(card);
	}

	@Override
	public void onIchiguFound() {
		Ichigu.playSoundSuccess();
		Game.vibrate(100);
		controller.setDealingState();
	}

	@Override
	public void onInvalidIchiguSelected() {
		Ichigu.playSoundError();
		Game.vibrate(0, 50, 50, 100);
		model.deselectCards();
	}
}