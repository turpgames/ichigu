package com.blox.ichigu.model;

import com.blox.framework.v0.ITexture;
import com.blox.framework.v0.impl.Settings;
import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.TextureDrawer;
import com.blox.ichigu.utils.R;

class SettingsButton extends ToolbarButton {
	private ITexture textureOff;
	private String settingsKey;
	private boolean isOn;

	SettingsButton(String settingsKey) {
		super();
		this.settingsKey = settingsKey;
		this.isOn = Settings.getBoolean(settingsKey, true);
	}

	void setOffTexture(String offTextureId) {
		textureOff = Game.getResourceManager().getTexture(offTextureId);
	}
	
	void setOnTexture(String onTextureId) {
		setTexture(onTextureId);
	}

	@Override
	protected void onDraw() {
		if(isOn) 
			getColor().set(R.colors.ichiguGreen);
		else
			getColor().set(R.colors.ichiguWhite);
		
		if (isOn)
			super.onDraw();
		else
			TextureDrawer.draw(textureOff, this);
	}

	@Override
	protected boolean onTap() {
		isOn = !isOn;
		Settings.putBoolean(settingsKey, isOn);
		return super.onTap();
	}

	void toggleActivation() {
		if (isActive())
			deactivate();
		else
			activate();
	}
}
