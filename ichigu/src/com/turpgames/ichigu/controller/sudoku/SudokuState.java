package com.turpgames.ichigu.controller.sudoku;

import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.game.mode.SudokuMode;
import com.turpgames.ichigu.model.game.table.ISudokuTableListener;
import com.turpgames.ichigu.view.IchiguScreen;

public abstract class SudokuState extends IchiguState implements ISudokuTableListener {
	final SudokuMode model;
	final IchiguScreen view;
	final SudokuController controller;

	public SudokuState(SudokuController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}

	@Override
	public void draw() {
		model.draw();
	}

	@Override
	public void onDealEnded() {
		model.dealEnded();
	}
	
	@Override
	public void onDealStarted() {
		model.dealStarted();
	}
	
	@Override
	public void onExitConfirmed() {
		view.onExitConfirmed();
	}

	@Override
	public void onModeEnd() {
		controller.setEndState();
	}

	@Override
	public void onNewGame() {

	}

	@Override
	public boolean onScreenDeactivated() {
		return model.exitMode();
	}
	
	@Override
	public void onSwapEnded() {
		model.swapEnded();
	}
	
	@Override
	public void onSwapStarted() {
		model.swapStarted();
	}

	@Override
	public void onTableFinished() {
		model.tableFinished();
	}
}