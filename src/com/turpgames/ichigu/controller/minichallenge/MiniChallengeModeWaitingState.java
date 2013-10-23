package com.turpgames.ichigu.controller.minichallenge;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.utils.IchiguResources;

public class MiniChallengeModeWaitingState extends MiniChallengeModeState {
	public MiniChallengeModeWaitingState(MiniChallengeModeController controller) {
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