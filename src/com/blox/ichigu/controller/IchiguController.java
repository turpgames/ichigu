package com.blox.ichigu.controller;

import com.blox.ichigu.model.Card;

public abstract class IchiguController<T extends IchiguState> implements IIchiguController {
	protected T currentState;

	@Override
	public void onCardTapped(Card card) {
		currentState.onCardTapped(card);
	}

	@Override
	public void onScreenActivated() {
		if (currentState != null)
			currentState.onScreenActivated();
	}

	@Override
	public boolean onScreenDeactivated() {
		return currentState.onScreenDeactivated();
	}

	@Override
	public void onIchiguFound() {
		currentState.onIchiguFound();
	}

	@Override
	public void onInvalidIchiguSelected() {
		currentState.onInvalidIchiguSelected();
	}

	@Override
	public void draw() {
		if (currentState != null)
			currentState.draw();
	}

	protected void setState(T newState) {
		if (currentState != null)
			currentState.deactivated();
		currentState = newState;
		currentState.activated();
	}
}