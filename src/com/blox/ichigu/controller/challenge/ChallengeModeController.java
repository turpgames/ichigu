package com.blox.ichigu.controller.challenge;

import com.blox.ichigu.controller.IchiguController;
import com.blox.ichigu.model.ChallengeMode;
import com.blox.ichigu.view.ChallengeModeScreen;

public class ChallengeModeController extends IchiguController<ChallengeModeState> implements IChallengeModeActionListener {
	final ChallengeMode model;
	final ChallengeModeScreen view;

	private ChallengeModeState waitingState;
	private ChallengeModeState dealingState;
	private ChallengeModeState endState;

	public ChallengeModeController(ChallengeModeScreen screen) {
		this.view = screen;

		this.model = new ChallengeMode();
		this.model.setModeListener(this);

		waitingState = new ChallengeModeWaitingState(this);
		dealingState = new ChallengeModeDealingState(this);
		endState = new ChallengeModeEndState(this);
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