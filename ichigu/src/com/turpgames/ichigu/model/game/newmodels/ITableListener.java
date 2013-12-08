package com.turpgames.ichigu.model.game.newmodels;

import com.turpgames.ichigu.model.game.ICardListener;

public interface ITableListener extends ICardListener {
	void onIchiguFound();

	void onInvalidIchiguSelected();
	
	void onDealEnded();

	void onDeckFinished();
}
