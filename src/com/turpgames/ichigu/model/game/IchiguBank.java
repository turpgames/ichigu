package com.turpgames.ichigu.model.game;

import com.turpgames.ichigu.utils.IchiguSettings;

public final class IchiguBank {
	private static final int hintPrice = 20; // 20 ichigus = 1 hint

	private static volatile int ichiguBalance;
	private static volatile int hintCount;

	static {
		ichiguBalance = IchiguSettings.getIchiguBalance();
		hintCount = IchiguSettings.getHintCount();
	}

	private IchiguBank() {

	}

	public static synchronized int getBalance() {
		return ichiguBalance;
	}

	public static int getHintCount() {
		return hintCount;
	}
	
	public static int getPointsPerHint() {
		return hintPrice;
	}
	
	public static synchronized boolean canBuyHintWithBalance() {
		return ichiguBalance >= hintPrice;
	}

	public static boolean hasHint() { 		
		return hintCount > 0;
	}
	
	public static synchronized boolean buyHintWithBalance() {
		if (!canBuyHintWithBalance())
			return false;

		ichiguBalance -= hintPrice;
		hintCount++;

		return true;
	}

	public static synchronized void increaseBalance() {
		ichiguBalance++;
	}
	
	public static synchronized void decreaseHintCount() {
		if (hasHint())
			hintCount--;
	}

	public static synchronized void saveData() {
		IchiguSettings.setIchiguBalance(ichiguBalance);
		IchiguSettings.setHintCount(hintCount);
	}
}
