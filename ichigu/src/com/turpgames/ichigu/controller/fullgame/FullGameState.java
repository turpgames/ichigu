package com.turpgames.ichigu.controller.fullgame;

import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.model.game.IIchiguModeListener;
import com.turpgames.ichigu.model.game.IchiguBank;
import com.turpgames.ichigu.view.IchiguScreen;

public abstract class FullGameState extends IchiguState implements IIchiguModeListener {
	final FullGameMode model;
	final IchiguScreen view;
	final FullGameController controller;

	public FullGameState(FullGameController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}

	@Override
	public void onModeEnd() {
		controller.setEndState();
	}

	@Override
	public void onNewGame() {

	}

	@Override
	public void onDealEnded() {
		model.dealEnded();
	}
	
	@Override
	public void onDeckFinished() {
		model.deckFinished();	
	}
	
	@Override
	public void onExitConfirmed() {
		view.onExitConfirmed();
	}

	@Override
	public void draw() {
		model.draw();
	}

	@Override
	public boolean onScreenDeactivated() {
		return model.exitMode();
	}
	
	@Override
	public void onIchiguFound() {
		IchiguBank.increaseBalance();
		IchiguBank.saveData();
		model.ichiguFound();
		super.onIchiguFound();
	}
}