package com.turpgames.ichigu.model.game.table;

import com.turpgames.ichigu.model.game.CyclicDeck;

public class TimeChallengeTable extends FullGameTable {
	public TimeChallengeTable() {
		super();
		deck = new CyclicDeck(this);
	}
}
