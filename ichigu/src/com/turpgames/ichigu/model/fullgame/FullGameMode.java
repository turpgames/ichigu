package com.turpgames.ichigu.model.fullgame;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.NoTipToast;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.display.TryAgainToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.game.IchiguMode;
import com.turpgames.ichigu.model.game.ResultScreenButtons;
import com.turpgames.ichigu.utils.Ichigu;

public abstract class FullGameMode extends IchiguMode implements IResultScreenButtonsListener, IHintListener {
	protected final static float secondPerPenalty = 10f;

	private FullGameHint hint;
	private int selectedCardCount;

	protected Text resultInfo;
	protected TimerText timerText;

	private TryAgainToast tryAgain;
	private NoTipToast noTip;

	private ResultScreenButtons resultScreenButtons;

	public FullGameMode() {
		resultScreenButtons = new ResultScreenButtons(this);

		hint = new FullGameHint(getDealer());
		hint.setLocation(10, Game.viewportToScreenY(30));
		hint.activate();
		hint.setHintListener(this);

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
	protected void initDealer() {
		dealer = new FullGameCardDealer(this);
	}
	
	protected abstract Timer getTimer();

	@Override
	protected void pauseTimer() {
		getTimer().pause();
		hint.deactivate();
	}

	@Override
	protected void startTimer() {
		getTimer().start();
		hint.activate();
	}

	protected FullGameCardDealer getDealer() {
		return (FullGameCardDealer) dealer;
	}

	@Override
	public IFullGameModeListener getModeListener() {
		return (IFullGameModeListener) super.modeListener;
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

	protected int checkIchigu() {
		int score = dealer.getScore();
		if (score > 0) {
			notifyIchiguFound();
		}
		else {
			tryAgain.show();
			dealer.deselectCards();
			notifyInvalidIchiguSelected();
		}
		return score;
	}
	
	@Override
	public void onCardTapped(Card card) {
		super.onCardTapped(card);
		hint.restartNotificationTimer();

		if (card.isSelected())
			selectedCardCount++;
		else
			selectedCardCount--;

		if (selectedCardCount == FullGameCards.IchiguCardCount) {
			checkIchigu();
			selectedCardCount = 0;
		}
	}

	protected void openExtraCards() {
		((FullGameCardDealer)dealer).openExtraCards();
		applyTimePenalty();
		hint.update();
	}

	private void applyTimePenalty() {
		if (hint.getIchiguCount() == 0)
			return;
		
		getTimer().addSeconds(secondPerPenalty);
		timerText.flash();
	}

	protected void onDeckFinished() {
		prepareResultInfoAndSaveHiscore();
		notifyModeEnd();
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
		selectedCardCount = 0;
		hint.activate();
		super.onStartMode();
	}

	@Override
	protected void onResetMode() {
		getTimer().restart();
		timerText.syncText();
		selectedCardCount = 0;
		super.onResetMode();
	}
	
	@Override
	protected void onEndMode() {
		getTimer().stop();
		hint.deactivate();
		resultScreenButtons.listenInput(true);
		super.onEndMode();
	}

	@Override
	protected boolean onExitMode() {
		if (!super.onExitMode())
			return false;
		getTimer().stop();
		resultScreenButtons.listenInput(false);
		hint.deactivate();
		return true;
	}

	@Override
	protected void onDraw() {
		dealer.drawCards();
		timerText.draw();
		hint.draw();
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
	public void onCardsActivated() {
		hint.update();
	}
}
