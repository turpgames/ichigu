package com.turpgames.ichigu.controller.fullchallenge;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.fullgame.fullchallenge.FullChallengeMode;
import com.turpgames.ichigu.view.FullChallengeModeScreen;

public class FullChallengeModeController extends IchiguController<FullChallengeModeState> implements IFullChallengeModeActionListener {
	final FullChallengeMode model;
	final FullChallengeModeScreen view;

	private FullChallengeModeState waitingState;
	private FullChallengeModeState dealingState;
	private FullChallengeModeState endState;

	public FullChallengeModeController(FullChallengeModeScreen screen) {
		this.view = screen;

		this.model = new FullChallengeMode();
		this.model.setModeListener(this);
		this.model.setDealerListener(this);

		waitingState = new FullChallengeModeWaitingState(this);
		dealingState = new FullChallengeModeDealingState(this);
		endState = new FullChallengeModeEndState(this);
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
	public void onDealEnd() {
		currentState.onDealEnd();		
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