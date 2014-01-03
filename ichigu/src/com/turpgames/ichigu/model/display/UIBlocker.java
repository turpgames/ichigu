package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Drawer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.ichigu.utils.Ichigu;

public class UIBlocker implements IDrawable {
	public final static UIBlocker instance = new UIBlocker();

	private IDrawable blockMessage;
	private Text message;
	private Color color;

	private UIBlocker() {
		color = Color.black();
		color.a = 0.5f;

		message = new Text();
		message.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		message.setSize(Game.getVirtualWidth(), Game.getVirtualHeight());
		message.setFontScale(1.5f);
		message.getColor().set(Color.white());
	}

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
		message.setText(Ichigu.getString(blockMessage));
		message.getColor().set(color);
		message.setFontScale(fontScale);
		block(message);
	}

	public void block(IDrawable blockMessage) {
		this.blockMessage = blockMessage;
		Game.getInputManager().deactivate();
		Drawer.getCurrent().register(this, Game.LAYER_DIALOG);
	}

	public void unblock() {
		Drawer.getCurrent().unregister(this);
		Game.getInputManager().activate();
		this.blockMessage = null;
	}
	
	public void setMessage(String blockMessage) {
		message.setText(Ichigu.getString(blockMessage));
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
