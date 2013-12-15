package com.turpgames.ichigu.model.game.dealer;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.effects.MoveEffect;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.Table;
import com.turpgames.ichigu.utils.R;

public class SingleGameDealer extends Dealer {

	public static float inDuration = 0.15f;
	public static float outDuration = 0.15f;
	
	private final static Vector selectedStart = new Vector(- Card.Width, (Game.getVirtualHeight() - Card.Height) / 2);
	private final static Vector toSelectStart = new Vector(- Card.Width, (Game.getVirtualHeight() - Card.Height) / 2);
	private final static Vector selectedDestination = new Vector(Game.getVirtualWidth(), (Game.getVirtualHeight() - Card.Height) / 2);
	private final static Vector toSelectDestination = new Vector(Game.getVirtualWidth(), (Game.getVirtualHeight() - Card.Height) / 2);

	private final static List<List<Vector>>inPositions;
	private final static List<Vector> outPositions = new ArrayList<Vector>();
	
	static {
		inPositions = new ArrayList<List<Vector>>();
		List<Vector> inStart = new ArrayList<Vector>();
		inStart.add(selectedStart);
		inStart.add(selectedStart);
		inStart.add(toSelectStart);
		inStart.add(toSelectStart);
		inStart.add(toSelectStart);

		List<Vector> inDestination = new ArrayList<Vector>();
		inDestination.add(R.singleGameMode.cardOnTable1Pos);
		inDestination.add(R.singleGameMode.cardOnTable2Pos);
		inDestination.add(R.singleGameMode.cardToSelect1Pos);
		inDestination.add(R.singleGameMode.cardToSelect2Pos);
		inDestination.add(R.singleGameMode.cardToSelect3Pos);
		
		inPositions.add(inStart);
		inPositions.add(inDestination);
		
		outPositions.add(new Vector(selectedDestination));
		outPositions.add(new Vector(selectedDestination));
		outPositions.add(new Vector(toSelectDestination));
		outPositions.add(new Vector(toSelectDestination));
		outPositions.add(new Vector(toSelectDestination));
	}
	
	public SingleGameDealer(Table table) {
		super(table);
	}

	@Override
	protected void selectDeal() {
		dealConsecutive();
	}

	@Override
	protected float getDealOutInterval() {
		return 0;
	}
	
	protected void setOutEffects() {
		MoveEffect moveEffect;
		for (int i = 0; i < cardsDealingOut.size(); i++) {
			moveEffect = new MoveEffect(cardsDealingOut.get(i));
			moveEffect.setLooping(false);
			moveEffect.setDestination(outPositions.get(i));
			moveEffect.setDuration(outDuration);
			cardsDealingOut.get(i).setDealerEffect(moveEffect);
		}
	}

	protected void setInEffects() {
		for (int i = 0; i < cardsDealingIn.size(); i++)
			cardsDealingIn.get(i).getLocation().set(inPositions.get(0).get(i));
		MoveEffect moveEffect;
		for (int i = 0; i < cardsDealingIn.size(); i++) {
			moveEffect = new MoveEffect(cardsDealingIn.get(i));
			moveEffect.setLooping(false);
			moveEffect.setDestination(inPositions.get(1).get(i));
			moveEffect.setDuration(inDuration);
			cardsDealingIn.get(i).setDealerEffect(moveEffect);
		}
	}
	
	@Override
	protected void concreteDrawCards() {
		for(Card card : cardsDealingIn)
			if (card != null)
				card.draw();
		for(Card card : cardsDealingOut)
			card.draw();
	}
}
