package com.turpgames.ichigu.model.game.newmodels;

import java.util.List;

import com.turpgames.ichigu.model.game.Card;

public interface IDealerListener {
	void onDeckFinished();

	void onDealEnded(List<Card> dealtIn, List<Card> dealtOut);
}
