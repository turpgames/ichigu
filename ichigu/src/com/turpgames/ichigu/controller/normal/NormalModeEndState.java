package com.turpgames.ichigu.controller.normal;

import com.turpgames.ichigu.utils.Ichigu;

public class NormalModeEndState extends NormalModeState {
	public NormalModeEndState(NormalModeController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		Ichigu.playSoundTimeUp();
		model.endMode();
	}

	@Override
	public void onNewGame() {
		model.startMode();
		controller.setDealingState();
	}
	
	@Override
	public void draw() {
		model.drawResult();
	}
}