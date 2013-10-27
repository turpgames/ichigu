package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.util.Color;

public class ToastGameInfo implements IEffectEndListener, Toast.IToastListener {
	private boolean isActive;

	private Toast toast;
	
	public ToastGameInfo() {
		toast = new Toast();
		toast.setListener(this);
	}

	public void show(String message, float duration, float toastDuration) {
		toast.setDuration(toastDuration);
		toast.show(message, duration);
	
		isActive = true;
	}

	private void hintEnd() {
		isActive = false;
	}

	@Override
	public boolean onEffectEnd(Object card) {
		hintEnd();
		return true;
	}

	@Override
	public void onToastHidden(Toast toast) {
		hintEnd();
	}

	public boolean isActive() {
		return isActive;
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