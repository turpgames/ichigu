package com.turpgames.ichigu.model.singlegame.practice;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.Card;
import com.turpgames.ichigu.model.FadingGameInfo;
import com.turpgames.ichigu.model.singlegame.SingleGameCards;
import com.turpgames.ichigu.model.singlegame.SingleGameMode;

public class PracticeMode extends SingleGameMode {
	private PracticeModeHint hint;
	private FadingGameInfo tryAgainInfo;
	
	public PracticeMode() {
		super();
		hint = new PracticeModeHint();
		tryAgainInfo = new FadingGameInfo();
		tryAgainInfo.locate(Text.HAlignCenter, Text.VAlignTop);
		tryAgainInfo.setPadding(0, Game.scale(170));
		tryAgainInfo.setText("Try again...");

		pointsInfo.getLocation().set(0, Game.getVirtualHeight() - Game.scale(pointsInfo.getHeight() + 15));
		pointsInfo.initPointInfos();
	}
	
	@Override
	public void activateCards() {
		super.activateCards();

		Card[] allCards = cards.getAllCards();

		Card card3 = null;
		for (int i = 0; i < SingleGameCards.CardToSelectCount; i++) {
			if (Card.isIchigu(allCards[0], allCards[1], cards.getCardsToSelect(i))) {
				card3 = cards.getCardsToSelect(i);
				break;
			}
		}

		hint.update(allCards[0], allCards[1], card3);
		hint.activate();
	}

	@Override
	public void deactivateCards() {
		hint.deactivate();
		super.deactivateCards();
	}

	@Override
	public boolean exitMode() {
		hint.deactivate();
		return super.exitMode();
	}

	@Override
	protected void wrongCardSelected() {
		tryAgainInfo.show();
		super.wrongCardSelected();
	}
	
	public void drawGame() {
		drawCards();
		hint.draw();
	}

	private void drawCards() {
		Card[] allCards = cards.getAllCards();
		for (int i = 0; i < allCards.length; i++)
			allCards[i].draw();
	}
}
