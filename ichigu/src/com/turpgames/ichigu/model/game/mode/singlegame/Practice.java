package com.turpgames.ichigu.model.game.mode.singlegame;

import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.HintButton;
import com.turpgames.ichigu.model.display.SingleGameQuestion;
import com.turpgames.ichigu.model.display.TryAgainToast;

public class Practice extends SingleGameMode {
	private TryAgainToast tryAgain;
	private HintButton hintButton;
	
	public Practice() {
		tryAgain = new TryAgainToast();
		question = new SingleGameQuestion(0.3f, 1.2f);

		hintButton = new HintButton();
		hintButton.getLocation().set((Game.getScreenWidth() - hintButton.getWidth()) / 2, Game.viewportToScreenY(50));
		hintButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				table.showHint();
			}
		});

		
		resetButton.deactivate();
	}

	@Override
	protected boolean onExitMode() {
		hintButton.deactivateHint();
		isExitConfirmed = true;
		return super.onExitMode();
	}
	
	@Override
	public void deal() {
		super.deal();
		tryAgain.hide();
	}

	@Override
	public void dealStarted() {
		hintButton.deactivateHint();
		super.dealStarted();
	}
	
	@Override
	public void dealEnded() {
		hintButton.activateHint();
		super.dealEnded();
	}
	
	@Override
	protected void onDraw() {
		hintButton.draw();
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
	public void concreteInvalidIchiguSelected() {
		tryAgain.show();
		super.concreteInvalidIchiguSelected();
	}
}
