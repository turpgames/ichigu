package com.blox.ichigu.controller.practice;

import com.blox.ichigu.controller.IchiguController;
import com.blox.ichigu.model.Card;
import com.blox.ichigu.model.PracticeMode;
import com.blox.ichigu.view.PracticeModeScreen;

public class PracticeModeController extends IchiguController<PracticeModeState> implements IPracticeModeActionListener {
	final PracticeMode model;
	final PracticeModeScreen view;

	private PracticeModeState waitingState;
	private PracticeModeState dealingState;
	private PracticeModeState blockedState;
	private PracticeModeState modeEndState;

	public PracticeModeController(PracticeModeScreen screen) {
		view = screen;
		model = new PracticeMode();
		model.setModeListener(this);
		waitingState = new PracticeModeWaitingState(this);
		dealingState = new PracticeModeDealingState(this);
		blockedState = new PracticeModeBlockedState(this);
		modeEndState = new PracticeModeEndState(this);
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
	public void onCardTapped(Card card) {
		currentState.onCardTapped(card);
	}

	@Override
	public void onUnblock() {
		currentState.onUnblock();
	}

	@Override
	public void onDealTimeUp() {
		currentState.onDealTimeUp();
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
}