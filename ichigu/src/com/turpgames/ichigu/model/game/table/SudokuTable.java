package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.util.Utils;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.model.game.SudokuDeck;
import com.turpgames.ichigu.model.game.dealer.SudokuGameDealer;

public class SudokuTable extends Table {

	private SudokuDeck deck;
	public SudokuTable() {
		deck = new SudokuDeck(this);
	}
	@Override
	protected void setDealer() {
		dealer = new SudokuGameDealer(this);
	}

	@Override
	protected void concreteDealEnded(List<Card> dealtIn, List<Card> dealtOut) {
		for (Card card : dealtOut) {
			cardsOnTable.remove(card);
			card.deactivate();
		}
		for (Card card : dealtIn) {
			cardsOnTable.add(card);
			card.open(true);
			card.activate();
		}
	}

	@Override
	protected void concreteCardTapped(Card card) {
		if (selectedCards.contains(card)) {
			selectedCards.remove(card);
			card.deselect();
		}
		else {
			selectedCards.add(card);
			card.select();
		}
		
		if (selectedCards.size() == 2)
		{
			Vector v = new Vector();
			v.set(selectedCards.get(0).getLocation());
			selectedCards.get(0).getLocation().set(selectedCards.get(1).getLocation());
			selectedCards.get(1).getLocation().set(v);
			
			selectedCards.get(0).deselect();
			selectedCards.get(1).deselect();
			swapSelected();
			selectedCards.clear();
			
			if (checkForIchigus())
				getListener().onTableFinished();
		}
	}
	
	private void swapSelected() {
		for (int i = 0; i < 9; i++) {
			if (cardsOnTable.get(i) == selectedCards.get(0)) {
				for (int j = 0; j < 9; j++) {
					if (cardsOnTable.get(j) == selectedCards.get(1)) {
						Utils.swap(cardsOnTable, i, j);
						return;
					}
				}
			}
		}
	}
	
	private boolean checkForIchigus() {
		for (int i = 0; i < 9; i+= 3) {
			if (!CardAttributes.isIchigu(cardsOnTable.get(i).getAttributes(), cardsOnTable.get(i+1).getAttributes(), cardsOnTable.get(i+2).getAttributes()))
				return false;
		}
		for (int j = 0; j < 3; j++) {
			if (!CardAttributes.isIchigu(cardsOnTable.get(j).getAttributes(), cardsOnTable.get(j+3).getAttributes(), cardsOnTable.get(j+6).getAttributes()))
				return false;
		}
		return true;
	}
	
	@Override
	public void openCloseCards(boolean open) {
		for (Card card : cardsOnTable) {
			card.open(open);
		}
	}

	@Override
	protected List<Card> getCardsToDealIn() {
		List<Card> toDealIn = deck.get9Cards();
		Utils.shuffle(toDealIn);
		return toDealIn;
	}

	@Override
	protected List<Card> getCardsToDealOut() {
		List<Card> toDealOut = new ArrayList<Card>();
		for(Card card : cardsOnTable)
			toDealOut.add(card);
		return toDealOut;
	}

	@Override
	public void start() {
		deck.start();
	}

	@Override
	public void end() {
		deck.end();
		selectedCards.clear();
		cardsOnTable.clear();
	}

	@Override
	public void reset() {
		deck.reset();
		selectedCards.clear();
		cardsOnTable.clear();
	}
}
