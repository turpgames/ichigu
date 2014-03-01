package com.turpgames.ichigu.controller.sudoku;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.utils.Ichigu;

public class SudokuWaitingState extends SudokuState {
	public SudokuWaitingState(SudokuController controller) {
		super(controller);
	}

	@Override
	public void onCardTapped(Card card) {
		Game.vibrate(50);
		Ichigu.playSoundFlip();
		model.cardTapped(card);
	}
}