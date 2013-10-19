package com.turpgames.ichigu.controller.relax;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.fullgame.relax.RelaxMode;
import com.turpgames.ichigu.view.RelaxModeScreen;

public class RelaxModeController extends IchiguController<RelaxModeState> implements IRelaxModeActionListener {
	final RelaxMode model;
	final RelaxModeScreen view;

	private RelaxModeState waitingState;
	private RelaxModeState dealingState;
	private RelaxModeState pausedState;
	private RelaxModeState endState;

	public RelaxModeController(RelaxModeScreen screen) {
		this.view = screen;

		this.model = new RelaxMode();
		this.model.setModeListener(this);

		waitingState = new RelaxModeWaitingState(this);
		dealingState = new RelaxModeDealingState(this);
		pausedState = new RelaxModePausedState(this);
		endState = new RelaxModeEndState(this);
	}

	@Override
	public void onScreenActivated() {
		if (currentState == null) {
			model.startMode();
			setDealingState();
		}
		else {
			super.onScreenActivated();
		}
	}

	@Override
	public void onModeEnd() {
		currentState.onModeEnd();
	}

	@Override
	public void onNewGame() {
		currentState.onNewGame();
	}

	void setDealingState() {
		setState(dealingState);
	}

	void setWaitingState() {
		setState(waitingState);
	}

	void setPausedState() {
		setState(pausedState);
	}

	void setEndState() {
		setState(endState);
	}
}