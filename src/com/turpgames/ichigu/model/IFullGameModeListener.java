package com.turpgames.ichigu.model;

public interface IFullGameModeListener extends IIchiguModeListener {
	void onModeEnd();

	void onNewGame();
}
