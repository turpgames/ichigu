package com.turpgames.ichigu.model.singlegame.minichallenge;

import com.turpgames.ichigu.model.IIchiguModeListener;

public interface IMiniChallengeModeListener extends IIchiguModeListener {
	void onUnblock();

	void onDealTimeUp();

	void onModeEnd();

	void onNewGame();
	
	void onExitConfirmed();
}