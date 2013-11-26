package com.turpgames.ichigu.model.game;

public interface IIchiguModeListener extends ICardListener {
	void onIchiguFound();

	void onInvalidIchiguSelected();

	void onExitConfirmed();
}