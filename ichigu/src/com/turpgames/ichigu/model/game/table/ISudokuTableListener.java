package com.turpgames.ichigu.model.game.table;

public interface ISudokuTableListener extends ITableListener {
	void onSwapStarted();

	void onSwapEnded();
}
