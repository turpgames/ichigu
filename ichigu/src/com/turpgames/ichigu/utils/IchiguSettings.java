package com.turpgames.ichigu.utils;

import com.turpgames.framework.v0.impl.Settings;

public final class IchiguSettings {
	public static int getHintCount() {
		return Settings.getInteger(R.settings.singleHintCount, 10);
	}
	
	public static int getIchiguBalance() {
		return Settings.getInteger(R.settings.ichiguBalance, 0);
	}

	public static void setSingleHintCount(int hintCount) {
		Settings.putInteger(R.settings.singleHintCount, hintCount);
	}
	
	public static void setIchiguBalance(int balance) {
		Settings.putInteger(R.settings.ichiguBalance, balance);
	}
	
	private IchiguSettings() {
		
	}
}
