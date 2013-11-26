package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.game.AboutInfo;

public class AboutScreen extends IchiguScreen {
	private AboutInfo aboutInfo;

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

	@Override
	public void init() {
		super.init();
		aboutInfo = new AboutInfo();
		registerDrawable(aboutInfo, Utils.LAYER_SCREEN);
	}
}
