package com.turpgames.ichigu.model.game;

public interface IIchiguModeListener extends ICardDealerListener {
	void onExitConfirmed();

	void onModeEnd();

	void onNewGame();
}