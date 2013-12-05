package com.turpgames.ichigu.model.game;

public interface ICardDealerListener {
	void onDealEnd();
	
	void onCardsActivated();
	
	void onCardsDeactivated();
}
