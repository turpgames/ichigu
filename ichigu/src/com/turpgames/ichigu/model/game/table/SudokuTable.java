package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.model.game.SudokuDeck;
import com.turpgames.ichigu.model.game.dealer.SudokuGameDealer;
import com.turpgames.ichigu.utils.R;

public class SudokuTable extends Table {
	class IchiguMark extends GameObject {
		private ITexture correct;
		private ITexture incorrect;
		private boolean isActive;
		
		public IchiguMark(Vector location) {
			getLocation().set(location);
			this.correct = Game.getResourceManager().getTexture(R.game.textures.singlegame.correctmark);
			this.incorrect = Game.getResourceManager().getTexture(R.game.textures.singlegame.incorrectmark);
			setWidth(R.ui.imageButtonWidth);
			setHeight(R.ui.imageButtonHeight);
			deactivate();
		}
		
		public void activate() {
			isActive = true;
			getColor().set(R.colors.ichiguGreen);
		}
		
		public void deactivate() {
			isActive = false;
			getColor().set(R.colors.ichiguRed);
		}
		
		@Override
		public void draw() {
			if (isActive)
				TextureDrawer.draw(correct, this);
			else
				TextureDrawer.draw(incorrect, this);
		}

		@Override
		public void registerSelf() { }
	}
	private static final List<Vector> markPositions = new ArrayList<Vector>();
	
	static {
		float dy = (Game.getVirtualHeight() - Game.getVirtualWidth()) / 2f - 20;
		float dx = (Game.getVirtualWidth() - 3 * Card.Width) / 2;
		dy += 3 * Card.Height;
		
		for (int i = 0; i < 3; i++) {
			int x = i % 3;
			markPositions.add(new Vector(
					x * Card.Width + Card.Space * (x + 1) + dx + (Card.Width - R.ui.imageButtonWidth) / 2,
					dy + (Card.Height - R.ui.imageButtonHeight) / 2 - 10));
		}

		dy -= 3 * Card.Height;
		dx -= Card.Width;
		
		for (int i = 0; i < 3; i++) {
			int y = i;

			markPositions.add(new Vector(
					dx + (Card.Width - R.ui.imageButtonWidth) / 2,
					y * Card.Height + Card.Space * (y + 1) + dy + (Card.Height - R.ui.imageButtonHeight) / 2));
		}
	}
	
	private SudokuDeck deck;
	private List<IchiguMark> marks;
	public SudokuTable() {
		deck = new SudokuDeck(this);
		marks = new ArrayList<IchiguMark>();
		for (int i = 0; i < 6; i++) {
			marks.add(new IchiguMark(markPositions.get(i)));
		}
	}
	@Override
	public void draw() {
		for (Card card : cardsOnTable) {
			if (!(dealer.isWorking() && selectedCards.contains(card)))
				card.draw();
		}
		for (IchiguMark mark : marks)
			mark.draw();
		dealer.drawCards();
	}

	@Override
	public void end() {
		deck.end();
		selectedCards.clear();
		cardsOnTable.clear();
	}
	
	@Override
	public ISudokuTableListener getListener() {
		return (ISudokuTableListener) listener;
	}

	@Override
	public void openCloseCards(boolean open) {
		for (Card card : cardsOnTable) {
			card.open(open);
		}
	}

	@Override
	public void reset() {
		deck.reset();
		selectedCards.clear();
		cardsOnTable.clear();
	}
	
	@Override
	public void start() {
		deck.start();
	}
	
	public void swapEnded() {
		swapSelectedInList();
		if (checkForIchigus())
			getListener().onTableFinished();
		selectedCards.clear();
		getListener().onSwapEnded();
	}
	
	private boolean checkForIchigus() {
		boolean returning = true;
		for (int i = 0; i < 9; i+= 3) {
			if (!CardAttributes.isIchigu(cardsOnTable.get(i).getAttributes(), cardsOnTable.get(i+1).getAttributes(), cardsOnTable.get(i+2).getAttributes())) {
				marks.get(i/3+3).deactivate();
				returning = false;
			}
			else
				marks.get(i/3+3).activate();
		}
		for (int j = 0; j < 3; j++) {
			if (!CardAttributes.isIchigu(cardsOnTable.get(j).getAttributes(), cardsOnTable.get(j+3).getAttributes(), cardsOnTable.get(j+6).getAttributes())) {
				marks.get(j).deactivate();
				returning = false;
			}
			else
				marks.get(j).activate();
		}
		return returning;
	}

	private void swapSelectedInList() {
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
			selectedCards.get(0).deselect();
			selectedCards.get(1).deselect();
			getListener().onSwapStarted();
			((SudokuGameDealer)dealer).swap(selectedCards);
		}
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
		checkForIchigus();
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
	protected void setDealer() {
		dealer = new SudokuGameDealer(this);
	}
}
