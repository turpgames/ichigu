package com.turpgames.ichigu.model.fullgame;

import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.info.GameInfo;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.DisplayTimer;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.model.display.ScoreToast;
import com.turpgames.ichigu.model.display.TryAgainToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.game.IchiguMode;
import com.turpgames.ichigu.model.game.ResultScreenButtons;
import com.turpgames.ichigu.utils.IchiguResources;
import com.turpgames.ichigu.utils.R;

public abstract class FullGameMode extends IchiguMode implements IResultScreenButtonsListener {
	private static final float buttonSize = Game.scale(R.ui.imageButtonWidth);
	
	protected FullGameCards cards;
	protected FullGameHint hint;
	protected int selectedCardCount;

	private ImageButton resetButton;
	private Dialog resetConfirmDialog;
	
	protected int deckCount;	
	protected GameInfo remaingCardInfo;
	
	protected Timer timer;
	protected DisplayTimer timeInfo;
	protected int modeCompleteTime;

	protected ScoreToast pointsInfo;
	protected boolean pointsInfoActive;
	
	private boolean areExtraCardsOpened;
	
	private TryAgainToast tryAgain;
	
	protected ResultScreenButtons resultScreenButtons;

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

	public FullGameMode() {
		cards = new FullGameCards();
		dealer = new FullGameCardDealer(cards);
		resultScreenButtons = new ResultScreenButtons(this);
		
		resetButton = new ImageButton(buttonSize, buttonSize, R.game.textures.refresh, R.colors.buttonDefault, R.colors.buttonTouched);
		resetButton.getLocation().set(10,  Game.viewportToScreenY(30));
		resetButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				confirmResetMode();
			}
		});

		resetConfirmDialog = new IchiguDialog();
		resetConfirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onResetConfirmed(R.strings.yes.equals(id));
			}
		});

		remaingCardInfo = new GameInfo();
		remaingCardInfo.locate(Text.HAlignCenter, Text.VAlignBottom);
		remaingCardInfo.setPadding(0, 55);
		
		timeInfo = new DisplayTimer(R.colors.ichiguRed, 5, 30);
		timeInfo.locate(Text.HAlignRight, Text.VAlignTop);
		timeInfo.setPadding(0, 110);
		
		timer = new Timer();
		timer.setInterval(1);
		timer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				int elapsed = (int) timer.getTotalElapsedTime();
				timeInfo.setTimeText(elapsed);
			}
		});

		hint = new FullGameHint();
		
		pointsInfo = new ScoreToast();
		pointsInfoActive = true;
		
		tryAgain = new TryAgainToast();
	}

	private void onResetConfirmed(boolean reset) {
		if (reset) {
			resetMode();
		}
		else {
			openCloseCards(true);
		}
	}

	private void confirmResetMode() {
		resetConfirmDialog.open(Game.getLanguageManager().getString(R.strings.resetConfirm));
		openCloseCards(false);
	}

	private void resetMode() {
		endMode();
		startMode();
		deal();
		closeExtraCards();
	}

	public void pause() {
		resetConfirmDialog.close();
		resetButton.listenInput(false);
	}
	
	public void resume() {
		resetButton.listenInput(true);
		openCloseCards(true);
	}
	
	public FullGameCards getCards() {
		return cards;
	}

	protected IFullGameModeListener getModeListener() {
		return (IFullGameModeListener) super.modeListener;
	}

	protected FullGameCardDealer getDealer() {
		return (FullGameCardDealer) dealer;
	}

	protected void notifyModeEnd() {
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	protected void notifyNewGame() {
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	public void cardTapped(Card card) {
		hint.restartNotificationTimer();

		if (!card.isOpened()) {
			card.deselect();
			openExtraCards();
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

	protected int checkIchigu() {
		int score = cards.getScore();
		if (score > 0) {
//			ichigusFound++;
			notifyIchiguFound();
			if (pointsInfoActive)
				pointsInfo.show(score);
			areExtraCardsOpened = false;
		}
		else {
			tryAgain.show();
			notifyInvalidIchiguSelected();
		}
		return score;
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

	protected void openExtraCards() {
		areExtraCardsOpened = true;
		for (int i = 0; i < FullGameCards.ExtraCardCount; i++)
			cards.getExtraCard(i).open();
	}
	
	protected void closeExtraCards() {
		areExtraCardsOpened = false;
		for (int i = 0; i < FullGameCards.ExtraCardCount; i++)
			cards.getExtraCard(i).close();
	}
	
	protected void updateHints() {
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

	public void deckFinished() {
		notifyModeEnd();
	}
	
	public void startMode() {
		cards.empty();
		dealer.reset();
		resultScreenButtons.listenInput(false);
		deckCount = 1;
		resetButton.listenInput(true);
		selectedCardCount = 0;
		timer.start();
		hint.activate();
		timeInfo.setTimeText(0);
	}

	public void endMode() {
		IchiguResources.playSoundTimeUp();
		modeCompleteTime = (int) timer.getTotalElapsedTime();
		timer.stop();
		cards.empty();
		deactivateCards();
		resetConfirmDialog.close();
		hint.deactivate();
		resultScreenButtons.listenInput(true);
	}

	public boolean exitMode() {
		resultScreenButtons.listenInput(false);
		deactivateCards();
		cards.empty();
		timer.stop();
		hint.deactivate();
		resetConfirmDialog.close();
		resetButton.listenInput(false);
		return true;
	}

	public void drawGame() {
		drawTime();
		drawCards();
		drawRemainingCards();
		drawResetButton();
	}

	protected void drawResetButton() {
		resetButton.draw();
	}
	
	public abstract void drawResult();

	protected void drawCards() {
		cards.draw();
	}

	protected void drawRemainingCards() {
		remaingCardInfo.setText(getDealer().getIndex() + "/" + Card.CardsInDeck);
//		remaingCardInfo.setText(getDealer().getIndex() + "/" + Card.CardsInDeck + " | Deck " + deckCount);
		remaingCardInfo.draw();
	}

	protected void drawTime() {
		timeInfo.draw();
	}
}
