package com.turpgames.ichigu.model.game;

import com.turpgames.ichigu.model.game.dealer.ITableListener;

public interface IIchiguModeListener extends ITableListener {
	void onExitConfirmed();

	void onModeEnd();

	void onNewGame();
}