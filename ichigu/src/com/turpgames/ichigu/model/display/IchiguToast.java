package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class IchiguToast {
	private final Toast toast;

	private final static IchiguToast instance = new IchiguToast();

	private IchiguToast() {
		toast = new Toast();
	}

	public static void showError(String resourceId) {
		show(resourceId, R.colors.ichiguRed);
	}

	public static void showInfo(String resourceId) {
		show(resourceId, R.colors.ichiguBlue);
	}

	public static void show(String resourceId, Color backColor) {
		showMessage(Ichigu.getString(resourceId), backColor);
	}

	public static void showMessage(String message, Color backColor) {
		showMessage(message, calculateFontScale(message), backColor, R.colors.ichiguWhite, true, 1.0f,
				Game.scale(30f), Game.scale(30f), calculateDuration(message), R.durations.toastSlideDuration);
	}

	public static void showMessage(String message, float fontScale, Color backColor, Color foreColor,
			boolean hideOnTap, float alpha, float padX, float padY, float displayDuration, float slideDuration) {
		preapreToast()
				.setAlpha(alpha)
				.setDisplayDuration(displayDuration)
				.setFontScale(fontScale)
				.setHideOnTap(hideOnTap)
				.setMessage(message)
				.setPadding(padX, padY)
				.setSlideDuration(slideDuration)
				.setTextColor(foreColor)
				.setBackColor(backColor)
				.show();
	}

	private static void reset() {
		instance.setAlpha(1)
				.setDisplayDuration(3f)
				.setFontScale(R.fontSize.medium)
				.setHideOnTap(true)
				.setMessage("")
				.setPadding(Game.scale(30f), Game.scale(30f))
				.setSlideDuration(R.durations.toastSlideDuration)
				.setTextColor(R.colors.ichiguWhite)
				.setBackColor(R.colors.ichiguBlue);
	}

	public static IchiguToast preapreToast() {
		hide();
		reset();
		return instance;
	}

	public IchiguToast setFontScale(float fontScale) {
		toast.setFontScale(fontScale);
		return this;
	}

	public IchiguToast setBackColor(Color backColor) {
		toast.setBackColor(backColor);
		return this;
	}

	public IchiguToast setTextColor(Color foreColor) {
		toast.setTextColor(foreColor);
		return this;
	}

	public IchiguToast setHideOnTap(boolean hideOnTap) {
		toast.setHideOnTap(hideOnTap);
		return this;
	}

	public IchiguToast setAlpha(float alpha) {
		toast.setAlpha(alpha);
		return this;
	}

	public IchiguToast setPadding(float padX, float padY) {
		toast.setPadX(padX);
		toast.setPadY(padY);
		return this;
	}

	public IchiguToast setSlideDuration(float slideDuration) {
		toast.setSlideDuration(slideDuration);
		return this;
	}

	public IchiguToast setDisplayDuration(float toastDuration) {
		toast.setDisplayDuration(toastDuration);
		return this;
	}

	public IchiguToast setMessage(String message) {
		toast.setMessage(message);
		return this;
	}

	public void show() {
		toast.show();
	}
	
	public static void updateMessage(String message) {
		instance.toast.setMessage(message);
	}

	public static void hide() {
		instance.toast.hide();
	}

	private static float calculateDuration(String message) {
		return message.split(" ").length * R.durations.toastDisplayDurationPerWord + R.durations.toastDisplayDurationBuffer;
	}

	private static float calculateFontScale(String message) {
		if (message.length() < 20) {
			return R.fontSize.large;
		}
		if (message.length() < 40) {
			return R.fontSize.large;
		}
		return R.fontSize.small;
	}
}
