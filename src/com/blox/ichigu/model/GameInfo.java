package com.blox.ichigu.model;

import com.blox.framework.v0.IFont;
import com.blox.framework.v0.util.Color;
import com.blox.framework.v0.util.FontManager;
import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.TextDrawer;

class GameInfo extends IchiguObject {
	private String text;
	private int alignment;
	private int shiftY;
	private IFont font;

	GameInfo(float paddingX, float paddingY) {
		getLocation().set(paddingX, paddingY);
		setWidth(Game.getVirtualWidth() - 2 * paddingX);
		setHeight(Game.getVirtualHeight() - 2 * paddingY);
		getColor().set(Color.white());
		
		font = FontManager.createDefaultFontInstance();
//		setFontScale(R.fontSize.small);
	}

	void setFontScale(float scale) {
		this.font.setScale(scale);
	}
	
	@Override
	public void draw() {
		Game.pushRenderingShift(0, shiftY, false);
		TextDrawer.draw(font, text, this, alignment);
		Game.popRenderingShift();
	}

	public void draw(String text, int alignment, int shiftY) {
		this.alignment = alignment;
		this.text = text;
		this.shiftY = shiftY;
		this.draw();
	}
}