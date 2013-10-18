package com.blox.ichigu.view;

import com.blox.ichigu.model.HiScores;
import com.blox.ichigu.model.Toolbar;

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
