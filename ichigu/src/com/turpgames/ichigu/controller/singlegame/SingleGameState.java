package com.turpgames.ichigu.controller.singlegame;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.game.mode.singlegame.ISingleGameModeListener;
import com.turpgames.ichigu.model.game.mode.singlegame.SingleGameMode;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.view.IchiguScreen;

public abstract class SingleGameState extends IchiguState implements ISingleGameModeListener {
	final SingleGameMode model;
	final IchiguScreen view;
	final SingleGameController controller;

	public SingleGameState(SingleGameController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}

	@Override
	public boolean onScreenDeactivated() {
		return model.exitMode();
	}
	
	@Override
	public void onUnblock() {
		controller.setWaitingState();
	}

	@Override
	public void onModeEnd() {
		Ichigu.playSoundTimeUp();
		Game.vibrate(100);
		controller.setModeEndState();
	}

	@Override
	public void onNewGame() {

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
	public void onDealStarted() {
		model.dealStarted();
	}
	
	@Override
	public void onDealEnded() {
		model.dealEnded();
	}
	
	@Override
	public void onTableFinished() {
		
	}
}