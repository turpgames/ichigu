package com.turpgames.ichigu.model.game.dealer;

import java.util.List;

import com.turpgames.ichigu.model.game.Card;

public interface IDealerListener {
	void onDealEnded(List<Card> dealtIn, List<Card> dealtOut);

	void onDeckFinished();
}
