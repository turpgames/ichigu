package com.turpgames.ichigu.utils;

import com.turpgames.framework.v0.impl.Settings;

public final class IchiguSettings {
	private IchiguSettings() {
		
	}
	
	public static int getHintCount() {
		return Settings.getInteger(R.settings.hintCount, 0);
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
	
//	public static int getHintCount() {
//		return Settings.getInteger(R.settings.country, 0);
//	}
//
//	public static void setHintCount(int hintCount) {
//		Settings.putInteger(R.settings.country, hintCount);
//	}
//
//	
//	public static int getHintCount() {
//		return Settings.getInteger(R.settings.language, 0);
//	}
//
//	public static void setHintCount(int hintCount) {
//		Settings.putInteger(R.settings.language, hintCount);
//	}
//	
//	public static int getHintCount() {
//		return Settings.getInteger(R.settings.music, 0);
//	}
//
//	public static void setHintCount(int hintCount) {
//		Settings.putInteger(R.settings.music, hintCount);
//	}
//	
//	public static int getHintCount() {
//		return Settings.getInteger(R.settings.sound, 0);
//	}
//
//	public static void setHintCount(int hintCount) {
//		Settings.putInteger(R.settings.sound, hintCount);
//	}
//	
//	public static int getHintCount() {
//		return Settings.getInteger(R.settings.vibration, 0);
//	}
//
//	public static void setHintCount(int hintCount) {
//		Settings.putInteger(R.settings.vibration, hintCount);
//	}	
}
