package com.blox.ichigu.controller.learning;

import com.blox.ichigu.controller.IchiguController;
import com.blox.ichigu.model.LearningMode;
import com.blox.ichigu.view.LearningModeScreen;

public class LearningModeController extends IchiguController<LearningModeState> implements ILearningModeActionListener {
	final LearningMode model;
	final LearningModeScreen view;

	private LearningModeState tutorialState;
	private LearningModeState waitingState;
	private LearningModeState dealingState;

	public LearningModeController(LearningModeScreen screen) {
		view = screen;
		model = new LearningMode();
		model.setModeListener(this);
		tutorialState = new LearningModeTutorialState(this);
		waitingState = new LearningModeWaitingState(this);
		dealingState = new LearningModeDealingState(this);
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

	void setDealingState() {
		setState(dealingState);
	}

	void setWaitingState() {
		setState(waitingState);
	}
}