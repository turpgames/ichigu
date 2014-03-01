package com.turpgames.ichigu.controller;

import com.turpgames.ichigu.model.game.Card;

public abstract class IchiguController<T extends IchiguState> implements IIchiguController {
	protected T currentState;

	@Override
	public void draw() {
		if (currentState != null)
			currentState.draw();
	}

	@Override
	public void onCardTapped(Card card) {
		currentState.onCardTapped(card);
	}

	@Override
	public void onExitConfirmed() {
		currentState.onExitConfirmed();		
	}
	

	@Override
	public void onModeEnd() {
		currentState.onModeEnd();
	}

	@Override
	public void onNewGame() {
		currentState.onNewGame();
	}
	
	@Override
	public void onScreenActivated() {
		if (currentState != null)
			currentState.onScreenActivated();
	}
	
	@Override
	public boolean onScreenDeactivated() {
		if (currentState != null)
			return currentState.onScreenDeactivated();
		return false;
	}
	
	protected void setState(T newState) {
		if (currentState != null)
			currentState.deactivated();
		currentState = newState;
		currentState.activated();
	}
}