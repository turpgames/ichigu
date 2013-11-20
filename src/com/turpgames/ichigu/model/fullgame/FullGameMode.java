package com.turpgames.ichigu.model.fullgame;

import com.turpgames.framework.v0.component.info.GameInfo;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.display.TryAgainToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.game.IchiguBank;
import com.turpgames.ichigu.model.game.IchiguMode;
import com.turpgames.ichigu.model.game.ResultScreenButtons;
import com.turpgames.ichigu.utils.Ichigu;

public abstract class FullGameMode extends IchiguMode implements IResultScreenButtonsListener, IHintListener {
	protected final static float secondsPerHint = 10f;

	private FullGameHint hint;
	private FullGameCards cards;
	private int selectedCardCount;

	private Text resultInfo;
	private TimerText timerText;

	private GameInfo remaingCardInfo;

	private boolean areExtraCardsOpened;

	private TryAgainToast tryAgain;

	private ResultScreenButtons resultScreenButtons;

	public FullGameMode() {
		cards = new FullGameCards();
		dealer = new FullGameCardDealer(cards);
		resultScreenButtons = new ResultScreenButtons(this);

		remaingCardInfo = new GameInfo();
		remaingCardInfo.setAlignment(Text.HAlignCenter, Text.VAlignBottom);
		remaingCardInfo.setPadding(0, 55);

		hint = new FullGameHint();
		hint.getLocation().set(Game.getScreenWidth() - hint.getWidth() - 10, Game.viewportToScreenY(30));
		hint.activate();
		hint.setHintListener(this);

		tryAgain = new TryAgainToast();

		timerText = new TimerText(getTimer());
		timerText.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		timerText.setPadding(Game.getVirtualWidth() - 120, 100);

		resultInfo = new Text();
		resultInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		resultInfo.setPadding(0, 250);
	}

	protected abstract Timer getTimer();

	@Override
	protected void pauseTimer() {
		getTimer().pause();
	}

	@Override
	protected void startTimer() {
		getTimer().start();
	}
	
	@Override
	protected void openCloseCards(boolean open) {
		for (int i = 0; i < FullGameCards.ActiveCardCount; i++) {
			if (!cards.isActiveCardEmpty(i)) {
				if (open)
					cards.getActiveCard(i).open();
				else
					cards.getActiveCard(i).close();
			}
		}

		if (areExtraCardsOpened) {
			for (int i = 0; i < FullGameCards.ExtraCardCount; i++) {
				if (!cards.isExtraCardEmpty(i)) {
					if (open)
						cards.getExtraCard(i).open();
					else
						cards.getExtraCard(i).close();

				}
			}
		}
	}

	private FullGameCardDealer getDealer() {
		return (FullGameCardDealer) dealer;
	}

	private IFullGameModeListener getModeListener() {
		return (IFullGameModeListener) super.modeListener;
	}

	private void notifyNewGame() {
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	private void closeExtraCards() {
		areExtraCardsOpened = false;
		for (int i = 0; i < FullGameCards.ExtraCardCount; i++)
			cards.getExtraCard(i).close();
	}

	private void updateHints() {
		cards.updateHint(hint);

		boolean thereIsNoIchigu = hint.getIchiguCount() == 0;
		boolean extraCardsAreOpened = cards.isExtraCardEmpty(0) || cards.getExtraCard(0).isOpened();
		boolean hasMoreCardsInDeck = getDealer().getIndex() < Card.CardsInDeck;

		if (thereIsNoIchigu && extraCardsAreOpened) {
			if (hasMoreCardsInDeck) {
				deactivateCards();
				getDealer().dealExtraCards();
				openExtraCards();
				activateCards();
			}
			else {
				deckFinished();
			}
		}
	}

	protected void openExtraCards() {
		areExtraCardsOpened = true;
		for (int i = 0; i < FullGameCards.ExtraCardCount; i++)
			cards.getExtraCard(i).open();

		if (getAvailableIchiguCount() != 0)
			getTimer().addSeconds(secondsPerHint);
	}

	protected int getAvailableIchiguCount() {
		return hint.getIchiguCount();
	}

	private void flashTimerText() {
		timerText.flash();
	}

	protected void notifyModeEnd() {
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	protected int checkIchigu() {
		int score = cards.getScore();
		if (score > 0) {
			notifyIchiguFound();
			areExtraCardsOpened = false;
			IchiguBank.increaseBalance();
		}
		else {
			tryAgain.show();
			notifyInvalidIchiguSelected();
		}
		return score;
	}

	public FullGameCards getCards() {
		return cards;
	}

	public void cardTapped(Card card) {
		hint.restartNotificationTimer();

		if (!card.isOpened()) {
			card.deselect();
			openExtraCards();
			flashTimerText();
			updateHints();
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

	public void activateCards() {
		for (int i = 0; i < FullGameCards.TotalCardsOnTable; i++) {
			if (!cards.isEmpty(i)) {
				cards.getCard(i).activate(modeListener);
			}
		}
		updateHints();
	}

	public void deactivateCards() {
		for (int i = 0; i < FullGameCards.TotalCardsOnTable; i++) {
			if (!cards.isEmpty(i)) {
				cards.getCard(i).deactivate();
			}
		}
	}

	public void deselectCards() {
		cards.deselectCards();
	}

	private void deckFinished() {
		notifyModeEnd();
	}

	@Override
	protected void resetMode() {
		super.resetMode();
		closeExtraCards();
	}

	@Override
	public void onInsufficientHint() {
		// TODO: Hint alma menusu aç
	}

	@Override
	protected void onStartMode() {
		getTimer().restart();
		timerText.syncText();
		cards.empty();
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
		cards.empty();
		deactivateCards();
		hint.deactivate();
		resultScreenButtons.listenInput(true);
		super.onEndMode();
	}

	@Override
	protected boolean onExitMode() {
		if (! super.onExitMode()) 
			return false;
		getTimer().stop();
		resultScreenButtons.listenInput(false);
		deactivateCards();
		cards.empty();
		hint.deactivate();
		return true;
	}

	@Override
	protected void onDraw() {
		drawCards();
		drawRemainingCards();
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

	private void drawCards() {
		cards.draw();
	}

	private void drawHint() {
		hint.draw();
	}

	private void drawRemainingCards() {
		remaingCardInfo.setText(getDealer().getIndex() + "/" + Card.CardsInDeck);
		remaingCardInfo.draw();
	}

	protected void setResultText(String resultText) {
		resultInfo.setText(resultText);
	}

	@Override
	public void onHintShown() {

	}

	@Override
	public void onBackToMenuTapped() {
		getModeListener().onExitConfirmed();
	}

	@Override
	public void onNewGameTapped() {
		notifyNewGame();
	}
}
