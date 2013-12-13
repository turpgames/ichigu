package com.turpgames.ichigu.controller.sudoku;

public class SudokuDealingState extends SudokuState {
	public SudokuDealingState(SudokuController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		model.deal();
	}

	@Override
	public void onDealEnded() {
		model.dealEnded();
		controller.setWaitingState();
	}

	@Override
	public boolean onScreenDeactivated() {
		return false;
	}
}
