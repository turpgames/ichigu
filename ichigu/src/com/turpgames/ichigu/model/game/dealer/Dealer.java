package com.turpgames.ichigu.model.game.dealer;

import java.util.List;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.Table;

public abstract class Dealer {	
	protected Table table;
	
	public Dealer(Table table) {
		this.table = table;
	}

	private IEffectEndListener outListener;
	private IEffectEndListener inListener;
	
	protected List<Card> cardsDealingIn;
	protected List<Card> cardsDealingOut;
	
	private int effectsToFinish;
	
	public void deal(List<Card> cardsToDealIn, List<Card> cardsToDealOut) {
		this.cardsDealingIn = cardsToDealIn;
		this.cardsDealingOut = cardsToDealOut;
		setOutEffects();
		setInEffects();
		this.effectsToFinish = 0;
		for(Card card : cardsToDealIn)
			if(card != null && card.hasDealerEffect())
				this.effectsToFinish++;
		for(Card card : cardsToDealOut)
			if(card.hasDealerEffect())
				this.effectsToFinish++;
		if (effectsToFinish == 0) {
			table.onDealEnded(cardsDealingIn, cardsDealingOut);
		}
		else {
			selectDeal();
		}
	}
	
	abstract protected void selectDeal();
	
	protected void dealSimultaneous() {
		inListener = new IEffectEndListener() {
			@Override
			public boolean onEffectEnd(Object obj) {
				finishEffect();
				return false;
			}
		};
		
		outListener = new IEffectEndListener() {
			
			@Override
			public boolean onEffectEnd(Object obj) {
				finishEffect();
				return false;
			}
		};

		dealOut();
		dealIn();
	}
	
	private synchronized void finishEffect() {
		effectsToFinish--;
		if (effectsToFinish == 0) {
			for(Card card : cardsDealingIn)
				if (card != null)
					card.resetDealerEffect();
			for(Card card : cardsDealingOut)
				card.resetDealerEffect();
			table.onDealEnded(cardsDealingIn, cardsDealingOut);
		}
	}
	
	protected void dealConsecutive() {
		inListener = new IEffectEndListener() {
			
			@Override
			public boolean onEffectEnd(Object obj) {
				finishEffect();
				return false;
			}
		};
		
		outListener = new IEffectEndListener() {
			
			@Override
			public boolean onEffectEnd(Object obj) {
				finishEffect();
				if (effectsToFinish == cardsDealingIn.size())
					dealIn();
				return false;
			}
		};

		dealOut();
		if (cardsDealingOut.size() == 0)
			dealIn();
	}

	private final void dealOut() {
		dealCards(cardsDealingOut, outListener);
	}

	private final void dealIn() {
		dealCards(cardsDealingIn, inListener);
	}
	
	abstract protected void setOutEffects();
	
	abstract protected void setInEffects();
	
	private void dealCards(List<Card> cardsToDeal, IEffectEndListener listener) {
		for (int i = 0; i < cardsToDeal.size(); i++) {
			if (cardsToDeal.get(i) == null)
				continue;
			cardsToDeal.get(i).startDealerEffect(listener);
		}
	}

	public boolean isWorking() {
		return effectsToFinish != 0;
	}

	public void drawCards() {
		if (!isWorking())
			return;
		for(Card card : cardsDealingIn)
			if (card != null)
				card.draw();
		for(Card card : cardsDealingOut)
			card.draw();
	}
}
