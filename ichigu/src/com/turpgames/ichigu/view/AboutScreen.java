package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.AboutInfo;
import com.turpgames.ichigu.model.display.IchiguToolbar;

public class AboutScreen extends IchiguScreen {
	private AboutInfo aboutInfo;

	@Override
	public void init() {
		super.init();
		aboutInfo = new AboutInfo();
		registerDrawable(aboutInfo, Game.LAYER_SCREEN);
	}
	
	@Override
	protected void onAfterActivate() {
		IchiguToolbar.getInstance().activateBackButton();
		aboutInfo.activate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		IchiguToolbar.getInstance().deactivateBackButton();
		aboutInfo.deactivate();
		return true;
	}
}
