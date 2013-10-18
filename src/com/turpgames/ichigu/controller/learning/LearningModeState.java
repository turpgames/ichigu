package com.turpgames.ichigu.controller.learning;

import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.LearningMode;
import com.turpgames.ichigu.view.LearningModeScreen;

public abstract class LearningModeState extends IchiguState implements ILearningModeActionListener {
	final LearningMode model;
	final LearningModeScreen view;
	final LearningModeController controller;

	public LearningModeState(LearningModeController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}

	@Override
	public boolean onScreenDeactivated() {
		model.exitMode();
		return true;
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
		controller.setDealingState();
	}

	@Override
	public void draw() {
		model.drawGame();
	}
}