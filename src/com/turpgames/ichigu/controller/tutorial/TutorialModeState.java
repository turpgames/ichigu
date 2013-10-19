package com.turpgames.ichigu.controller.tutorial;

import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.tutorial.TutorialMode;
import com.turpgames.ichigu.utils.R;
import com.turpgames.ichigu.view.TutorialModeScreen;

public abstract class TutorialModeState extends IchiguState implements ITutorialModeActionListener {
	final TutorialMode model;
	final TutorialModeScreen view;
	final TutorialModeController controller;

	public TutorialModeState(TutorialModeController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}
	
	@Override
	protected void activated() {
		model.beginTutorial();
	}

	@Override
	protected void deactivated() {
		model.endTutorial();
	}

	@Override
	public void onTutorialEnd() {
		// Change to practice screen
		ScreenManager.instance.switchTo(R.game.screens.practice, true);
//		controller.setDealingState();
	}
}