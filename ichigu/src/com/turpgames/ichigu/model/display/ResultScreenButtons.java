package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.social.Facebook;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class ResultScreenButtons implements IDrawable, ILanguageListener {
	private TextButton backToMenu;
	private TextButton newGame;
	private TextButton sendScore;
	private IResultScreenButtonsListener listener;

	public ResultScreenButtons(IResultScreenButtonsListener l) {
		this.listener = l;

		backToMenu = new TextButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
		backToMenu.listenInput(false);
		backToMenu.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				listener.onBackToMenuTapped();
			}
		});

		newGame = new TextButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
		newGame.listenInput(false);
		newGame.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				listener.onNewGameTapped();
			}
		});

		sendScore = new TextButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
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
	
	public void activateScoreButton() {
		sendScore.activate();
	}
	
	public void deactivateScoreButton() {
		sendScore.deactivate();
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
	
	public void updateButtons() {
		setLanguageSensitiveInfo();
	}

	private void setLanguageSensitiveInfo() {
		backToMenu.setText(Ichigu.getString(R.strings.backToMenu));
		backToMenu.getLocation().set((Game.getVirtualWidth() - backToMenu.getWidth()) / 2, 80);
		newGame.setText(Ichigu.getString(R.strings.newGame));
		newGame.getLocation().set((Game.getVirtualWidth() - newGame.getWidth()) / 2, 150);

		if (Facebook.isLoggedIn())
			sendScore.setText(Ichigu.getString(R.strings.shareScore));
		else
			sendScore.setText(Ichigu.getString(R.strings.sendScore));
		
		sendScore.getLocation().set((Game.getVirtualWidth() - newGame.getWidth()) / 2, 220);
	}
}
