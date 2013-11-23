package com.turpgames.ichigu.model.fullgame;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
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

	private boolean isInfiniteDeal;
	private final Card[] cardsToPutBack = new Card[3];

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

	public void setAsInfiniteDeal() {
		isInfiniteDeal = true;
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
		for (int i = 0; i < FullGameCards.IchiguCardCount; i++) {
			if (isInfiniteDeal)
				cardsToPutBack[i] = cards.getSelectedCard(i);
			cards.getSelectedCard(i).fadeOut(fadeOutListener);
		}
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

	@Override
	public void abortDeal() {

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
		int x = 0;
		for (int i = 0; i < FullGameCards.IchiguCardCount && index < deck.length; i++) {
			Card card = deck[index++];
			card.getColor().a = 1;
			card.deselect();
			card.getLocation().set(cardLocations.get(i + FullGameCards.ActiveCardCount));
			card.close();

			// extra cardlar açýlmasýna raðmen hala no icihgu olmasý durumunda desteye geri koyulacak kartlar eski extra cardlardýr
			if (isInfiniteDeal && cardsToPutBack[x] == null)
				cardsToPutBack[x] = cards.getExtraCard(i);
			
			cards.setExtraCard(i, card);
		}

		if (isInfiniteDeal)
			putFoundIchiguBackIntoDeck();
	}

	private void putFoundIchiguBackIntoDeck() {
		for (int i = 0; i < FullGameCards.TotalCardsOnTable; i++)
			deck[i] = cards.getCard(i);

		for (int i = FullGameCards.TotalCardsOnTable; i < deck.length - 3; i++)
			deck[i] = deck[i + 3];

		for (int i = 0; i < cardsToPutBack.length; i++)
			deck[deck.length - i - 1] = cardsToPutBack[i];

		index = FullGameCards.TotalCardsOnTable;

		Utils.shuffle(deck, index, deck.length, 3);
		
		for (int i = 0; i < cardsToPutBack.length; i++)
			cardsToPutBack[i] = null;
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
