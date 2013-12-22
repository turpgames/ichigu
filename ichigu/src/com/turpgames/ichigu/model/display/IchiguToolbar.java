package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.ToggleButton;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.ichigu.utils.R;

public class IchiguToolbar extends Toolbar {

	private static IchiguToolbar instance;

	public static IchiguToolbar getInstance() {
		if (instance == null)
			instance = new IchiguToolbar();
		return instance;
	}

	private IchiguToolbar() {
		
	}

	public void disable() {
		settingsButton.deactivate();
		backButton.deactivate();
	}

	public void enable() {
		settingsButton.activate();
		backButton.activate();
	}
	
	public GameObject getBackButton() {
		return backButton;
	}

	@Override
	protected void concreteAddBackButton() {
		backButton = new ImageButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.game.textures.toolbar.back, R.colors.buttonDefault, R.colors.ichiguYellow);
		backButton.setLocation(Button.AlignNW, R.sizes.toolbarMargin, R.sizes.toolbarMargin);
	}

	@Override
	protected void concreteAddSettingsButton() {
		settingsButton = new ImageButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.game.textures.toolbar.settings, R.colors.buttonDefault, R.colors.ichiguYellow);
		settingsButton.setLocation(Button.AlignNE,R.sizes. toolbarMargin, R.sizes.toolbarMargin);
	}

	@Override
	protected void concreteAddSoundButton() {
		soundButton = new ToggleButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.settings.sound, R.game.textures.toolbar.soundOn, R.game.textures.toolbar.soundOff, 
				R.colors.ichiguCyan, R.colors.ichiguWhite);
		soundButton.setLocation(Button.AlignNE, R.sizes.menuButtonSizeToScreen + 2 * R.sizes.menuButtonSpacing + R.sizes.toolbarMargin, R.sizes.toolbarMargin);
	}

	@Override
	protected void concreteAddVibrationButton() {
		vibrationButton = new ToggleButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.settings.vibration, R.game.textures.toolbar.vibrationOn, 
				R.game.textures.toolbar.vibrationOff, R.colors.ichiguCyan, R.colors.ichiguWhite);
		vibrationButton.setLocation(Button.AlignNE, 2 * R.sizes.menuButtonSizeToScreen + 3 * R.sizes.menuButtonSpacing + R.sizes.toolbarMargin, R.sizes.toolbarMargin);	
	}
}
