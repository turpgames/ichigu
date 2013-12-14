package com.turpgames.ichigu.controller;

import com.turpgames.ichigu.model.game.Card;

public abstract class IchiguState implements IIchiguController {
	protected void activated() {

	}

	protected void deactivated() {

	}

	@Override
	public void onScreenActivated() {
		
	}

	@Override
	public boolean onScreenDeactivated() {
		this.deactivated();
		return true;
	}
	
	@Override
	public void onCardTapped(Card card) {
		
	}
}