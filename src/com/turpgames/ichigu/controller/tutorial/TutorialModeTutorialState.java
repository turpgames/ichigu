package com.turpgames.ichigu.controller.tutorial;

public class TutorialModeTutorialState extends TutorialModeState {
	public TutorialModeTutorialState(TutorialModeController controller) {
		super(controller);
	}

	@Override
	public void draw() {
		model.draw();
	}
}