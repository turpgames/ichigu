package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.info.ToastGameInfo;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class WaitToast extends ToastGameInfo {

	public WaitToast() {
		super();
		toast.setHideOnTap(false);
		setAlpha(1.0f);
		setToastColor(R.colors.ichiguRed);
		setTextColor(R.colors.ichiguWhite);
	}
	
	public void show(float duration) {
		super.show(Ichigu.getString(R.strings.wait) + ": ", duration, 0.2f);
	}
	
	@Override
	public void setText(String message) {
		toast.setText(Ichigu.getString(R.strings.wait) + ": " + message); 
	}
}