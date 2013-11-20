package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.info.ToastGameInfo;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class TryAgainToast extends ToastGameInfo {

	public TryAgainToast() {
		super();
		setAlpha(1.0f);
		setToastColor(R.colors.ichiguRed);
		setTextColor(R.colors.ichiguWhite);
	}
	
	public void show() {
		super.show(Ichigu.getString(R.strings.tryAgain), 1f, 0.2f);
	}
}