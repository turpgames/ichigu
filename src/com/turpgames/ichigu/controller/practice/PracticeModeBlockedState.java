package com.turpgames.ichigu.controller.practice;

import com.turpgames.ichigu.model.Card;
import com.turpgames.ichigu.utils.IchiguResources;

public class PracticeModeBlockedState extends PracticeModeState {
	public PracticeModeBlockedState(PracticeModeController controller) {
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
		IchiguResources.playSoundWait();
	}
}
