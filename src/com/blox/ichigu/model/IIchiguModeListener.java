package com.blox.ichigu.model;

public interface IIchiguModeListener extends ICardListener {
	void onIchiguFound();

	void onInvalidIchiguSelected();
}