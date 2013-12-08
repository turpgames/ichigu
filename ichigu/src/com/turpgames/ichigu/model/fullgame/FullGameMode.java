package com.turpgames.ichigu.model.fullgame;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.NoTipToast;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.display.TryAgainToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IIchiguModeListener;
import com.turpgames.ichigu.model.game.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.game.IchiguMode;
import com.turpgames.ichigu.model.game.ResultScreenButtons;
import com.turpgames.ichigu.model.game.newmodels.FullGameTable;
import com.turpgames.ichigu.utils.Ichigu;

public abstract class FullGameMode extends IchiguMode implements IResultScreenButtonsListener, IHintListener {
	protected final static float secondPerPenalty = 10f;

//	private FullGameHint hint;

	protected Text resultInfo;
	protected TimerText timerText;

	private TryAgainToast tryAgain;
	private NoTipToast noTip;

	private ResultScreenButtons resultScreenButtons;

	public FullGameMode() {
		resultScreenButtons = new ResultScreenButtons(this);

//		hint = new FullGameHint(table);
//		hint.setLocation(10, Game.viewportToScreenY(30));
//		hint.activate();
//		hint.setHintListener(this);

		tryAgain = new TryAgainToast();
		noTip = new NoTipToast();

		timerText = new TimerText(getTimer());
		timerText.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		timerText.setPadding(Game.getVirtualWidth() - 115, 110);

		resultInfo = new Text();
		resultInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		resultInfo.setPadding(0, 150);
	}

	@Override
	protected void initTable() {
		table = new FullGameTable();
	}
	
	protected abstract Timer getTimer();

	@Override
	protected void pauseTimer() {
		getTimer().pause();
//		hint.deactivate();
	}

	@Override
	protected void startTimer() {
		getTimer().start();
//		hint.activate();
	}

	@Override
	public IIchiguModeListener getModeListener() {
		return (IIchiguModeListener) super.modeListener;
	}

	private void notifyNewGame() {
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	protected void notifyModeEnd() {
		prepareResultInfoAndSaveHiscore();
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	public void cardTapped(Card card) {
//		hint.restartNotificationTimer();
	}

	protected void onOpenExtraCards() {
		applyTimePenalty();
//		hint.update();
	}

	private void applyTimePenalty() {
//		if (hint.getIchiguCount() == 0)
//			return;
		
		getTimer().addSeconds(secondPerPenalty);
		timerText.flash();
	}

	@Override
	public void onInsufficientHint() {
		Ichigu.playSoundError();
		noTip.show();
	}

	@Override
	protected void onStartMode() {
		getTimer().restart();
		timerText.syncText();
		resultScreenButtons.listenInput(false);
//		hint.activate();
		super.onStartMode();
	}

	@Override
	protected void onResetMode() {
		getTimer().restart();
		timerText.syncText();
		super.onResetMode();
	}
	
	@Override
	protected void onEndMode() {
		getTimer().stop();
//		hint.deactivate();
		resultScreenButtons.listenInput(true);
		super.onEndMode();
	}

	@Override
	protected boolean onExitMode() {
		if (!super.onExitMode())
			return false;
		getTimer().stop();
		resultScreenButtons.listenInput(false);
//		hint.deactivate();
		return true;
	}

	@Override
	protected void onDraw() {
		table.drawCards();
		timerText.draw();
//		hint.draw();
		super.onDraw();
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
	
	@Override
	public void deal() {
		super.deal();
//		hint.update();
	}
	
	public void dealEnded() {
		
	}
	
	public void deckFinished() {
		prepareResultInfoAndSaveHiscore();
		notifyModeEnd();
	}

	@Override
	public void concreteIchiguFound() {
		
	}
	
	@Override
	public void concreteInvalidIchiguSelected() {
		tryAgain.show();
	}
}
