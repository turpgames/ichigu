package com.turpgames.ichigu.model.display;

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
	private TextButton sendScore;
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

		sendScore = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		sendScore.listenInput(false);
		sendScore.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				listener.onSendScore();
			}
		});

		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
	}

	@Override
	public void draw() {
		backToMenu.draw();
		newGame.draw();
		sendScore.draw();
	}

	public void listenInput(boolean listen) {
		backToMenu.listenInput(listen);
		newGame.listenInput(listen);
		sendScore.listenInput(listen);
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}

	private void setLanguageSensitiveInfo() {
		backToMenu.setText(Ichigu.getString(R.strings.backToMenu));
		backToMenu.getLocation().set((Game.getVirtualWidth() - backToMenu.getWidth()) / 2, 80);
		newGame.setText(Ichigu.getString(R.strings.newGame));
		newGame.getLocation().set((Game.getVirtualWidth() - newGame.getWidth()) / 2, 150);
		sendScore.setText("Send Score");
		sendScore.getLocation().set((Game.getVirtualWidth() - sendScore.getWidth()) / 2, 220);
	}
}
