package com.turpgames.ichigu.model.game;

public interface ICardDealerListener extends ICardListener {
	void onDealEnded();

	void onDeckFinished();
	
	void onIchiguFound();

	void onInvalidIchiguSelected();

}
