package com.turpgames.ichigu.controller.fullgame;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.utils.Ichigu;

public class FullGameWaitingState extends FullGameState {
	public FullGameWaitingState(FullGameController controller) {
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
		super.onIchiguFound();
		model.ichiguFound();
		controller.setDealingState();
	}
}