package com.turpgames.ichigu.controller.practice;

import com.turpgames.ichigu.controller.IchiguController;
import com.turpgames.ichigu.model.singlegame.practice.PracticeMode;
import com.turpgames.ichigu.view.PracticeModeScreen;

public class PracticeModeController extends IchiguController<PracticeModeState> {
	final PracticeMode model;
	final PracticeModeScreen view;
	
	private PracticeModeState waitingState;
	private PracticeModeState dealingState;
	
	public PracticeModeController(PracticeModeScreen screen) {
		view = screen;
		model = new PracticeMode();
		model.setModeListener(this);
		waitingState = new PracticeModeWaitingState(this);
		dealingState = new PracticeModeDealingState(this);
	}

	@Override
	public void onScreenActivated() {
		super.onScreenActivated();
		setDealingState();
	}

	public void setDealingState() {
		setState(dealingState);
	}
	
	void setWaitingState() {
		setState(waitingState);
	}
}
