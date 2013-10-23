package com.turpgames.ichigu.view;

import com.turpgames.ichigu.model.display.Toolbar;
import com.turpgames.ichigu.model.game.HiScores;

public class ScoreBoardScreen extends IchiguScreen {
	private HiScores hiScores;

	@Override
	protected void onAfterActivate() {
		hiScores.activate();
		Toolbar.getInstance().activateBackButton();
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		hiScores.deactivate();
		return true;
	}

	@Override
	public void init() {
		super.init();
		hiScores = new HiScores();
		registerDrawable(hiScores, 10);
	}
}
