package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.info.ToastGameInfo;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class WaitToast extends ToastGameInfo {

	public WaitToast() {
		super();
		setAlpha(1.0f);
		setToastColor(R.colors.ichiguRed);
		setTextColor(R.colors.ichiguWhite);
	}
	
	public void show(float duration) {
		super.show(Game.getLanguageManager().getString(R.strings.wait) + ": ", duration, 0.2f);
	}
	
	@Override
	public void setText(String message) {
		toast.setText(Game.getLanguageManager().getString(R.strings.wait) + ": " + message); 
	}
}