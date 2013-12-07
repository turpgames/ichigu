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
	public void onCardTapped(Card card) {

	}

	@Override
	public void onIchiguFound() {
		Ichigu.playSoundSuccess();
		Game.vibrate(50);
	}

	@Override
	public void onInvalidIchiguSelected() {
		Ichigu.playSoundError();
		Game.vibrate(0, 50, 50, 100);
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
	public void onDealEnded() {
	
	}
	
	@Override
	public void onDeckFinished() {
		
	}
}