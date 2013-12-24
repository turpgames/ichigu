package com.turpgames.ichigu.model.game.dealer;

import java.util.List;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.framework.v0.util.Timer.ITimerTickListener;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.Table;

public abstract class Dealer {
	class DealListener implements ITimerTickListener {

		private List<Card> cards;
		private IEffectEndListener listener;
		private int i;
		public DealListener(List<Card> cards, IEffectEndListener listener){
			this.cards = cards;
			this.listener = listener;
			this.i = 0;
		}
		
		@Override
		public void timerTick(Timer timer) {
			if (i >= cards.size())
				return;
			cards.get(i).startDealerEffect(listener);
			i++;
		}
	}
	
	protected Table table;
	private IEffectEndListener outListener;
	private IEffectEndListener inListener;
	
	protected List<Card> cardsDealingIn;
	protected List<Card> cardsDealingOut;

	protected Timer dealInTimer;
	protected Timer dealOutTimer;

	private DealListener inTickListener;
	private DealListener outTickListener;
	
	private int effectsToFinish;
	
	public Dealer(Table table) {
		this.table = table;
		this.dealInTimer = new Timer();
		this.dealOutTimer = new Timer();
	}

	public final void deal(List<Card> cardsToDealIn, List<Card> cardsToDealOut) {
		for(Card card : cardsToDealIn)
			card.resetDealerEffect();
		for(Card card : cardsToDealOut)
			card.resetDealerEffect();

		this.cardsDealingIn = cardsToDealIn;
		this.cardsDealingOut = cardsToDealOut;
		
		setOutEffects();
		setInEffects();
		
		this.effectsToFinish = cardsToDealIn.size() + cardsDealingOut.size();
		selectDeal();
	}
	
	public void draw() {
		if (!isWorking())
			return;
		concreteDrawCards();
	}
	
	protected final static void drawCards(List<Card> cards) {
		for (Card card : cards)
			card.draw();
	}

	public boolean isWorking() {
		return effectsToFinish != 0;
	}

	private final void dealIn() {
		inTickListener = new DealListener(cardsDealingIn, inListener);
		dealInTimer.setInterval(getDealInInterval());
		dealInTimer.setTickListener(inTickListener);
		dealInTimer.start();
	}
	
	private final void dealOut() {
		outTickListener = new DealListener(cardsDealingOut, outListener);
		dealOutTimer.setInterval(getDealOutInterval());
		dealOutTimer.setTickListener(outTickListener);
		dealOutTimer.start();
	}
	
	private synchronized void finishEffect() {
		effectsToFinish--;
		if (effectsToFinish == 0) {
			for(Card card : cardsDealingIn)
				card.resetDealerEffect();
			for(Card card : cardsDealingOut)
				card.resetDealerEffect();
			table.onDealEnded();
			dealInTimer.stop();
			dealOutTimer.stop();
		}
	}
	
	abstract protected void concreteDrawCards();
	
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

	protected float getDealInInterval() {
		return 0.12f;
	}

	protected float getDealOutInterval() {
		return 0.12f;
	}

	abstract protected void selectDeal();

	abstract protected void setInEffects();
	
	abstract protected void setOutEffects();
}
