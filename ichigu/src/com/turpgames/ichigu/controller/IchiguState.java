package com.turpgames.ichigu.controller;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.utils.Ichigu;

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