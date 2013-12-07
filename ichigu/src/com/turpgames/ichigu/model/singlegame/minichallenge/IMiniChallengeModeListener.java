package com.turpgames.ichigu.model.singlegame.minichallenge;

import com.turpgames.ichigu.model.game.ICardDealerListener;
import com.turpgames.ichigu.model.game.IIchiguModeListener;

public interface IMiniChallengeModeListener extends IIchiguModeListener, ICardDealerListener {
	void onUnblock();

	void onDealTimeUp();

	void onModeEnd();

	void onNewGame();
	
	void onExitConfirmed();
}