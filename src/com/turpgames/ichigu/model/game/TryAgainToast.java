package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class TryAgainToast extends ToastGameInfo {

	public TryAgainToast() {
		super();
		setAlpha(1.0f);
		setToastColor(R.colors.ichiguRed);
		setTextColor(R.colors.ichiguYellow);
	}
	
	public void show(float duration, float toastDuration) {
		super.show(Game.getResourceManager().getString(R.strings.tryAgain), duration, toastDuration);
	}
}
