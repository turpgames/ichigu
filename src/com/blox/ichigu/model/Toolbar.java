package com.blox.ichigu.model;

import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.Utils;
import com.blox.framework.v0.util.Vector;
import com.blox.ichigu.utils.R;

public class Toolbar extends IchiguObject {
	public static interface IToolbarListener {
		void onToolbarBack();
	}

	private static Toolbar instance;

	private static final float buttonSpacing = Game.scale(10);
	private static final float toolbarMargin = Game.scale(15);
	private static final float buttonSize = Game.scale(R.ui.imageButtonSize);

	public static void init() {
		if (instance == null)
			instance = new Toolbar();
	}

	public static Toolbar getInstance() {
		return instance;
	}

	private boolean isActive;

	private ToolbarButton backButton;
	private ToolbarButton settingsButton;

	private SettingsButton soundButton;
	private SettingsButton vibrationButton;

	private IToolbarListener listener;
	
	private Toolbar() {
		backButton = new ToolbarButton();
		backButton.setTexture(R.game.textures.toolbar.back);
		backButton.setLocation(ToolbarButton.AlignNW, toolbarMargin, toolbarMargin);
		backButton.deactivate();
		backButton.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onToolbarBack();
			}
		});

		settingsButton = new ToolbarButton();
		settingsButton.setTexture(R.game.textures.toolbar.settings);
		settingsButton.setLocation(ToolbarButton.AlignNE, toolbarMargin, toolbarMargin);
		settingsButton.activate();
		settingsButton.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				// musicButton.toggleActivation();
				soundButton.toggleActivation();
				vibrationButton.toggleActivation();
				isActive = !isActive;
			}
		});

		soundButton = new SettingsButton(R.settings.sound);
		soundButton.setLocation(ToolbarButton.AlignNE, buttonSize + 2 * buttonSpacing + toolbarMargin, toolbarMargin);
		soundButton.setOnTexture(R.game.textures.toolbar.soundOn);
		soundButton.setOffTexture(R.game.textures.toolbar.soundOff);
		soundButton.deactivate();

		vibrationButton = new SettingsButton(R.settings.vibration);
		vibrationButton.setLocation(ToolbarButton.AlignNE, 2 * buttonSize + 3 * buttonSpacing + toolbarMargin, toolbarMargin);
		vibrationButton.setOnTexture(R.game.textures.toolbar.vibrationOn);
		vibrationButton.setOffTexture(R.game.textures.toolbar.vibrationOff);
		vibrationButton.deactivate();

		listenInput(true);
	}

	public void setListener(IToolbarListener listener) {
		this.listener = listener;
	}

	public void activateBackButton() {
		backButton.activate();
	}

	public void deactivateBackButton() {
		backButton.deactivate();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Vector l = vibrationButton.getLocation();
		if (isActive && !Utils.isIn(Game.viewportToScreenX(x), Game.viewportToScreenY(y), l, Game.getScreenWidth() - l.x, Game.getScreenHeight() - l.y)) {
			soundButton.toggleActivation();
			vibrationButton.toggleActivation();
			isActive = false;
		}
		return super.touchDown(x, y, pointer, button);
	}

	@Override
	public void draw() {
		backButton.draw();
		settingsButton.draw();
		soundButton.draw();
		vibrationButton.draw();
	}
}
