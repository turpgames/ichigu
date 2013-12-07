package com.turpgames.ichigu.model.singlegame;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IchiguMode;
import com.turpgames.ichigu.utils.R;

public abstract class SingleGameMode extends IchiguMode {
	private static final int dividerHeight = 10;
	private static final int dividerWidth = 420;
	
	protected SingleGameQuestion question;

	@Override
	protected void initDealer() {
		dealer = new SingleGameCardDealer();
	}

	@Override
	protected void onDraw() {
		dealer.drawCards();
		question.draw();
		
		ShapeDrawer.drawRect(Game.getVirtualWidth() / 2 - Card.Width * 1.5f - 60, (Game.getVirtualHeight() - dividerHeight) / 2 - 17,
				dividerWidth, dividerHeight, R.colors.ichiguYellow, true, false);
		
		super.onDraw();
	}

	@Override
	public void ichiguFound() {
		question.startCorrectEffect();
	}

	@Override
	public void invalidIchiguSelected() {
		question.startIncorrectEffect();
	}
}