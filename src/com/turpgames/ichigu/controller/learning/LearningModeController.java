package com.turpgames.ichigu.controller.learning;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.LearningMode;
import com.turpgames.ichigu.view.LearningModeScreen;

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