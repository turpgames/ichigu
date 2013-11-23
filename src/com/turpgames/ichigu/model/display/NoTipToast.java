package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.info.ToastGameInfo;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class NoTipToast extends ToastGameInfo {
	public NoTipToast() {
		setAlpha(1.0f);
		setToastColor(R.colors.ichiguRed);
		setTextColor(R.colors.ichiguWhite);
	}
	
	public void show() {
		super.show(Ichigu.getString(R.strings.noTip), 1f, 0.2f);
	}
}
