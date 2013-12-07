package com.turpgames.ichigu.controller.fullgame;

import com.turpgames.ichigu.utils.Ichigu;

public class FullGameEndState extends FullGameState {
	public FullGameEndState(FullGameController controller) {
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