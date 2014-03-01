package com.turpgames.ichigu.controller.fullgame;

import com.turpgames.ichigu.utils.Ichigu;

class FullGameEndState extends FullGameState {
	public FullGameEndState(FullGameController controller) {
		super(controller);
	}

	@Override
	public void draw() {
		model.drawResult();
	}

	@Override
	public void onNewGame() {
		model.startMode();
		controller.setDealingState();
	}
	
	@Override
	protected void activated() {
		Ichigu.playSoundTimeUp();
		model.endMode();
	}
}