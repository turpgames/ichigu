package com.turpgames.ichigu.controller.sudoku;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.game.mode.SudokuMode;
import com.turpgames.ichigu.model.game.table.ISudokuTableListener;
import com.turpgames.ichigu.view.IchiguScreen;

public class SudokuController extends IchiguController<SudokuState> implements ISudokuTableListener {
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
	public void onSwapEnded() {
		currentState.onSwapEnded();
	}

	@Override
	public void onSwapStarted() {
		currentState.onSwapStarted();
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

}