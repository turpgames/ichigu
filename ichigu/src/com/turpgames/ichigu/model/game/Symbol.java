package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.utils.R;

class Symbol extends GameObject {
	private Card parent;
	private ITexture texture;

	public Symbol(ITexture texture, int colorAttribute, Vector location, Card parent) {
		this.texture = texture;
		this.parent = parent;

		setWidth(R.sizes.symbolWidth);
		setHeight(R.sizes.symbolHeight);

		super.getLocation().set(location);

		if (colorAttribute == R.cardAttributes.colorRed)
			getColor().set(R.colors.ichiguRed);
		if (colorAttribute == R.cardAttributes.colorGreen)
			getColor().set(R.colors.ichiguGreen);
		if (colorAttribute == R.cardAttributes.colorBlue)
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
	public Rotation getRotation() {
		return parent.getRotation();
	}

	@Override
	public Vector getScale() {
		return parent.getScale();
	}

	@Override
	public void registerSelf() {
		
	}
}
