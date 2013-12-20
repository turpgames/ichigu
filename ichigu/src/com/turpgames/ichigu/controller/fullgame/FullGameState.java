package com.turpgames.ichigu.controller.fullgame;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.game.IchiguBank;
import com.turpgames.ichigu.model.game.mode.IIchiguModeListener;
import com.turpgames.ichigu.model.game.mode.fullgame.FullGameMode;
import com.turpgames.ichigu.model.game.table.IFullGameTableListener;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.view.IchiguScreen;

public abstract class FullGameState extends IchiguState implements IIchiguModeListener, IFullGameTableListener {
	final FullGameMode model;
	final IchiguScreen view;
	final FullGameController controller;

	public FullGameState(FullGameController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}

	@Override
	public void draw() {
		model.draw();
	}

	@Override
	public void onDealEnded() {
		model.dealEnded();
	}
	
	@Override
	public void onDealStarted() {
		model.dealStarted();
	}
	
	@Override
	public void onExitConfirmed() {
		view.onExitConfirmed();
	}

	@Override
	public void onIchiguFound() {
		IchiguBank.increaseBalance();
		IchiguBank.saveData();
		model.ichiguFound();
		Ichigu.playSoundSuccess();
		Game.vibrate(100);
	}

	@Override
	public void onInvalidIchiguSelected() {
		Ichigu.playSoundError();
		Game.vibrate(0, 50, 50, 100);
	}

	@Override
	public void onModeEnd() {
		controller.setEndState();
	}
	
	@Override
	public void onNewGame() {

	}
	
	@Override
	public void onOpenedExtraCardsWhileThereIsIchigu() {
		model.applyTimePenalty();
	}

	@Override
	public boolean onScreenDeactivated() {
		return model.exitMode();
	}

	@Override
	public void onTableFinished() {
		model.deckFinished();	
	}
}