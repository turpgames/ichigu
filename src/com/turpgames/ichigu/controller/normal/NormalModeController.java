package com.turpgames.ichigu.controller.normal;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.fullgame.normal.NormalMode;
import com.turpgames.ichigu.view.NormalModeScreen;

public class NormalModeController extends IchiguController<NormalModeState> implements INormalModeActionListener {
	final NormalMode model;
	final NormalModeScreen view;

	private NormalModeState waitingState;
	private NormalModeState dealingState;
	private NormalModeState endState;

	public NormalModeController(NormalModeScreen screen) {
		this.view = screen;

		this.model = new NormalMode();
		this.model.setModeListener(this);

		waitingState = new NormalModeWaitingState(this);
		dealingState = new NormalModeDealingState(this);
		endState = new NormalModeEndState(this);
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