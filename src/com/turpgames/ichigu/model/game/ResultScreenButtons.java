package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IIchiguButtonListener;
import com.turpgames.ichigu.model.display.IchiguTextButton;
import com.turpgames.ichigu.utils.R;

public class ResultScreenButtons implements IDrawable {
	private IchiguTextButton backToMenu;
	private IchiguTextButton newGame;
	private IResultScreenButtonsListener listener;
	
	public ResultScreenButtons(IResultScreenButtonsListener l) {
		this.listener = l;
		
		backToMenu = new IchiguTextButton();
		backToMenu.setText(Game.getResourceManager().getString(R.strings.backToMenu));
		backToMenu.setDefaultColor(R.colors.ichiguYellow);
		backToMenu.setTouchedColor(R.colors.ichiguRed);
		backToMenu.listenInput(false);
		backToMenu.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				listener.onBackToMenuTapped();
			}
		});
		backToMenu.getLocation().set(
				(Game.getVirtualWidth() - backToMenu.getWidth()) / 2, 80
				);
		
		newGame = new IchiguTextButton();
		newGame.setText(Game.getResourceManager().getString(R.strings.newGame));
		newGame.setDefaultColor(R.colors.ichiguYellow);
		newGame.setTouchedColor(R.colors.ichiguRed);
		newGame.listenInput(false);
		newGame.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				listener.onNewGameTapped();
			}
		});
		newGame.getLocation().set(
				(Game.getVirtualWidth() - newGame.getWidth()) / 2, 150
				);
	}
	
	public void listenInput(boolean listen) {
		backToMenu.listenInput(listen);
		newGame.listenInput(listen);
	}

	@Override
	public void draw() {
		backToMenu.draw();
		newGame.draw();
	}
}
