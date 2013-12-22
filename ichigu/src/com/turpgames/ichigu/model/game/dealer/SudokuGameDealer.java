package com.turpgames.ichigu.model.game.dealer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.turpgames.framework.v0.effects.CompositeEffect;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.MoveWithSpeedEffect;
import com.turpgames.framework.v0.effects.ProjectileMoveEffect;
import com.turpgames.framework.v0.effects.ScaleEffect;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.SudokuTable;
import com.turpgames.ichigu.model.game.table.Table;
import com.turpgames.ichigu.utils.R;

public class SudokuGameDealer extends Dealer {
	private final static Vector inPosition = new Vector(R.sizes.cardWidth / 2, R.sizes.cardWidth / 2);
	private final static Vector outPosition = new Vector(Game.getVirtualWidth() + R.sizes.cardWidth / 2, R.sizes.cardWidth / 2);
	
	public final static Map<Integer, Vector> cardLocations = new HashMap<Integer, Vector>();
	static {
		float dy = (Game.getVirtualHeight() - (3 * R.sizes.cardHeight + 2 * 4 * R.sizes.cardSpace)) / 2f;
		float dx = (Game.getVirtualWidth() - (3 * R.sizes.cardWidth + 2 * 4 * R.sizes.cardSpace)) / 2f;
		for (int i = 0; i < 9; i++) {
			int x = i % 3;
			int y = i / 3;

			cardLocations.put(i, new Vector(
					dx + x * (R.sizes.cardWidth + 4 * R.sizes.cardSpace), 
					dy + y * (R.sizes.cardHeight + 4 * R.sizes.cardSpace)));
		}
	}

	private boolean isSwapping;

	private List<Card> swappingCards;
	private float cardSpeed = 1200f;

	public SudokuGameDealer(Table table) {
		super(table);
		this.swappingCards = new ArrayList<Card>();
	}

	@Override
	public boolean isWorking() {
		return isSwapping || super.isWorking();
	}

	public void swap(List<Card> cards) {
		this.swappingCards = cards;
		isSwapping = true;
		IEffectEndListener listener = new IEffectEndListener() {
			boolean firstSwap = false;

			@Override
			public boolean onEffectEnd(Object obj) {
				if (!firstSwap) {
					firstSwap = true;
				} else {
					isSwapping = false;
					((SudokuTable) table).swapEnded();
				}
				return false;
			}
		};

		Vector d1 = new Vector();
		d1.set(swappingCards.get(1).getLocation());
		Vector d2 = new Vector();
		d2.set(swappingCards.get(0).getLocation());

		ProjectileMoveEffect moveEffect;
		ScaleEffect scaleEffect;
		CompositeEffect moveAndScaleEffect;

		moveEffect = new ProjectileMoveEffect(swappingCards.get(0));
		moveEffect.setDestination(d1);
		scaleEffect = new ScaleEffect(swappingCards.get(0));
		scaleEffect.setMaxScale(R.sizes.maxScale);

		moveAndScaleEffect = new CompositeEffect(this, moveEffect, scaleEffect);
		moveAndScaleEffect.setLooping(false);
		moveAndScaleEffect.setDuration(R.durations.sudokuModeSwapDuration);
		swappingCards.get(0).setDealerEffect(moveAndScaleEffect);

		moveEffect = new ProjectileMoveEffect(swappingCards.get(1));
		moveEffect.setDestination(d2);
		scaleEffect = new ScaleEffect(swappingCards.get(1));
		scaleEffect.setMaxScale(R.sizes.maxScale);

		moveAndScaleEffect = new CompositeEffect(this, moveEffect, scaleEffect);
		moveAndScaleEffect.setLooping(false);
		moveAndScaleEffect.setDuration(R.durations.sudokuModeSwapDuration);
		swappingCards.get(1).setDealerEffect(moveAndScaleEffect);

		swappingCards.get(0).startDealerEffect(listener);
		swappingCards.get(1).startDealerEffect(listener);
	}

	@Override
	protected void concreteDrawCards() {
		if (isSwapping) {
			draw(swappingCards);
		} else {
			draw(cardsDealingIn);
			draw(cardsDealingOut);
		}
	}

	@Override
	protected void selectDeal() {
		dealSimultaneous();
	}

	@Override
	protected void setInEffects() {
		for (int i = 0; i < cardsDealingIn.size(); i++)
			cardsDealingIn.get(i).getLocation().set(inPosition);
		MoveWithSpeedEffect moveEffect;
		for (int i = 0; i < cardsDealingIn.size(); i++) {
			moveEffect = new MoveWithSpeedEffect(cardsDealingIn.get(i));
			moveEffect.setLooping(false);
			moveEffect.setDestinationAndSpeed(cardLocations.get(i), cardSpeed);
			cardsDealingIn.get(i).setDealerEffect(moveEffect);
		}
	}

	@Override
	protected void setOutEffects() {
		MoveWithSpeedEffect moveEffect;
		for (int i = 0; i < cardsDealingOut.size(); i++) {
			moveEffect = new MoveWithSpeedEffect(cardsDealingOut.get(i));
			moveEffect.setLooping(false);
			moveEffect.setDestinationAndSpeed(outPosition, cardSpeed);
			cardsDealingOut.get(i).setDealerEffect(moveEffect);
		}
	}
	
	private static void draw(List<Card> cards) {
		for (Card card : cards)
			if (card != null)
				card.draw();
	}
}
