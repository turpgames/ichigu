package com.turpgames.ichigu.model.game;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.effects.Effect;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class Card extends IchiguObject {

	// region static

	public static final int Width = 100;
	public static final int Height = 174;
	public static final int Space = 7;
	public static final int SymbolHeight = 45;
	public static final int SymbolWidth = 45;

	public static final int CardsInDeck = 81;

	public static Card createTutorialCard(int color, int shape, int count, int pattern, float x, float y) {
		Card card = new Card(new CardAttributes(color, shape, count, pattern), null);
		card.open(true);
		card.getLocation().set(x, y);
		return card;
	}

	public static int getIchiguCount(List<Card>cards) {
		int count = 0;
		for (int i = 0; i < cards.size(); i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				for (int k = j + 1; k < cards.size(); k++) {
					if (isIchigu(cards.get(i), cards.get(j), cards.get(k))) {
						count++;
					}
				}
			}
		}
		return count;
	}

	public static int getIchiguScore(Card card1, Card card2, Card card3) {
		return CardAttributes.getIchiguScore(card1.attributes, card2.attributes, card3.attributes);
	}
	
	// endregion

	public static boolean isIchigu(Card card1, Card card2, Card card3) {
		return CardAttributes.isIchigu(card1.attributes, card2.attributes, card3.attributes);
	}
	private boolean isOpened;

	private boolean isSelected;
	private List<Symbol> symbols;
	private CardAttributes attributes;

	private ICardListener eventListener;
	
	private Effect dealerEffect;

	public Card(CardAttributes cardAttributes, ICardListener eventListener) {
		this.attributes = cardAttributes;
		this.eventListener = eventListener;
		
		setWidth(Card.Width);
		setHeight(Card.Height);

		initSymbols();
	}

	public void activate() {
		listenInput(true);
	}

	public void deactivate() {
		listenInput(false);
	}

	public void deselect() {
		isSelected = false;
	}

	@Override
	public void draw() {
		if (!isOpened) {
			Ichigu.drawTextureCardClosed(this);
			return;
		}

		Ichigu.drawTextureCardEmpty(this);
		for (int i = 0; i < symbols.size(); i++) {
			symbols.get(i).getColor().a = getColor().a;
			symbols.get(i).draw();
		}

		if (isSelected)
			Ichigu.drawTextureCardBorder(this);
	}

	public CardAttributes getAttributes() {
		return attributes;
	}
	
	@Override
	public Rotation getRotation() {
		super.getRotation().origin.set((getLocation().x + Card.Width) / 2, (getLocation().y + Card.Height) / 2);
		return super.getRotation();
	}
	
	public boolean hasDealerEffect() {
		return dealerEffect != null;
	}

	public boolean isOpened() {
		return isOpened;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void open(boolean open) {
		isOpened = open;
	}

	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Utils.LAYER_GAME);
	}

	public void reset() {
		deactivate();
		deselect();
		getColor().set(Color.white());
	}

	public void resetDealerEffect() {
		dealerEffect = null;
	}

	public void select() {
		isSelected = true;
	}

	public void setDealerEffect(Effect effect) {
		dealerEffect = effect;
	}
	
	public void startDealerEffect(IEffectEndListener listener) {
		if (!hasDealerEffect()) {
			listener.onEffectEnd(null);
			return;
		}
		dealerEffect.setListener(listener);
		dealerEffect.start(listener);
	}

	@Override
	public String toString() {
		return getAttributes().toString();
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

	private void notifyTapped() {
		if (eventListener != null)
			eventListener.onCardTapped(this);
	}
	
	@Override
	protected boolean onTap() {
		notifyTapped();
		return true;
	}
}
