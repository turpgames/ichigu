package com.turpgames.ichigu.controller.minichallenge;

import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.utils.Ichigu;

public class SingleGameBlockedState extends SingleGameState {
	public SingleGameBlockedState(SingleGameController controller) {
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
		card.deselect();
	}
	
	@Override
	public void onIchiguFound() {
		Ichigu.playSoundWait();
	}
	
	@Override
	public void onInvalidIchiguSelected() {
		Ichigu.playSoundWait();
	}
}
