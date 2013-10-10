package com.blox.ichigu.controller;

import com.blox.ichigu.model.Card;

public abstract class IchiguState implements IIchiguController {
	protected void activated() {

	}

	protected void deactivated() {

	}

	@Override
	public void onCardTapped(Card card) {

	}

	@Override
	public void onIchiguFound() {

	}

	@Override
	public void onInvalidIchiguSelected() {

	}

	@Override
	public void onScreenActivated() {

	}

	@Override
	public void onScreenDeactivated() {
		this.deactivated();
	}
}