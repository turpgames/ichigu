package com.turpgames.ichigu.controller.normal;

import com.turpgames.ichigu.controller.IchiguState;
import com.turpgames.ichigu.model.fullgame.normal.NormalMode;
import com.turpgames.ichigu.view.NormalModeScreen;

public abstract class NormalModeState extends IchiguState implements INormalModeActionListener {
	final NormalMode model;
	final NormalModeScreen view;
	final NormalModeController controller;

	public NormalModeState(NormalModeController controller) {
		this.controller = controller;
		this.model = controller.model;
		this.view = controller.view;
	}

	@Override
	public void onModeEnd() {
		controller.setEndState();
	}

	@Override
	public void onNewGame() {

	}

	@Override
	public void onDealEnd() {
				
	}
	
	@Override
	public void onExitConfirmed() {
		view.onExitConfirmed();
	}

	@Override
	public void draw() {
		model.draw();
	}

	@Override
	public boolean onScreenDeactivated() {
		return model.exitMode();
	}
}