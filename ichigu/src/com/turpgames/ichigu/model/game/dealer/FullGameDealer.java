package com.turpgames.ichigu.model.game.dealer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.turpgames.framework.v0.effects.MoveEffect;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.FullGameTable;
import com.turpgames.ichigu.model.game.table.Table;

public class FullGameDealer extends Dealer {

	private static float fadeDuration = 2f;
	private static float inDuration = 2f;
	
	
	private final static Map<Integer, Vector> cardLocations = new HashMap<Integer, Vector>();
	private final static List<Vector> extraCardLocations = new ArrayList<Vector>();
	private final static List<List<Vector>> inPositions = new ArrayList<List<Vector>>();
	static {
		float dy = (Game.getVirtualHeight() - Game.getVirtualWidth()) / 2f - 20;
		for (int i = 0; i < FullGameTable.ActiveCardCount; i++) {
			int x = i % (FullGameTable.cols - 1);
			int y = i / (FullGameTable.cols - 1);

			int dx = 0;

			cardLocations.put(i, new Vector(
					x * Card.Width + Card.Space * (x + 1) + dx,
					y * Card.Height + Card.Space * (y + 1) + dy));
		}
		
		for (int i = 0; i < FullGameTable.ExtraCardCount; i++) {
			int x = FullGameTable.cols - 1;
			int y = i;

			int dx = Card.Space + 1;

			extraCardLocations.add(new Vector(
					x * Card.Width + Card.Space * (x + 1) + dx,
					y * Card.Height + Card.Space * (y + 1) + dy));
		}
		
		List<Vector> inStart = new ArrayList<Vector>();
		inStart.add(new Vector(0,0));
		inStart.add(new Vector(0,0));
		inStart.add(new Vector(0,0));
		
		List<Vector> inDestination = new ArrayList<Vector>();
		inDestination.addAll(extraCardLocations);
		
		inPositions.add(inStart);
		inPositions.add(inDestination);
	}
	
	public FullGameDealer(Table table) {
		super(table);
	}

	@Override
	protected void selectDeal() {
		dealSimultaneous();
	}

	@Override
	protected void setOutEffects() {
		FadeOutEffect fadeEffect;
		for (int i = 0; i < cardsDealingOut.size(); i++) {
			fadeEffect = new FadeOutEffect(cardsDealingOut.get(i));
			fadeEffect.setDuration(fadeDuration);
			cardsDealingOut.get(i).setDealerEffect(fadeEffect);
		}
	}

	@Override
	protected void setInEffects() {
		if(table.isFirstDeal()) {
			for (int i = 0; i < FullGameTable.ExtraCardCount; i++)
				cardsDealingIn.get(i).getLocation().set(extraCardLocations.get(i));
			for (int i = 0; i < FullGameTable.ActiveCardCount; i++)
				cardsDealingIn.get(i + FullGameTable.ExtraCardCount).getLocation().set(cardLocations.get(i));
		}
		else {
			// first three of cardsToDealOut are new cards, remaining cards are unused extra cards repositioned
			for (int i = 0; i < 3; i++) {
				if (cardsDealingIn.get(i) == null)
					continue;
				cardsDealingIn.get(i).getLocation().set(inPositions.get(0).get(i));
			}
			
			// the destinations for unused extra cards are added.
			for (int i = 3; i < cardsDealingIn.size(); i++) {
				inPositions.get(1).add(i, cardsDealingOut.get(i-3).getLocation());	
			}
			
			MoveEffect moveEffect;
			for (int i = 0; i < cardsDealingIn.size(); i++) {
				if (cardsDealingIn.get(i) == null)
					continue;
				moveEffect = new MoveEffect(cardsDealingIn.get(i));
				moveEffect.setLooping(false);
				moveEffect.setDestination(inPositions.get(1).get(i));
				moveEffect.setDuration(inDuration);
				cardsDealingIn.get(i).setDealerEffect(moveEffect);
			}
		}
	}
}
