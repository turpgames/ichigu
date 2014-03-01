package com.turpgames.ichigu.controller.singlegame;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.Ichigu;

public class SingleGameEndState extends SingleGameState {
	public SingleGameEndState(SingleGameController controller) {
		super(controller);
	}

	@Override
	public void draw() {
		model.drawResultScreen();
	}

	@Override
	public void onNewGame() {
		model.startMode();
		controller.setDealingState();
	}
	
	@Override
	public void onUnblock() {

	}

	@Override
	protected void activated() {
		Ichigu.playSoundTimeUp();
		model.endMode();
		Game.vibrate(100);
	}
}