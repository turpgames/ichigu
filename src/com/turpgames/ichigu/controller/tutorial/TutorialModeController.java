package com.turpgames.ichigu.controller.tutorial;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.tutorial.TutorialMode;
import com.turpgames.ichigu.view.TutorialModeScreen;

public class TutorialModeController extends IchiguController<TutorialModeState> implements ITutorialModeActionListener {
	final TutorialMode model;
	final TutorialModeScreen view;

	private TutorialModeState tutorialState;

	public TutorialModeController(TutorialModeScreen screen) {
		view = screen;
		model = new TutorialMode();
		model.setModeListener(this);
		tutorialState = new TutorialModeTutorialState(this);
	}

	@Override
	public void onScreenActivated() {
		super.onScreenActivated();
		setTutorialState();
	}

	@Override
	public void onTutorialEnd() {
		currentState.onTutorialEnd();
	}

	void setTutorialState() {
		setState(tutorialState);
	}
}