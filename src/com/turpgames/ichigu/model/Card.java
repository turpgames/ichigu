package com.turpgames.ichigu.model;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.utils.IchiguResources;
import com.turpgames.ichigu.utils.R;

public class Card extends IchiguObject {

	// region static

	public static final int Width = 100;
	public static final int Height = 174;
	public static final int Space = 7;
	public static final int SymbolHeight = 45;
	public static final int SymbolWidth = 45;

	public static final int CardsInDeck = 81;

	private static void createDeck(Card[] deck) {
		int[] colors = new int[] { CardAttributes.Color_Red, CardAttributes.Color_Green, CardAttributes.Color_Blue };
		int[] shapes = new int[] { CardAttributes.Shape_Circle, CardAttributes.Shape_Square, CardAttributes.Shape_Triangle };
		int[] counts = new int[] { CardAttributes.Count_1, CardAttributes.Count_2, CardAttributes.Count_3 };
		int[] patterns = new int[] { CardAttributes.Pattern_Empty, CardAttributes.Pattern_Filled, CardAttributes.Pattern_Striped };

		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < shapes.length; j++) {
				for (int k = 0; k < counts.length; k++) {
					for (int n = 0; n < patterns.length; n++) {
						Card card = new Card(new CardAttributes(colors[i], shapes[j], counts[k], patterns[n]));
						deck[i + j * 3 + k * 9 + n * 27] = card;
					}
				}
			}
		}
	}

	public static Card[] newDeck() {
		Card[] deck = new Card[81];
		createDeck(deck);
		Utils.shuffle(deck);
		return deck;
	}

	public static int getIchiguScore(Card card1, Card card2, Card card3) {
		return CardAttributes.getIchiguScore(card1.attributes, card2.attributes, card3.attributes);
	}

	public static boolean isIchigu(Card card1, Card card2, Card card3) {
		return CardAttributes.isIchigu(card1.attributes, card2.attributes, card3.attributes);
	}

	// endregion

	private boolean isOpened;
	private boolean isSelected;

	private List<Symbol> symbols;
	private CardAttributes attributes;
	private ICardListener eventListener;

	Card(CardAttributes cardAttributes) {
		this.attributes = cardAttributes;
		setWidth(Card.Width);
		setHeight(Card.Height);

		initSymbols();
	}

	private void initSymbols() {
		String symbolName = "card-" + attributes.getShape() + attributes.getPattern();

		ITexture symbolTexture = Game.getResourceManager().getTexture(symbolName);
		symbols = new ArrayList<Symbol>();

		if (attributes.getCount() == 1) {
			symbols.add(new Symbol(symbolTexture, attributes.getColor(), R.symbolpositions.firstOfOne, this));
		}
		else if (attributes.getCount() == 2) {
			symbols.add(new Symbol(symbolTexture, attributes.getColor(), R.symbolpositions.firstOfTwo, this));
			symbols.add(new Symbol(symbolTexture, attributes.getColor(), R.symbolpositions.secondOfTwo, this));
		}
		else if (attributes.getCount() == 4) {
			symbols.add(new Symbol(symbolTexture, attributes.getColor(), R.symbolpositions.firstOfThree, this));
			symbols.add(new Symbol(symbolTexture, attributes.getColor(), R.symbolpositions.secondOfThree, this));
			symbols.add(new Symbol(symbolTexture, attributes.getColor(), R.symbolpositions.thirdOfThree, this));
		}
	}

	private void switchSelected() {
		isSelected = !isSelected;
	}

	public void activate(ICardListener listener) {
		listenInput(true);
		eventListener = listener;
	}

	public void deactivate() {
		listenInput(false);
		eventListener = null;
	}

	public boolean isOpened() {
		return isOpened;
	}

	public void open() {
		isOpened = true;
	}

	public void close() {
		isOpened = false;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void deselect() {
		isSelected = false;
	}

	public CardAttributes getAttributes() {
		return attributes;
	}

	@Override
	public void draw() {
		if (!isOpened) {
			IchiguResources.drawTextureCardClosed(this);
			return;
		}

		IchiguResources.drawTextureCardEmpty(this);
		for (int i = 0; i < symbols.size(); i++) {
			symbols.get(i).getColor().a = getColor().a;
			symbols.get(i).draw();
		}

		if (isSelected)
			IchiguResources.drawTextureCardBorder(this);
	}

	@Override
	public Rotation getRotation() {
		super.getRotation().origin.set((getLocation().x + Card.Width) / 2, (getLocation().y + Card.Height) / 2);
		return super.getRotation();
	}

	@Override
	protected boolean onTap() {
		switchSelected();
		notifyTapped();
		return true;
	}

	private void notifyTapped() {
		if (eventListener != null)
			eventListener.onCardTapped(this);
	}

	public static Card createTutorialCard(int color, int shape, int count, int pattern, float x, float y) {
		Card card = new Card(new CardAttributes(color, shape, count, pattern));
		card.open();
		card.getLocation().set(x, y);
		return card;
	}
}
