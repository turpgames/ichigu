package com.turpgames.ichigu.model.game.dealer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.turpgames.framework.v0.effects.MoveWithSpeedEffect;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.FullGameTable;
import com.turpgames.ichigu.model.game.table.Table;

public class FullGameDealer extends Dealer {

	private static float fadeDuration = 0.3f;
	private static float cardSpeed = 1000f;

	private final static Map<Integer, Vector> cardLocations = new HashMap<Integer, Vector>();
	private final static List<Vector> extraCardLocations = new ArrayList<Vector>();
	private final static List<Vector> extraStart = new ArrayList<Vector>();
	private final static Vector inPosition = new Vector(Card.Width / 2,
			Card.Width / 2);
	static {
		float dy = (Game.getVirtualHeight() - Game.getVirtualWidth()) / 2f - 20;
		for (int i = 0; i < FullGameTable.ActiveCardCount; i++) {
			int x = i % (FullGameTable.cols - 1);
			int y = 2 - i / (FullGameTable.cols - 1);

			int dx = 0;

			cardLocations.put(i,
					new Vector(x * Card.Width + Card.Space * (x + 1) + dx, y
							* Card.Height + Card.Space * (y + 1) + dy));
		}

		for (int i = FullGameTable.ExtraCardCount - 1; i >= 0; i--) {
			int x = FullGameTable.cols - 1;
			int y = i;

			int dx = Card.Space + 1;

			extraCardLocations
					.add(new Vector(x * Card.Width + Card.Space * (x + 1) + dx,
							y * Card.Height + Card.Space * (y + 1) + dy));
		}

		extraStart.add(new Vector(extraCardLocations.get(0).tmp()
				.add(Card.Width + 30, 0)));
		extraStart.add(new Vector(extraCardLocations.get(1).tmp()
				.add(Card.Width + 30, 0)));
		extraStart.add(new Vector(extraCardLocations.get(2).tmp()
				.add(Card.Width + 30, 0)));
	}

	public FullGameDealer(Table table) {
		super(table);
	}

	@Override
	protected void selectDeal() {
		dealSimultaneous();
	}

	@Override
	protected float getDealOutInterval() {
		return 0;
	}

	@Override
	protected float getDealInInterval() {
		return table.isFirstDeal() ? 0.15f : 0;
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
		if (table.isFirstDeal()) {
			MoveWithSpeedEffect moveEffect;
			for (int i = 0; i < FullGameTable.ActiveCardCount; i++) {
				cardsDealingIn.get(i).getLocation().set(inPosition);
				moveEffect = new MoveWithSpeedEffect(cardsDealingIn.get(i));
				moveEffect.setLooping(false);
				moveEffect.setDestinationAndSpeed(cardLocations.get(i), cardSpeed);
				cardsDealingIn.get(i).setDealerEffect(moveEffect);
			}

			for (int i = 0; i < FullGameTable.ExtraCardCount; i++) {
				cardsDealingIn.get(i + FullGameTable.ActiveCardCount)
						.getLocation().set(extraStart.get(i));
				moveEffect = new MoveWithSpeedEffect(cardsDealingIn.get(i + FullGameTable.ActiveCardCount));
				moveEffect.setLooping(false);
				moveEffect.setDestinationAndSpeed(extraCardLocations.get(i), cardSpeed);
				cardsDealingIn.get(i + FullGameTable.ActiveCardCount)
						.setDealerEffect(moveEffect);
			}
		} else {
			// last three of cardsToDealOut are new cards, the first cards are
			// unused extra cards repositioned

			List<Vector> destinations = new ArrayList<Vector>();
			// the destinations for unused extra cards are added.
			List<Vector> dealtOutDestinations = new ArrayList<Vector>();
			for (int i = 0; i < cardsDealingIn.size() - 3; i++) {
				dealtOutDestinations.add(cardsDealingOut.get(i).getLocation());
			}
			Collections.sort(dealtOutDestinations, new DealtOutComparator());
			for (int i = 0; i < cardsDealingIn.size() - 3; i++) {
				destinations.add(dealtOutDestinations.get(i));
			}

			destinations.addAll(extraCardLocations);

			for (int i = cardsDealingIn.size() - 3; i < cardsDealingIn.size(); i++) {
				if (cardsDealingIn.get(i) == null)
					continue;
				cardsDealingIn.get(i).getLocation()
						.set(extraStart.get(i - (cardsDealingIn.size() - 3)));
			}

			MoveWithSpeedEffect moveEffect;
			for (int i = 0; i < cardsDealingIn.size(); i++) {
				if (cardsDealingIn.get(i) == null)
					continue;
				moveEffect = new MoveWithSpeedEffect(cardsDealingIn.get(i));
				moveEffect.setLooping(false);
				moveEffect.setDestinationAndSpeed(destinations.get(i), cardSpeed);
				cardsDealingIn.get(i).setDealerEffect(moveEffect);
			}
		}
	}

	@Override
	protected void concreteDrawCards() {
		for (Card card : cardsDealingIn)
			if (card != null)
				card.draw();
		for (Card card : cardsDealingOut)
			card.draw();
	}

	class DealtOutComparator implements Comparator<Vector> {

		@Override
		public int compare(Vector arg0, Vector arg1) {
			return arg0.y < arg1.y ? 1 
					: arg0.y > arg1.y ? -1
							: arg0.x < arg1.x ? 1 
									: arg0.x > arg1.x ? -1 : 0;
		}
	}
}
