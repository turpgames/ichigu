package com.turpgames.ichigu.controller.minichallenge;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.game.singlegame.ISingleGameModeListener;
import com.turpgames.ichigu.model.game.singlegame.SingleGameMode;
import com.turpgames.ichigu.view.IchiguScreen;

public class SingleGameController extends IchiguController<SingleGameState> implements ISingleGameModeListener {
	final SingleGameMode model;
	final IchiguScreen view;

	private SingleGameState waitingState;
	private SingleGameState dealingState;
	private SingleGameState blockedState;
	private SingleGameState modeEndState;

	public SingleGameController(IchiguScreen screen, SingleGameMode model) {
		this.view = screen;
		this.model = model;
		this.model.setModeListener(this);
		waitingState = new SingleGameWaitingState(this);
		dealingState = new SingleGameDealingState(this);
		blockedState = new SingleGameBlockedState(this);
		modeEndState = new SingleGameEndState(this);
	}

	@Override
	public void onScreenActivated() {
		model.startMode();
		setDealingState();
	}

	@Override
	public void onNewGame() {
		currentState.onNewGame();
	}

	@Override
	public void onUnblock() {
		currentState.onUnblock();
	}

	@Override
	public void onExitConfirmed() {
		currentState.onExitConfirmed();
	}
	
	@Override
	public void onModeEnd() {
		currentState.onModeEnd();
	}

	void setDealingState() {
		setState(dealingState);
	}

	void setWaitingState() {
		setState(waitingState);
	}

	void setBlockedState() {
		setState(blockedState);
	}

	void setModeEndState() {
		setState(modeEndState);
	}

	@Override
	public void onDealStarted() {
		currentState.onDealStarted();
	}
	
	@Override
	public void onDealEnded() {
		currentState.onDealEnded();
	}

	@Override
	public void onTableFinished() {
		
	}
}