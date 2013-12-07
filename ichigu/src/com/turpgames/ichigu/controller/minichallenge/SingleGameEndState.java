package com.turpgames.ichigu.controller.minichallenge;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.Ichigu;

public class SingleGameEndState extends SingleGameState {
	public SingleGameEndState(SingleGameController controller) {
		super(controller);
	}

	@Override
	protected void activated() {
		Ichigu.playSoundTimeUp();
		model.endMode();
		Game.vibrate(100);
	}

	@Override
	public void onUnblock() {

	}
	
	@Override
	public void onNewGame() {
		model.startMode();
		controller.setDealingState();
	}

	@Override
	public void draw() {
		model.drawResultScreen();
	}
}