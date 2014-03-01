package com.turpgames.ichigu.model.game.table;

public interface IRegularTableListener extends ITableListener {
	void onIchiguFound();

	void onInvalidIchiguSelected();
}
