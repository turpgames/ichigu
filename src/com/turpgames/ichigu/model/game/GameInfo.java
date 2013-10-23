package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;

public class GameInfo implements IDrawable {
	protected Text text;

	public GameInfo() {
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
