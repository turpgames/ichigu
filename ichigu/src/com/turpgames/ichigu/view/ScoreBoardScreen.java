package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.HiScores;
import com.turpgames.ichigu.model.display.IchiguToolbar;

public class ScoreBoardScreen extends IchiguScreen {
	private HiScores hiScores;

	@Override
	public void init() {
		super.init();
		hiScores = new HiScores();
		registerDrawable(hiScores, Game.LAYER_SCREEN);
	}
	
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
}
