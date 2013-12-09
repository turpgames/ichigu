package com.turpgames.ichigu.model.game.dealer;

import com.turpgames.ichigu.model.game.ICardListener;

public interface ITableListener extends ICardListener {
	void onIchiguFound();

	void onInvalidIchiguSelected();
	
	void onDealStarted();
	
	void onDealEnded();

	void onTableFinished();
}
