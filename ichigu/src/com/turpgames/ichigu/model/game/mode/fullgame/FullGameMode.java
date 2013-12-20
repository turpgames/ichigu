package com.turpgames.ichigu.model.game.mode.fullgame;

import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.HintButton;
import com.turpgames.ichigu.model.display.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.display.ResultScreenButtons;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.display.TryAgainToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.mode.RegularMode;
import com.turpgames.ichigu.model.game.table.FullGameTable;

public abstract class FullGameMode extends RegularMode implements IResultScreenButtonsListener {
	protected final static float secondPerPenalty = 10f;

	private HintButton hintButton;

	protected Text resultInfo;
	protected TimerText timerText;

	private TryAgainToast tryAgain;

	private ResultScreenButtons resultScreenButtons;

	public FullGameMode() {
		resultScreenButtons = new ResultScreenButtons(this);
		
		hintButton = new HintButton();
		hintButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				table.showHint();
			}
		});
		
		tryAgain = new TryAgainToast();

		timerText = new TimerText(getTimer());
		timerText.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		timerText.setPadding(Game.getVirtualWidth() - 115, 110);

		resultInfo = new Text();
		resultInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		resultInfo.setPadding(0, 150);
	}

	public void applyTimePenalty() {		
		getTimer().addSeconds(secondPerPenalty);
		timerText.flash();
	}
	
	public void cardTapped(Card card) {
		hintButton.restartNotificationTimer();
	}
	
	@Override
	public void concreteIchiguFound() {
		
	}
	
	@Override
	public void concreteInvalidIchiguSelected() {
		tryAgain.show();
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

	public void deckFinished() {
		notifyModeEnd();
	}

	public void drawResult() {
		resultInfo.draw();
		resultScreenButtons.draw();
	}

	@Override
	public void onBackToMenuTapped() {
		getModeListener().onExitConfirmed();
	}

	@Override
	public void onNewGameTapped() {
		notifyNewGame();
	}

	private void notifyNewGame() {
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}
	
	@Override
	protected FullGameTable getTable() {
		return (FullGameTable) table;
	}

	protected abstract Timer getTimer();

	@Override
	protected void initTable() {
		table = new FullGameTable();
	}

	protected void notifyModeEnd() {
		prepareResultInfoAndSaveHiscore();
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}
	
	@Override
	protected void onDraw() {
		timerText.draw();
		hintButton.draw();
		super.onDraw();
	}

	@Override
	protected void onEndMode() {
		getTimer().stop();
		hintButton.deactivateHint();
		resultScreenButtons.listenInput(true);
		super.onEndMode();
	}
	
	@Override
	protected boolean onExitMode() {
		if (!super.onExitMode())
			return false;
		getTimer().stop();
		resultScreenButtons.listenInput(false);
		hintButton.deactivateHint();
		return true;
	}
	
	@Override
	protected void onResetMode() {
		getTimer().restart();
		timerText.syncText();
		super.onResetMode();
	}
	
	@Override
	protected void onStartMode() {
		getTimer().restart();
		timerText.syncText();
		resultScreenButtons.listenInput(false);
		hintButton.activateHint();
		super.onStartMode();
	}

	@Override
	protected void pauseTimer() {
		getTimer().pause();
		hintButton.deactivateHint();
	}
	
	@Override
	protected void startTimer() {
		getTimer().start();
		hintButton.activateHint();
	}
}
