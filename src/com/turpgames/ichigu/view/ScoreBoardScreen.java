package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.game.HiScores;

public class ScoreBoardScreen extends IchiguScreen {
	private HiScores hiScores;

	@Override
	protected void onAfterActivate() {
		hiScores.activate();
		IchiguToolbar.getInstance().activateBackButton();
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		hiScores.deactivate();
		IchiguToolbar.getInstance().deactivateBackButton();
		return true;
	}

	@Override
	public void init() {
		super.init();
		hiScores = new HiScores();
		registerDrawable(hiScores, Utils.LAYER_INFO);
	}
}
