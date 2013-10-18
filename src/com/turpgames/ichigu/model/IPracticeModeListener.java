package com.turpgames.ichigu.model;

public interface IPracticeModeListener extends IIchiguModeListener {
	void onUnblock();

	void onDealTimeUp();

	void onModeEnd();

	void onNewGame();
	
	void onExitConfirmed();
}