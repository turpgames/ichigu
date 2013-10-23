package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.ichigu.utils.R;

public class IchiguImageButton extends IchiguButton {	
	private ITexture texture;
	
	public IchiguImageButton() {
		super();
		setWidth(R.ui.imageButtonSize);
		setHeight(R.ui.imageButtonSize);
	}
	
	public void setTexture(String id) {
		texture = Game.getResourceManager().getTexture(id);
	}
	
	@Override
	protected void onDraw() {
		TextureDrawer.draw(texture, this);
	}
}
