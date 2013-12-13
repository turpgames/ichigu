package com.turpgames.ichigu.model.game.dealer;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.Table;

public class SudokuGameDealer extends Dealer {

	private final static Map<Integer, Vector> cardLocations = new HashMap<Integer, Vector>();
	static {
		float dy = (Game.getVirtualHeight() - Game.getVirtualWidth()) / 2f - 20;
		float dx = (Game.getVirtualWidth() - 3 * Card.Width) / 2;
		for (int i = 0; i < 9; i++) {
			int x = i % 3;
			int y = i / 3;


			cardLocations.put(i, new Vector(
					x * Card.Width + Card.Space * (x + 1) + dx,
					y * Card.Height + Card.Space * (y + 1) + dy));
		}
	}
	public SudokuGameDealer(Table table) {
		super(table);
	}

	@Override
	protected void selectDeal() {
		dealConsecutive();
	}

	@Override
	protected void setOutEffects() {
		
	}

	@Override
	protected void setInEffects() {
		for (int i = 0; i < 9; i++)
			cardsDealingIn.get(i).getLocation().set(cardLocations.get(i));
	}
}
