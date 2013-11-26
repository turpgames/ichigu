package com.turpgames.ichigu.model.fullgame;

import com.turpgames.ichigu.model.game.ICardDealerListener;
import com.turpgames.ichigu.model.game.IIchiguModeListener;

public interface IFullGameModeListener extends IIchiguModeListener, ICardDealerListener {
	void onModeEnd();

	void onNewGame();
}
