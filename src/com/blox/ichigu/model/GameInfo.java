package com.blox.ichigu.model;

import com.blox.framework.v0.IDrawable;
import com.blox.framework.v0.impl.Text;
import com.blox.framework.v0.util.Color;
import com.blox.framework.v0.util.Game;

class GameInfo implements IDrawable {
	private Text text;

	GameInfo() {
		text = new Text();
		text.setWidth(Game.getVirtualWidth());
		text.setHeight(Game.getVirtualHeight());
	}

	public void locate(int halign, int valign) {
		text.setHorizontalAlignment(halign);
		text.setVerticalAlignment(valign);
	}

	public void setPadding(float padX, float padY) {
		text.setPadX(padX);
		text.setPadY(padY);
	}

	public void setColor(Color color) {
		text.getColor().set(color);
	}
	
	public String getText() {
		return text.getText();
	}
	
	public void setText(String info) {
		text.setText(info);
	}

	@Override
	public void draw() {
		text.draw();		
	}
}
