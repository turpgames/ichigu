package com.turpgames.ichigu.view;

import com.turpgames.ichigu.controller.sudoku.SudokuController;
import com.turpgames.ichigu.model.game.mode.sudoku.SudokuMode;

public class SudokuModeScreen extends IchiguScreen {
	@Override
	public void init() {
		super.init();
		setScreenListener(new SudokuController(this, new SudokuMode()));
	}
}
