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
			setWidth(R.sizes.menuButtonSize);
			setHeight(R.sizes.menuButtonSize);
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
		public void registerSelf() {
		}
	}

	private static final List<Vector> markPositions = new ArrayList<Vector>();

	static {
		float dx = (Game.getVirtualWidth() - (3 * R.sizes.cardWidth + 2 * 4 * R.sizes.cardSpace)) / 2f;
		float dy = (Game.getVirtualHeight() - (3 * R.sizes.cardHeight + 2 * 4 * R.sizes.cardSpace)) / 2f;

		float halfButtonSize = R.sizes.menuButtonSize / 2;
		
		float margin = 10f;

		for (int i = 0; i < 3; i++) {
			markPositions.add(new Vector(
					dx + ((i + 0.5f) * R.sizes.cardWidth) + i * (4 * R.sizes.cardSpace) - halfButtonSize,
					Game.getVirtualHeight() - dy + margin));
		}

		for (int i = 0; i < 3; i++) {
			markPositions.add(new Vector(
					dx - R.sizes.menuButtonSize - margin,
					dy + ((i + 0.5f) * R.sizes.cardHeight) + i * (4 * R.sizes.cardSpace) - halfButtonSize));
		}
	}

	private SudokuDeck deck;
	private List<IchiguMark> marks;
	private boolean marksVisible;

	private List<Card> toDealOut;
	private List<Card> toDealIn;
	public SudokuTable() {
		deck = new SudokuDeck(this);
		marks = new ArrayList<IchiguMark>();
		for (int i = 0; i < 6; i++) {
			marks.add(new IchiguMark(markPositions.get(i)));
		}
		
		toDealOut = new ArrayList<Card>();
		toDealIn = new ArrayList<Card>();
	}

	@Override
	public void draw() {
		for (Card card : cardsOnTable) {
			if (!(dealer.isWorking() && selectedCards.contains(card)))
				card.draw();
		}
		if (marksVisible)
			for (IchiguMark mark : marks)
				mark.draw();
		dealer.draw();
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
		marksVisible = false;
	}

	@Override
	public void start() {
		deck.start();
		marksVisible = false;
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
		for (int i = 0; i < 9; i += 3) {
			if (!CardAttributes.isIchigu(cardsOnTable.get(i).getAttributes(), cardsOnTable.get(i + 1).getAttributes(), cardsOnTable.get(i + 2).getAttributes())) {
				marks.get(i / 3 + 3).deactivate();
				returning = false;
			}
			else
				marks.get(i / 3 + 3).activate();
		}
		for (int j = 0; j < 3; j++) {
			if (!CardAttributes.isIchigu(cardsOnTable.get(j).getAttributes(), cardsOnTable.get(j + 3).getAttributes(), cardsOnTable.get(j + 6).getAttributes())) {
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
			((SudokuGameDealer) dealer).swap(selectedCards);
		}
	}

	@Override
	protected void concreteDealEnded() {
		for (Card card : toDealOut) {
			cardsOnTable.remove(card);
			card.deactivate();
		}
		for (Card card : toDealIn) {
			cardsOnTable.add(card);
			card.open(true);
			card.activate();
		}
		checkForIchigus();
		marksVisible = true;
	}

	@Override
	protected List<Card> getCardsToDealIn() {
		toDealIn.clear();
		toDealIn.addAll(deck.get9Cards());
		Utils.shuffle(toDealIn);
		return toDealIn;
	}

	@Override
	protected List<Card> getCardsToDealOut() {
		toDealOut.clear();
		for (Card card : cardsOnTable)
			toDealOut.add(card);
		return toDealOut;
	}

	@Override
	protected void setDealer() {
		dealer = new SudokuGameDealer(this);
	}
}
