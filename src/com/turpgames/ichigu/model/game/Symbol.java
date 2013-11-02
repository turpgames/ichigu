package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.utils.R;

class Symbol extends GameObject {
	private Card parent;
	private ITexture texture;

	public Symbol(ITexture texture, int colorAttribute, Vector location, Card parent) {
		this.texture = texture;
		this.parent = parent;

		setWidth(Card.SymbolWidth);
		setHeight(Card.SymbolHeight);

		super.getLocation().set(location);

		if (colorAttribute == CardAttributes.Color_Red)
			getColor().set(R.colors.ichiguRed);
		if (colorAttribute == CardAttributes.Color_Green)
			getColor().set(R.colors.ichiguGreen);
		if (colorAttribute == CardAttributes.Color_Blue)
			getColor().set(R.colors.ichiguBlue);
	}

	@Override
	public void draw() {
		TextureDrawer.draw(texture, this);
	}

	@Override
	public Vector getLocation() {		
		return parent.getLocation().tmp().add(super.getLocation());
	}

	@Override
	public Vector getScale() {
		return parent.getScale();
	}

	@Override
	public Rotation getRotation() {
		return parent.getRotation();
	}

	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Utils.LAYER_GAME);
	}
}
