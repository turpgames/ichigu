package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.ichigu.utils.R;

public class Logo extends GameObject {
	private ITexture logo;

	public Logo() {
		logo = Game.getResourceManager().getTexture(R.game.textures.logo);

		setWidth(R.sizes.logoSize);
		setHeight(R.sizes.logoSize);
		getLocation().set(
				(Game.getVirtualWidth() - R.sizes.logoSize) / 2, 
				Game.screenToViewportY((Game.getScreenHeight() - Game.scale(R.sizes.logoSize) - Game.getScreenHeight() / 32)));
	}

	@Override
	public void draw() {
		TextureDrawer.draw(logo, this);
	}
}
