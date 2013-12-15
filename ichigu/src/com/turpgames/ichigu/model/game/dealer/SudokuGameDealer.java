package com.turpgames.ichigu.model.game.dealer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.turpgames.framework.v0.effects.CompositeEffect;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.MoveEffect;
import com.turpgames.framework.v0.effects.ProjectileMoveEffect;
import com.turpgames.framework.v0.effects.ScaleEffect;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.SudokuTable;
import com.turpgames.ichigu.model.game.table.Table;

public class SudokuGameDealer extends Dealer {
	public static float inDuration = 0.15f;
	public static float outDuration = 0.15f;
	
	private final static float swapDuration = 0.5f;
	private final static float maxScale = 0.15f;

	private final static Vector inPosition = new Vector(Card.Width/2, Card.Width/2);
	private final static Vector outPosition = new Vector(Game.getVirtualWidth() + Card.Width/2, Card.Width/2);
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
	
	private boolean isSwapping;

	private List<Card> swappingCards;
	
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
				}
				else {
					isSwapping = false;
					((SudokuTable)table).swapEnded();
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
		scaleEffect.setMaxScale(maxScale);
		
		moveAndScaleEffect = new CompositeEffect(this, moveEffect, scaleEffect);
		moveAndScaleEffect.setLooping(false);
		moveAndScaleEffect.setDuration(swapDuration);
		swappingCards.get(0).setDealerEffect(moveAndScaleEffect);
		
		
		moveEffect = new ProjectileMoveEffect(swappingCards.get(1));
		moveEffect.setDestination(d2);
		scaleEffect = new ScaleEffect(swappingCards.get(1));
		scaleEffect.setMaxScale(maxScale);
		
		moveAndScaleEffect = new CompositeEffect(this, moveEffect, scaleEffect);
		moveAndScaleEffect.setLooping(false);
		moveAndScaleEffect.setDuration(swapDuration);
		swappingCards.get(1).setDealerEffect(moveAndScaleEffect);
		
		swappingCards.get(0).startDealerEffect(listener);
		swappingCards.get(1).startDealerEffect(listener);
	}
	@Override
	protected void concreteDrawCards() {
		for(Card card : swappingCards)
			if (card != null)
				card.draw();
		for(Card card : cardsDealingIn)
			if (card != null)
				card.draw();
		for(Card card : cardsDealingOut)
			if (card != null)
				card.draw();
	}

	@Override
	protected void selectDeal() {
		dealSimultaneous();
	}

	@Override
	protected void setInEffects() {
		for (int i = 0; i < cardsDealingIn.size(); i++)
			cardsDealingIn.get(i).getLocation().set(inPosition);
		MoveEffect moveEffect;
		for (int i = 0; i < cardsDealingIn.size(); i++) {
			moveEffect = new MoveEffect(cardsDealingIn.get(i));
			moveEffect.setLooping(false);
			moveEffect.setDestination(cardLocations.get(i));
			moveEffect.setDuration(inDuration);
			cardsDealingIn.get(i).setDealerEffect(moveEffect);
		}
	}
	
	@Override
	protected void setOutEffects() {
		MoveEffect moveEffect;
		for (int i = 0; i < cardsDealingOut.size(); i++) {
			moveEffect = new MoveEffect(cardsDealingOut.get(i));
			moveEffect.setLooping(false);
			moveEffect.setDestination(outPosition);
			moveEffect.setDuration(outDuration);
			cardsDealingOut.get(i).setDealerEffect(moveEffect);
		}
	}
}
