package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IInputListener;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Drawer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;

public class UIBlocker implements IDrawable {
	public final static UIBlocker instance = new UIBlocker();

	private UIBlocker() {
		color = Color.black();
		color.a = 0.5f;

		text = new Text();
		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		text.setSize(Game.getVirtualWidth(), Game.getVirtualHeight());
		text.setFontScale(1.5f);
		text.getColor().set(Color.white());
	}

	private IDrawable blockMessage;
	private Text text;
	private Color color;

	public void block(String blockMessage) {
		block(blockMessage, Color.white(), 1.5f);
	}

	public void block(String blockMessage, Color color) {
		block(blockMessage, color, 1.5f);
	}

	public void block(String blockMessage, float fontScale) {
		block(blockMessage, Color.white(), fontScale);
	}

	public void block(String blockMessage, Color color, float fontScale) {
		text.setText(blockMessage);
		text.getColor().set(color);
		text.setFontScale(fontScale);
		block(text);
	}

	public void block(IDrawable blockMessage) {
		this.blockMessage = blockMessage;
		Game.getInputManager().register(IInputListener.blocker, Game.LAYER_DIALOG);
		Drawer.getCurrent().register(this, Game.LAYER_DIALOG);
	}

	public void unblock() {
		Drawer.getCurrent().unregister(this);
		Game.getInputManager().unregister(IInputListener.blocker);
		this.blockMessage = null;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void draw() {
		ShapeDrawer.drawRect(0, 0, Game.getScreenWidth(), Game.getScreenHeight(), color, true, true);
		if (blockMessage != null)
			blockMessage.draw();
	}
}
