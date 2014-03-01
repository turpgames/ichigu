package com.turpgames.ichigu.model.game.mode.singlegame;

import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.HintButton;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.model.display.SingleGameQuestion;
import com.turpgames.ichigu.model.game.table.SingleGameTable;
import com.turpgames.ichigu.utils.R;

public class Practice extends SingleGameMode {
	private HintButton hintButton;
	
	public Practice() {
		question = new SingleGameQuestion(0.3f, 1.2f);

		hintButton = new HintButton(R.game.textures.hint);
		hintButton.getLocation().set((Game.getScreenWidth() - hintButton.getWidth()) / 2, Game.viewportToScreenY(50));
		hintButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				((SingleGameTable)table).showHint();
			}
		});

		
		resetButton.deactivate();
	}

	@Override
	public void concreteInvalidIchiguSelected() {
		IchiguToast.showError(R.strings.tryAgain);
		super.concreteInvalidIchiguSelected();
	}
	
	@Override
	public void deal() {
		super.deal();
		IchiguToast.hide();
	}

	@Override
	public void dealEnded() {
		hintButton.activateHint();
		super.dealEnded();
	}
	
	@Override
	public void dealStarted() {
		hintButton.deactivateHint();
		super.dealStarted();
	}
	
	@Override
	protected void onDraw() {
		hintButton.draw();
		super.onDraw();
	}

	@Override
	protected boolean onExitMode() {
		hintButton.deactivateHint();
		isExitConfirmed = true;
		return super.onExitMode();
	}

	@Override
	protected void pauseTimer() {
		
	}

	@Override
	protected void prepareResultInfoAndSaveHiscore() {
		
	}

	@Override
	protected void startTimer() {
		
	}
}
