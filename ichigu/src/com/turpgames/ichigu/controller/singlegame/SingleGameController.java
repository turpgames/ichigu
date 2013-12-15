package com.turpgames.ichigu.controller.singlegame;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.game.mode.singlegame.ISingleGameModeListener;
import com.turpgames.ichigu.model.game.mode.singlegame.SingleGameMode;
import com.turpgames.ichigu.model.game.table.IRegularTableListener;
import com.turpgames.ichigu.view.IchiguScreen;

public class SingleGameController extends IchiguController<SingleGameState> implements ISingleGameModeListener, IRegularTableListener {
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
	public void onDealEnded() {
		currentState.onDealEnded();
	}

	@Override
	public void onDealStarted() {
		currentState.onDealStarted();
	}

	@Override
	public void onExitConfirmed() {
		currentState.onExitConfirmed();
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
	public void onModeEnd() {
		currentState.onModeEnd();
	}
	
	@Override
	public void onNewGame() {
		currentState.onNewGame();
	}
	
	@Override
	public void onScreenActivated() {
		model.startMode();
		setDealingState();
	}

	@Override
	public void onTableFinished() {
		
	}

	@Override
	public void onUnblock() {
		currentState.onUnblock();
	}

	void setBlockedState() {
		setState(blockedState);
	}

	void setDealingState() {
		setState(dealingState);
	}
	
	void setModeEndState() {
		setState(modeEndState);
	}

	void setWaitingState() {
		setState(waitingState);
	}
}