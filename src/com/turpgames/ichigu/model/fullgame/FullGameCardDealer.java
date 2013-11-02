package com.turpgames.ichigu.model.fullgame;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardDealer;

public class FullGameCardDealer extends CardDealer {
	// region static

	private final static int cols = 5;
	private final static float moveDuration = 0.5f;

	private final static Map<Integer, Vector> cardLocations = new HashMap<Integer, Vector>();

	static {
		float dy = (Game.getVirtualHeight() - Game.getVirtualWidth()) / 2f - 20;
		for (int i = 0; i < FullGameCards.TotalCardsOnTable; i++) {
			int x = i < FullGameCards.ActiveCardCount ? i % (cols - 1) : cols - 1;
			int y = i < FullGameCards.ActiveCardCount ? i / (cols - 1) : i - FullGameCards.ActiveCardCount;

			int dx = i < FullGameCards.ActiveCardCount ? 0 : Card.Space + 1;

			cardLocations.put(i, new Vector(
					x * Card.Width + Card.Space * (x + 1) + dx,
					y * Card.Height + Card.Space * (y + 1) + dy));
		}
	}

	// endregion

	private final FullGameCards cards;
	private int index;

	private int cardsToFadeOut;
	private int cardsToMove;

	FullGameCardDealer(FullGameCards cards) {
		this.cards = cards;
	}

	@Override
	public void deal() {
		if (index == 0) {
			initCards();
			notifyDealEnd();
		}
		else {
			removeIchiguCards();
		}
	}

	public int getIndex() {
		return index;
	}

	public void reset() {
		super.reset();
		index = 0;
	}

	private void initCards() {
		for (int i = 0; i < FullGameCards.TotalCardsOnTable; i++) {
			cards.setCard(i, deck[i]);
			deck[i].getLocation().set(cardLocations.get(i));
			if (i < FullGameCards.ActiveCardCount)
				deck[i].open();
			else
				deck[i].close();
		}

		index = FullGameCards.TotalCardsOnTable;
	}

	private void removeIchiguCards() {
		cardsToFadeOut = FullGameCards.IchiguCardCount;
		for (int i = 0; i < FullGameCards.IchiguCardCount; i++)
			cards.getSelectedCard(i).fadeOut(fadeOutListener);
	}

	private void onFadeOutComplete() {
		startMovingExtraCards();
		if (cardsToMove == 0)
			onCardsMoveEnd();
	}

	private void startMovingExtraCards() {
		cardsToMove = 0;
		int extraCardIndex = 0;
		for (int i = FullGameCards.ActiveCardCount - 1; i >= 0; i--) {
			if (cards.isActiveCardEmpty(i) || !cards.getActiveCard(i).isSelected())
				continue;

			extraCardIndex = findNextAvailableExtraCardIndex(extraCardIndex);
			if (extraCardIndex < 0)
				break;

			startMovingExtraCard(extraCardIndex, i);

			extraCardIndex++;
			cardsToMove++;
		}
	}

	private void startMovingExtraCard(int extraCardIndex, int targetActiveCardIndex) {
		Card extraCard = cards.getExtraCard(extraCardIndex);
		extraCard.open();
		extraCard.moveTo(moveEndListener, cards.getActiveCard(targetActiveCardIndex).getLocation(), moveDuration);		
	}

	private void onCardsMoveEnd() {
		cards.emptySelectedCards();
		dealExtraCards();
		notifyDealEnd();
	}

	private int findNextAvailableExtraCardIndex(int searchFrom) {
		for (int i = searchFrom; i < FullGameCards.ExtraCardCount; i++) {
			if (!cards.isExtraCardEmpty(i) && !cards.getExtraCard(i).isSelected())
				return i;
		}
		return -1;
	}

	void dealExtraCards() {
		for (int i = 0; i < FullGameCards.IchiguCardCount && index < deck.length; i++) {
			Card card = deck[index++];
			card.getLocation().set(cardLocations.get(i + FullGameCards.ActiveCardCount));
			card.close();
			cards.setExtraCard(i, card);
		}
	}

	private void onCardFadeOutComplete() {
		cardsToFadeOut--;
		if (cardsToFadeOut == 0)
			onFadeOutComplete();
	}

	private void onCardMoveEnd(Card card) {
		for (int i = 0; i < FullGameCards.ActiveCardCount; i++) {
			if (!cards.isActiveCardEmpty(i) && cards.getActiveCard(i).isSelected()) {
				cards.setActiveCard(i, card);
				break;
			}
		}

		for (int i = 0; i < FullGameCards.ExtraCardCount; i++) {
			if (card == cards.getExtraCard(i)) {
				cards.setExtraCard(i, null);
				break;
			}
		}

		if (--cardsToMove == 0)
			onCardsMoveEnd();
	}

	private final IEffectEndListener fadeOutListener = new IEffectEndListener() {
		@Override
		public boolean onEffectEnd(Object obj) {
			onCardFadeOutComplete();
			return true;
		}
	};

	private final IEffectEndListener moveEndListener = new IEffectEndListener() {
		@Override
		public boolean onEffectEnd(Object obj) {
			onCardMoveEnd((Card) obj);
			return true;
		}
	};
}
