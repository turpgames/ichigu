package com.turpgames.ichigu.model;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IDrawingInfo;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class Background implements IDrawable {
	private ITexture bg;

	public Background() {
		bg = Game.getResourceManager().getTexture("bg");
	}

	@Override
	public void draw() {
		TextureDrawer.draw(bg, IDrawingInfo.screen);
	}
}
