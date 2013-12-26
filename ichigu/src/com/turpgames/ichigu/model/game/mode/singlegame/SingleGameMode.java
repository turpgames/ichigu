package com.turpgames.ichigu.model.game.mode.singlegame;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.ichigu.model.display.SingleGameQuestion;
import com.turpgames.ichigu.model.game.mode.RegularMode;
import com.turpgames.ichigu.model.game.table.SingleGameTable;
import com.turpgames.ichigu.model.game.table.Table;
import com.turpgames.ichigu.utils.R;

public abstract class SingleGameMode extends RegularMode {
	
	protected SingleGameQuestion question;

	@Override
	public void concreteIchiguFound() {
		question.startCorrectEffect();
	}
	
	@Override
	public void concreteInvalidIchiguSelected() {
		question.startIncorrectEffect();
	}

	public void drawResultScreen() {
		
	}

	@Override
	protected Table createTable() {
		return new SingleGameTable();
	}
	
	@Override
	protected void onDraw() {
		table.draw();
		question.draw();
		
		ShapeDrawer.drawRect(
				Game.getVirtualWidth() / 2 - R.sizes.cardWidth * 1.5f - 60,
				(Game.getVirtualHeight() - R.sizes.singleModeDividerHeight) / 2 - 17,
				R.sizes.singleModeDividerWidth, 
				R.sizes.singleModeDividerHeight, 
				R.colors.ichiguYellow, 
				true, 
				false);
		
		super.onDraw();
	}
}