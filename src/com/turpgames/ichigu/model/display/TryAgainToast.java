package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.info.ToastGameInfo;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class TryAgainToast extends ToastGameInfo {

	public TryAgainToast() {
		super();
		setAlpha(1.0f);
		setToastColor(R.colors.ichiguRed);
		setTextColor(R.colors.ichiguWhite);
	}
	
	public void show() {
		super.show(Game.getLanguageManager().getString(R.strings.tryAgain), 1000, 200);
	}

	@Override
	public void onTap() { }
}
