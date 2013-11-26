package com.turpgames.ichigu.controller.practice;

import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.singlegame.practice.PracticeMode;
import com.turpgames.ichigu.view.PracticeModeScreen;

public abstract class PracticeModeState extends IchiguState {
	final PracticeMode model;
	final PracticeModeScreen view;
	final PracticeModeController controller;
	
	public PracticeModeState(PracticeModeController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}

	@Override
	public void onExitConfirmed() {
				
	}
	
	@Override
	public void draw() {
		model.draw();
	}
}
