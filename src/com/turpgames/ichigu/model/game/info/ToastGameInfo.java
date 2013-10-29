package com.turpgames.ichigu.model.game.info;

import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.util.Color;

public abstract class ToastGameInfo implements Toast.IToastListener {
	protected Toast toast;
	
	public ToastGameInfo() {
		toast = new Toast();
		toast.setListener(this);
	}

	/***
	 * 
	 * @param message
	 * @param duration in milliseconds
	 * @param toastDuration in milliseconds
	 */
	public void show(String message, float duration, float toastDuration) {
		toast.setDuration(toastDuration);
		toast.show(message, duration);
	}

	@Override
	public void onToastHidden(Toast toast) {

	}

	public void setTextColor(Color color) {
		toast.setTextColor(color);
	}

	public void setToastColor(Color color) {
		toast.setToastColor(color);
	}
	
	public void setText(String message) {
		toast.setText(message);
	}

	public void setAlpha(float f) {
		toast.setAlpha(f);
	}
}