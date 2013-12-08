package com.turpgames.ichigu.controller.fullgame;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.model.game.IIchiguModeListener;
import com.turpgames.ichigu.view.IchiguScreen;

public class FullGameController extends IchiguController<FullGameState> implements IIchiguModeListener {
	final FullGameMode model;
	final IchiguScreen view;

	private FullGameState waitingState;
	private FullGameState dealingState;
	private FullGameState endState;

	public FullGameController(IchiguScreen screen, FullGameMode model) {
		this.view = screen;

		this.model = model;
		this.model.setModeListener(this);

		waitingState = new FullGameWaitingState(this);
		dealingState = new FullGameDealingState(this);
		endState = new FullGameEndState(this);
	}

	@Override
	public void onScreenActivated() {
		model.startMode();
		setDealingState();
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
	public void onDealEnded() {
		currentState.onDealEnded();		
	}
	
	@Override
	public void onDeckFinished() {
		currentState.onDeckFinished();	
	}
	
	@Override
	public void onExitConfirmed() {
		currentState.onExitConfirmed();
	}

	void setDealingState() {
		setState(dealingState);
	}

	void setWaitingState() {
		setState(waitingState);
	}

	void setEndState() {
		setState(endState);
	}
}