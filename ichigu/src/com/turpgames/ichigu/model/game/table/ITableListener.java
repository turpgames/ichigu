package com.turpgames.ichigu.model.game.table;

import com.turpgames.ichigu.model.game.ICardListener;

public interface ITableListener extends ICardListener {
	void onDealStarted();
	
	void onDealEnded();

	void onTableFinished();
}
