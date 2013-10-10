package com.blox.ichigu.view;

import com.blox.ichigu.model.HiScores;

public class ScoreBoardScreen extends IchiguScreen {
	private HiScores hiScores;

	@Override
	protected String getTitle() {
		return "Hi Scores";
	}

	@Override
	public void activated() {
		super.activated();
		hiScores.activate();
	}

	@Override
	public void deactivated() {
		super.deactivated();
		hiScores.deactivate();
	}

	@Override
	public void init() {
		super.init();
		hiScores = new HiScores();
		registerDrawable(hiScores, 10);
	}
}
