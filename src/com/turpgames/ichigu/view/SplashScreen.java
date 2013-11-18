package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.UpdateProcessor;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.display.Logo;
import com.turpgames.ichigu.utils.R;

public class SplashScreen extends Screen {

	private IResourceManager resourceManager;
	private Color progressColor;

	@Override
	public void init() {
		// AfterUpdateProcess's must be added here UpdateProcessor.instance.addProcess();
		
		super.init();
		registerDrawable(new Logo(), Utils.LAYER_BACKGROUND);
		progressColor = new Color(R.colors.ichiguRed);
		resourceManager = Game.getResourceManager();
	}

	@Override
	public void draw() {
		super.draw();

		float width = 500 * resourceManager.getProgress();
		float height = 10;
		float x = (Game.getVirtualWidth() - width) / 2;

		ShapeDrawer.drawRect(x, 100, width, height, progressColor, true, false);
	}

	@Override
	public void update() {
		if (!resourceManager.isLoading()) {
			
			// Execute AfterUpdateProcess's after loading resources
			UpdateProcessor.instance.execute();
			
			Button.defaultClickSound = Game.getResourceManager().getSound(R.game.sounds.flip);
			switchToMenu();
		}
		else {
			setProgressColor(resourceManager.getProgress());
		}
	}

	private void setProgressColor(float progress) {
		if (progress < 0.5f) {
			progressColor.r = (1 - 2 * progress) * R.colors.ichiguRed.r + 2 * progress * R.colors.ichiguGreen.r;
			progressColor.g = (1 - 2 * progress) * R.colors.ichiguRed.g + 2 * progress * R.colors.ichiguGreen.g;
			progressColor.b = (1 - 2 * progress) * R.colors.ichiguRed.b + 2 * progress * R.colors.ichiguGreen.b;
		}
		else {
			progressColor.r = 2 * (1 - progress) * R.colors.ichiguGreen.r + 2 * (progress - 0.5f) * R.colors.ichiguBlue.r;
			progressColor.g = 2 * (1 - progress) * R.colors.ichiguGreen.g + 2 * (progress - 0.5f) * R.colors.ichiguBlue.g;
			progressColor.b = 2 * (1 - progress) * R.colors.ichiguGreen.b + 2 * (progress - 0.5f) * R.colors.ichiguBlue.b;
		}
	}

	private void switchToMenu() {
		ScreenManager.instance.switchTo(R.game.screens.menu, false);
	}
}