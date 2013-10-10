package com.blox.ichigu.controller.learning;

public class LearningModeTutorialState extends LearningModeState {
	public LearningModeTutorialState(LearningModeController controller) {
		super(controller);
	}

	@Override
	public void draw() {
		model.drawTutorial();
	}
}