package com.turpgames.ichigu.controller.minichallenge;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.Card;
import com.turpgames.ichigu.model.singlegame.minichallenge.MiniChallengeMode;
import com.turpgames.ichigu.view.MiniChallengeModeScreen;

public class MiniChallengeModeController extends IchiguController<MiniChallengeModeState> implements IMiniChallengeModeActionListener {
	final MiniChallengeMode model;
	final MiniChallengeModeScreen view;

	private MiniChallengeModeState waitingState;
	private MiniChallengeModeState dealingState;
	private MiniChallengeModeState blockedState;
	private MiniChallengeModeState modeEndState;

	public MiniChallengeModeController(MiniChallengeModeScreen screen) {
		view = screen;
		model = new MiniChallengeMode();
		model.setModeListener(this);
		waitingState = new MiniChallengeModeWaitingState(this);
		dealingState = new MiniChallengeModeDealingState(this);
		blockedState = new MiniChallengeModeBlockedState(this);
		modeEndState = new MiniChallengeModeEndState(this);
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