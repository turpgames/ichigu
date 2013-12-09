package com.turpgames.ichigu.model.game.singlegame.modes;

import com.turpgames.ichigu.model.display.TryAgainToast;
import com.turpgames.ichigu.model.game.singlegame.SingleGameHint;
import com.turpgames.ichigu.model.game.singlegame.SingleGameMode;
import com.turpgames.ichigu.model.game.singlegame.SingleGameQuestion;

public class PracticeMode extends SingleGameMode {
	private SingleGameHint hint;
	private TryAgainToast tryAgain;
	
	public PracticeMode() {
		hint = new SingleGameHint(table);
		tryAgain = new TryAgainToast();
		question = new SingleGameQuestion(0.3f, 1.2f);
		
		resetButton.deactivate();
	}

	@Override
	protected boolean onExitMode() {
		hint.deactivate();
		isExitConfirmed = true;
		return super.onExitMode();
	}
	
	@Override
	public void deal() {
		super.deal();
		tryAgain.hide();
	}

	@Override
	protected void onDraw() {
		hint.draw();
		super.onDraw();
	}

	@Override
	protected void pauseTimer() {
		
	}

	@Override
	protected void startTimer() {
		
	}

	@Override
	protected void prepareResultInfoAndSaveHiscore() {
		
	}
	
	@Override
	public void dealEnded() {
		hint.update();
		super.dealEnded();
	}

	@Override
	public void concreteInvalidIchiguSelected() {
		tryAgain.show();
		super.concreteInvalidIchiguSelected();
	}
}
