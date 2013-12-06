package com.turpgames.ichigu.model.singlegame.practice;

import com.turpgames.ichigu.model.display.TryAgainToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.singlegame.SingleGameCardDealer;
import com.turpgames.ichigu.model.singlegame.SingleGameMode;
import com.turpgames.ichigu.model.singlegame.SingleGameQuestion;

public class PracticeMode extends SingleGameMode {
	private PracticeModeHint hint;
	private TryAgainToast tryAgain;
	
	public PracticeMode() {
		hint = new PracticeModeHint();
		tryAgain = new TryAgainToast();
		question = new SingleGameQuestion(0.3f, 1.2f);
		
		resetButton.deactivate();
	}
	
	@Override
	public void onCardTapped(Card selectedCard) {
		int score = dealer.getScore();
		if (score == 0)
			tryAgain.show();
		super.onCardTapped(selectedCard);
	}
	
	@Override
	public void onCardsActivated() {
		Card[] ichigu = ((SingleGameCardDealer)dealer).getIchiguCards();
		
		hint.update(ichigu[0], ichigu[1], ichigu[2]);
		hint.activate();
	}
	
	@Override
	public void onCardsDeactivated() {
		hint.deactivate();
	}

	@Override
	protected boolean onExitMode() {
		hint.deactivate();
		isExitConfirmed = true;
		return super.onExitMode();
	}
	
	@Override
	public void deal() {
		tryAgain.hide();
		super.deal();
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
}
