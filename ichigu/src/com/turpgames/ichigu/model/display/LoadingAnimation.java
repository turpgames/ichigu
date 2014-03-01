package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.impl.AnimatedGameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class LoadingAnimation extends AnimatedGameObject {
	private final Text message;

	private LoadingAnimation() {
		addAnimation(R.game.animations.loading);
		setWidth(75);
		setHeight(75);
		getLocation().x = (Game.getVirtualWidth() - getWidth()) / 2;
		getLocation().y = (Game.getVirtualHeight() - getHeight()) / 2;
		startAnimation(R.game.animations.loading);

		message = new Text();
		message.setFontScale(R.fontSize.medium);
		message.setAlignment(Text.HAlignCenter, Text.VAlignBottom);
		message.setY(getLocation().y - 30f);
	}

	public void setMessage(String message) {
		this.message.setText(message);
	}

	public final static LoadingAnimation instance = new LoadingAnimation();
	
	@Override
	public void draw() {
		message.draw();
		super.draw();
	}
}
