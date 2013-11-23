package com.turpgames.ichigu.utils;

import com.turpgames.framework.v0.impl.Settings;

public final class IchiguSettings {
	private IchiguSettings() {
		
	}
	
	public static int getHintCount() {
		return Settings.getInteger(R.settings.hintCount, 10);
	}

	public static void setHintCount(int hintCount) {
		Settings.putInteger(R.settings.hintCount, hintCount);
	}
	
	public static int getIchiguBalance() {
		return Settings.getInteger(R.settings.ichiguBalance, 0);
	}
	
	public static void setIchiguBalance(int balance) {
		Settings.putInteger(R.settings.ichiguBalance, balance);
	}
}
