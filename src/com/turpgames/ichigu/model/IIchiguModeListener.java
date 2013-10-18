package com.turpgames.ichigu.model;

public interface IIchiguModeListener extends ICardListener {
	void onIchiguFound();

	void onInvalidIchiguSelected();
}