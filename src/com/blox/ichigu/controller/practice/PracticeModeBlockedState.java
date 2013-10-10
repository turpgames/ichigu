package com.blox.ichigu.controller.practice;

import com.blox.ichigu.model.Card;
import com.blox.ichigu.utils.IchiguResources;

public class PracticeModeBlockedState extends PracticeModeState {
	public PracticeModeBlockedState(PracticeModeController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		model.activateCardsOnTable();
	}

	@Override
	protected void deactivated() {
		model.deactivateCards();
	}
	
	@Override
	public void onCardTapped(Card card) {
		card.deselect();
		IchiguResources.playSoundWait();
	}
}
