package com.turpgames.ichigu.controller;

import com.turpgames.ichigu.model.game.Card;

public abstract class IchiguState implements IIchiguController {
	@Override
	public void onCardTapped(Card card) {
		
	}

	@Override
	public void onScreenActivated() {
		
	}

	@Override
	public boolean onScreenDeactivated() {
		this.deactivated();
		return true;
	}

	protected void activated() {

	}
	
	protected void deactivated() {

	}
}