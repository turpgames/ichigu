package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class ResultScreenButtons implements IDrawable, ILanguageListener {
	private TextButton backToMenu;
	private TextButton newGame;
	private IResultScreenButtonsListener listener;
	
	public ResultScreenButtons(IResultScreenButtonsListener l) {
		this.listener = l;
		
		backToMenu = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		backToMenu.listenInput(false);
		backToMenu.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				listener.onBackToMenuTapped();
			}
		});
		
		newGame = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		newGame.setDefaultColor(R.colors.ichiguYellow);
		newGame.setTouchedColor(R.colors.ichiguRed);
		newGame.listenInput(false);
		newGame.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				listener.onNewGameTapped();
			}
		});
		
		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
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

	private void setLanguageSensitiveInfo() {
		backToMenu.setText(Ichigu.getString(R.strings.backToMenu));
		backToMenu.getLocation().set((Game.getVirtualWidth() - backToMenu.getWidth()) / 2, 80);
		newGame.setText(Ichigu.getString(R.strings.newGame));
		newGame.getLocation().set((Game.getVirtualWidth() - newGame.getWidth()) / 2, 150);
	}
	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
}
