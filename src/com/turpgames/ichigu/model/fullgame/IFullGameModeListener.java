package com.turpgames.ichigu.model.fullgame;

import com.turpgames.ichigu.model.game.IIchiguModeListener;

public interface IFullGameModeListener extends IIchiguModeListener {
	void onModeEnd();

	void onNewGame();
}
