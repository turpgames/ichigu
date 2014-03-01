package com.turpgames.ichigu.model.game.mode;

import com.turpgames.ichigu.model.game.table.ITableListener;

public interface IIchiguModeListener extends ITableListener {
	void onExitConfirmed();

	void onModeEnd();

	void onNewGame();
}