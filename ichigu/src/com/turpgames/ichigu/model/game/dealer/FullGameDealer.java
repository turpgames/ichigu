package com.turpgames.ichigu.model.game.dealer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.turpgames.framework.v0.effects.MoveEffect;
import com.turpgames.framework.v0.effects.MoveWithSpeedEffect;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.Table;
import com.turpgames.ichigu.utils.R;

public class FullGameDealer extends Dealer {

	class DealtOutComparator implements Comparator<Vector> {
		@Override
		public int compare(Vector arg0, Vector arg1) {
			return arg0.y < arg1.y ? 1 
					: arg0.y > arg1.y ? -1
							: arg0.x < arg1.x ? 1 
									: arg0.x > arg1.x ? -1 : 0;
		}
	}
	
	private static float fadeDuration = 0.3f;
	private static float inDuration = 0.2f;

	private static float cardSpeed = 1000f;
	private final static Map<Integer, Vector> cardLocations = new HashMap<Integer, Vector>();
	private final static List<Vector> extraCardLocations = new ArrayList<Vector>();
	private final static List<Vector> extraStart = new ArrayList<Vector>();
	private final static Vector inPosition = new Vector(R.sizes.cardWidth / 2, R.sizes.cardWidth / 2);

	static {
		float dy = (Game.getVirtualHeight() - Game.getVirtualWidth()) / 2f - 20;
		for (int i = 0; i < R.counts.fullModeActiveCardCount; i++) {
			int x = i % (R.counts.fullModeCols - 1);
			int y = 2 - i / (R.counts.fullModeCols - 1);

			int dx = 0;

			cardLocations.put(i,
					new Vector(x * R.sizes.cardWidth + R.sizes.cardSpace * (x + 1) + dx, y
							* R.sizes.cardHeight + R.sizes.cardSpace * (y + 1) + dy));
		}

		for (int i = R.counts.fullModeExtraCardCount - 1; i >= 0; i--) {
			int x = R.counts.fullModeCols - 1;
			int y = i;

			int dx = R.sizes.cardSpace + 1;

			extraCardLocations
					.add(new Vector(x * R.sizes.cardWidth + R.sizes.cardSpace * (x + 1) + dx,
							y * R.sizes.cardHeight + R.sizes.cardSpace * (y + 1) + dy));
		}

		extraStart.add(new Vector(extraCardLocations.get(0).tmp()
				.add(R.sizes.cardWidth + 30, 0)));
		extraStart.add(new Vector(extraCardLocations.get(1).tmp()
				.add(R.sizes.cardWidth + 30, 0)));
		extraStart.add(new Vector(extraCardLocations.get(2).tmp()
				.add(R.sizes.cardWidth + 30, 0)));
	}

	public FullGameDealer(Table table) {
		super(table);
		cardsDealingIn = new FullGameCardsIn();
	}

	@Override
	protected void concreteDrawCards() {
		drawCards(getInList());
		drawCards(getOutList());
	}

	@Override
	protected float getDealInInterval() {
		return table.isFirstDeal() ? 0.15f : 0;
	}

	@Override
	protected float getDealOutInterval() {
		return 0;
	}

	@Override
	protected void selectDeal() {
		dealSimultaneous();
	}

	@Override
	protected FullGameCardsIn getInList() {
		return (FullGameCardsIn)cardsDealingIn;
	}
	
	@Override
	protected void setInEffects() {
		FullGameCardsIn cardsDealingIn = getInList();
		List<Card> cardsDealingOut = getOutList();
		List<Card> others = cardsDealingIn.getOthers();
		List<Card> extras = cardsDealingIn.getExtras();
		if (table.isFirstDeal()) {
			MoveWithSpeedEffect moveEffect;
			
			for (int i = 0; i < R.counts.fullModeActiveCardCount; i++) {
				others.get(i).getLocation().set(inPosition);
				moveEffect = new MoveWithSpeedEffect(others.get(i));
				moveEffect.setLooping(false);
				moveEffect.setDestinationAndSpeed(cardLocations.get(i), cardSpeed);
				others.get(i).setDealerEffect(moveEffect);
			}

			for (int i = 0; i < R.counts.fullModeExtraCardCount; i++) {
				extras.get(i).getLocation().set(extraStart.get(i));
				moveEffect = new MoveWithSpeedEffect(extras.get(i));
				moveEffect.setLooping(false);
				moveEffect.setDestinationAndSpeed(extraCardLocations.get(i), cardSpeed);
				extras.get(i).setDealerEffect(moveEffect);
			}
		} else {
			if (cardsDealingIn.size() == 0)
				return;
			
			List<Vector> destinations = new ArrayList<Vector>();
			// the destinations for unused extra cards are added.
			List<Vector> dealtOutDestinations = new ArrayList<Vector>();
			for (int i = 0; i < others.size(); i++) {
				dealtOutDestinations.add(cardsDealingOut.get(i).getLocation());
			}
			Collections.sort(dealtOutDestinations, new DealtOutComparator());
			for (int i = 0; i < dealtOutDestinations.size(); i++) {
				destinations.add(dealtOutDestinations.get(i));
			}

			destinations.addAll(extraCardLocations);

			for (int i = 0; i < extras.size(); i++) {
				extras.get(i).getLocation().set(extraStart.get(i));
			}

			MoveEffect moveEffect;
			for (int i = 0; i < cardsDealingIn.size(); i++) {
				moveEffect = new MoveEffect(cardsDealingIn.get(i));
				moveEffect.setLooping(false);
				moveEffect.setDestination(destinations.get(i));
				moveEffect.setDuration(inDuration);
				cardsDealingIn.get(i).setDealerEffect(moveEffect);
			}
		}
	}

	@Override
	protected void setOutEffects() {
		List<Card> cardsDealingOut = getOutList();
		FadeOutEffect fadeEffect;
		for (int i = 0; i < cardsDealingOut.size(); i++) {
			fadeEffect = new FadeOutEffect(cardsDealingOut.get(i));
			fadeEffect.setDuration(fadeDuration);
			cardsDealingOut.get(i).setDealerEffect(fadeEffect);
		}
	}
}
