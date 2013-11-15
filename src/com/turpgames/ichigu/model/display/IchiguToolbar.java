package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.ToggleButton;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class IchiguToolbar extends Toolbar {
	private static final float buttonSpacing = Game.scale(10);
	private static final float toolbarMargin = Game.scale(15);
	private static final float buttonSize = Game.scale(R.ui.imageButtonWidth);

	private static IchiguToolbar instance;

	public static IchiguToolbar getInstance() {
		if (instance == null)
			instance = new IchiguToolbar();
		return instance;
	}

	private IchiguToolbar() {
		
	}

	public void enable() {
		settingsButton.activate();
	}

	public void disable() {
		settingsButton.deactivate();
	}
	
	@Override
	protected void concreteAddBackButton() {
		backButton = new ImageButton(buttonSize, buttonSize, R.game.textures.toolbar.back, R.colors.buttonDefault, R.colors.buttonTouched);
		backButton.setLocation(Button.AlignNW, toolbarMargin, toolbarMargin);
	}

	@Override
	protected void concreteAddSettingsButton() {
		settingsButton = new ImageButton(buttonSize, buttonSize, R.game.textures.toolbar.settings, R.colors.buttonDefault, R.colors.buttonTouched);
		settingsButton.setLocation(Button.AlignNE, toolbarMargin, toolbarMargin);
	}

	@Override
	protected void concreteAddSoundButton() {
		soundButton = new ToggleButton(buttonSize, buttonSize, R.settings.sound, R.game.textures.toolbar.soundOn, R.game.textures.toolbar.soundOff, 
				R.colors.ichiguCyan, R.colors.ichiguWhite);
		soundButton.setLocation(Button.AlignNE, buttonSize + 2 * buttonSpacing + toolbarMargin, toolbarMargin);
	}

	@Override
	protected void concreteAddVibrationButton() {
		vibrationButton = new ToggleButton(buttonSize, buttonSize, R.settings.vibration, R.game.textures.toolbar.vibrationOn, 
				R.game.textures.toolbar.vibrationOff, R.colors.ichiguCyan, R.colors.ichiguWhite);
		vibrationButton.setLocation(Button.AlignNE, 2 * buttonSize + 3 * buttonSpacing + toolbarMargin, toolbarMargin);	
	}
}
