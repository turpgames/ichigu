package com.blox.ichigu.model;

import com.blox.framework.v0.ITexture;
import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.TextureDrawer;
import com.blox.ichigu.utils.R;

public class IchiguImageButton extends IchiguButton {	
	private ITexture texture;
	
	IchiguImageButton() {
		super();
		setWidth(R.ui.imageButtonSize);
		setHeight(R.ui.imageButtonSize);
	}
	
	void setTexture(String id) {
		texture = Game.getResourceManager().getTexture(id);
	}
	
	@Override
	protected void onDraw() {
		TextureDrawer.draw(texture, this);
	}
}
