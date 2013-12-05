package com.turpgames.ichigu.model.fullgame;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.NoTipToast;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.display.TryAgainToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.game.IchiguBank;
import com.turpgames.ichigu.model.game.IchiguMode;
import com.turpgames.ichigu.model.game.ResultScreenButtons;
import com.turpgames.ichigu.utils.Ichigu;

public abstract class FullGameMode extends IchiguMode implements IResultScreenButtonsListener, IHintListener {
	protected final static float secondPerPenalty = 10f;

	private FullGameHint hint;
	private int selectedCardCount;

	private Text resultInfo;
	private TimerText timerText;

	private TryAgainToast tryAgain;
	private NoTipToast noTip;

	private ResultScreenButtons resultScreenButtons;

	public FullGameMode() {
		resultScreenButtons = new ResultScreenButtons(this);

		hint = new FullGameHint();
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
	protected void setDealer() {
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

	@Override
	protected void openCloseCards(boolean open) {
		dealer.openCloseCards(open);
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

	private void updateHints() {
		getDealer().updateHint(hint);

		boolean thereIsNoIchigu = hint.getIchiguCount() == 0;
		boolean extraCardsAreOpened = getDealer().isExtraCardsOpened();
		boolean hasMoreCardsInDeck = getDealer().getIndex() < Card.CardsInDeck;

		if (thereIsNoIchigu && extraCardsAreOpened) {
			if (hasMoreCardsInDeck) {
				deactivateCards();
				getDealer().dealExtraCards();
				getDealer().openExtraCards();
				activateCards();
			}
			else {
				onDeckFinished();
			}
		}
	}

	protected void flashTimerText() {
		timerText.flash();
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
			IchiguBank.increaseBalance();
			IchiguBank.saveData();
		}
		else {
			tryAgain.show();
			dealer.deselectCards();
			notifyInvalidIchiguSelected();
		}
		return score;
	}
	
	public void cardTapped(Card card) {
		hint.restartNotificationTimer();

		if (!card.isOpened()) {
			card.deselect();
			onOpenExtraCards();
			return;
		}

		if (card.isSelected())
			selectedCardCount++;
		else
			selectedCardCount--;

		if (selectedCardCount == FullGameCards.IchiguCardCount) {
			checkIchigu();
			selectedCardCount = 0;
		}
	}

	protected void onOpenExtraCards() {
		((FullGameCardDealer)dealer).openExtraCards();
		applyTimePenalty();
		updateHints();
	}

	private void applyTimePenalty() {
		if (hint.getIchiguCount() == 0)
			return;
		
		getTimer().addSeconds(secondPerPenalty);
		flashTimerText();
	}

	protected void onDeckFinished() {
		notifyModeEnd();
	}

	@Override
	protected void resetMode() {
		super.resetMode();
		((FullGameCardDealer)dealer).closeExtraCards();
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
		dealer.emptyCards();
		dealer.reset();
		resultScreenButtons.listenInput(false);
		selectedCardCount = 0;
		hint.activate();
		super.onStartMode();
	}

	@Override
	protected void onEndMode() {
		getTimer().stop();
		Ichigu.playSoundTimeUp();
		dealer.emptyCards();
		deactivateCards();
		hint.deactivate();
		resultScreenButtons.listenInput(true);
		super.onEndMode();
	}
	
	protected abstract void prepareResultInfoAndSaveHiscore();

	@Override
	protected boolean onExitMode() {
		if (!super.onExitMode())
			return false;
		getTimer().stop();
		resultScreenButtons.listenInput(false);
		deactivateCards();
		dealer.exit();
		hint.deactivate();
		return true;
	}

	@Override
	protected void onDraw() {
		dealer.drawCards();
		drawTime();
		drawHint();
		super.onDraw();
	}

	private void drawTime() {
		timerText.draw();
	}

	public void drawResult() {
		resultInfo.draw();
		resultScreenButtons.draw();
	}

	private void drawHint() {
		hint.draw();
	}

	protected void setResultText(String resultText) {
		resultInfo.setText(resultText);
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
		updateHints();
	}


	@Override
	public void activateCards() {
		getDealer().activateCards();	
	}

	@Override
	public void deactivateCards() {
		getDealer().deactivateCards();
	}
}
