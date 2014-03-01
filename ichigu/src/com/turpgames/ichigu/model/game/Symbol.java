package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Rotation;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

class Symbol extends GameObject {
	private Card parent;

	public Symbol(Vector location, Card parent) {
		this.parent = parent;

		setWidth(R.sizes.symbolWidth);
		setHeight(R.sizes.symbolHeight);

		super.getLocation().set(location);

		if (parent.getAttributes().getColor() == CardAttributes.colorRed)
			getColor().set(R.colors.ichiguRed);
		if (parent.getAttributes().getColor() == CardAttributes.colorGreen)
			getColor().set(R.colors.ichiguGreen);
		if (parent.getAttributes().getColor() == CardAttributes.colorBlue)
			getColor().set(R.colors.ichiguBlue);
	}

	@Override
	public void draw() {
		Ichigu.drawSymbol(parent.getAttributes().getShape(), parent.getAttributes().getPattern(), this);
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
}
