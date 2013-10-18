package com.blox.ichigu.model;

import com.blox.framework.v0.IDrawable;
import com.blox.framework.v0.IDrawingInfo;
import com.blox.framework.v0.ITexture;
import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.TextureDrawer;

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
