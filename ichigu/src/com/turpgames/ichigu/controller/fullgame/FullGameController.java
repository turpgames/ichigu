package com.turpgames.ichigu.controller.fullgame;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.game.mode.fullgame.FullGameMode;
import com.turpgames.ichigu.model.game.mode.fullgame.IFullGameModeListener;
import com.turpgames.ichigu.model.game.table.IFullGameTableListener;
import com.turpgames.ichigu.view.IchiguScreen;

public class FullGameController extends IchiguController<FullGameState> implements IFullGameModeListener, IFullGameTableListener {
	final FullGameMode model;
	final IchiguScreen view;

	private FullGameState waitingState;
	private FullGameState dealingState;
	private FullGameState endState;
	private FullGamePausedForMarketState pausedForMarketState;

	public FullGameController(IchiguScreen screen, FullGameMode model) {
		this.view = screen;

		this.model = model;
		this.model.setModeListener(this);

		waitingState = new FullGameWaitingState(this);
		dealingState = new FullGameDealingState(this);
		endState = new FullGameEndState(this);
		pausedForMarketState = new FullGamePausedForMarketState(this);
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
	public void onOpenedExtraCardsWhileThereIsIchigu() {
		currentState.onOpenedExtraCardsWhileThereIsIchigu();
	}

	@Override
	public void onPauseForMarketMenu() {
		currentState.onPauseForMarketMenu();
	}

	@Override
	public void onScreenActivated() {
		if (currentState == null) {
			model.startMode();
			setDealingState();
		}
		else {
			currentState.onScreenActivated();
		}
	}

	@Override
	public void onTableFinished() {
		currentState.onTableFinished();
	}

	void setDealingState() {
		setState(dealingState);
	}

	void setEndState() {
		setState(endState);
	}

	void setWaitingState() {
		setState(waitingState);
	}

	void setPausedForMarketState() {
		setState(pausedForMarketState);
	}
}