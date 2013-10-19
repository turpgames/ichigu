package com.turpgames.ichigu.model.fullgame;

import com.turpgames.ichigu.model.IIchiguModeListener;

public interface IFullGameModeListener extends IIchiguModeListener {
	void onModeEnd();

	void onNewGame();
}
