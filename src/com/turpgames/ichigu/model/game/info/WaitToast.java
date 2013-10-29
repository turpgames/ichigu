package com.turpgames.ichigu.model.game.info;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class WaitToast extends ToastGameInfo {

	public WaitToast() {
		super();
		setAlpha(1.0f);
		setToastColor(R.colors.ichiguRed);
		setTextColor(R.colors.ichiguYellow);
	}
	
	public void show(float duration) {
		super.show(Game.getLanguageManager().getString(R.strings.wait) + ": ", duration, 200);
	}
	
	@Override
	public void setText(String message) {
		toast.setText(Game.getLanguageManager().getString(R.strings.wait) + ": " + message); 
	}
}
