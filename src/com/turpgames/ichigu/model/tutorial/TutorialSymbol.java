package com.turpgames.ichigu.model.tutorial;

import com.turpgames.framework.v0.impl.TexturedGameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.ichigu.model.Card;

public class TutorialSymbol extends TexturedGameObject {
	public TutorialSymbol(String textureId, Color color) {
		super(textureId);
		setWidth(Card.SymbolWidth);
		setHeight(Card.SymbolHeight);		
		getColor().set(color);
	}
}
