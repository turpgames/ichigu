package com.blox.ichigu.model;

import com.blox.framework.v0.impl.AttachedText;
import com.blox.framework.v0.impl.Text;
import com.blox.framework.v0.util.Color;

class IchiguTextButton extends IchiguButton {
	private Text text;
	
	public IchiguTextButton() {
		text = new AttachedText(this);
		text.setPadX(10);
		text.setPadY(5);
	}

	public String getText() {
		return this.text.getText();
	}

	public void setText(String text) {
		this.text.setText(text);
		updateSize();
	}

	private void updateSize() {
		setWidth(text.getTextAreaWidth() + 20);
		setHeight(text.getTextAreaHeight() + 10);
	}

	@Override
	public Color getColor() {
		return text.getColor();
	}

	@Override
	protected void onDraw() {
		text.draw();
	}
}