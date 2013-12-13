package com.turpgames.ichigu.controller.sudoku;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.game.mode.ISudokuModeListener;
import com.turpgames.ichigu.model.game.mode.SudokuMode;
import com.turpgames.ichigu.view.IIchiguViewListener;
import com.turpgames.ichigu.view.IchiguScreen;

public class SudokuController extends IchiguController<SudokuState> implements IIchiguViewListener, ISudokuModeListener {
	final SudokuMode model;
	final IchiguScreen view;

	private SudokuState waitingState;
	private SudokuState dealingState;
	private SudokuState endState;

	public SudokuController(IchiguScreen screen, SudokuMode model) {
		this.view = screen;

		this.model = model;
		this.model.setModeListener(this);

		waitingState = new SudokuWaitingState(this);
		dealingState = new SudokuDealingState(this);
		endState = new SudokuEndState(this);
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
	public void onDealStarted() {
		currentState.onDealStarted();
	}
	
	@Override
	public void onDealEnded() {
		currentState.onDealEnded();		
	}
	
	@Override
	public void onTableFinished() {
		currentState.onTableFinished();	
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