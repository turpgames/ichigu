package com.blox.ichigu.model;

import com.blox.framework.v0.impl.TexturedGameObject;
import com.blox.framework.v0.util.Color;

public class TutorialSymbol extends TexturedGameObject {
	public TutorialSymbol(String textureId, Color color) {
		super(textureId);
		setWidth(Card.SymbolWidth);
		setHeight(Card.SymbolHeight);		
		getColor().set(color);
	}
}
