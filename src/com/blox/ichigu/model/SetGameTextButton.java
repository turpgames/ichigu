package com.blox.ichigu.model;

import com.blox.framework.v0.IFont;
import com.blox.framework.v0.util.Color;
import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.TextDrawer;
import com.blox.framework.v0.util.Vector;

class IchiguTextButton extends IchiguButton {
	private String text;
	private IFont font;
		
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		updateSize();
	}

	public IFont getFont() {
		return font;
	}

	public void setFont(IFont font) {
		this.font = font;
		updateSize();
	}

	private void updateSize() {
		if (font == null || text == null)
			return;
		Vector textSize = font.measureText(text);
		
		setWidth(Game.descale(textSize.x) + 10);
		setHeight(Game.descale(textSize.y) + 5);
	}

	@Override
	public Color getColor() {
		if (font != null)
			return font.getColor();
		return super.getColor();
	}
	
	@Override
	protected void onDraw() {
		TextDrawer.draw(font, text, this);
	}
}