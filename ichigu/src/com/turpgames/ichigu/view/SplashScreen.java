package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.ichigu.model.display.TurpLogo;
import com.turpgames.ichigu.updates.IchiguUpdateManager;
import com.turpgames.ichigu.utils.R;

public class SplashScreen extends Screen {

	private IResourceManager resourceManager;
	private Color progressColor;

	@Override
	public void draw() {
		super.draw();

		float width = 500 * resourceManager.getProgress();
		float height = 20;
		float x = (Game.getVirtualWidth() - width) / 2;

		ShapeDrawer.drawRect(x, 100, width, height, progressColor, true, false);
	}

	@Override
	public void init() {		
		super.init();
		registerDrawable(new TurpLogo(), Game.LAYER_BACKGROUND);
		progressColor = new Color(R.colors.ichiguYellow);
		resourceManager = Game.getResourceManager();
	}

	@Override
	public void update() {
		if (!resourceManager.isLoading()) {
			IchiguUpdateManager.runUpdates();
			
			Button.defaultClickSound = Game.getResourceManager().getSound(R.game.sounds.flip);
			switchToMenu();
		}
	}

	private void switchToMenu() {
		ScreenManager.instance.switchTo(R.game.screens.menu, false);
	}
}