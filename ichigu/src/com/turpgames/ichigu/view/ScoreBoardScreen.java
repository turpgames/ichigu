package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.hiscores.HiScores;

public class ScoreBoardScreen extends IchiguScreen {
	private HiScores hiScores;
	
	@Override
	protected void onAfterActivate() {
		if (hiScores == null) {
			hiScores = new HiScores();
			registerDrawable(hiScores, Game.LAYER_SCREEN);
		}
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
