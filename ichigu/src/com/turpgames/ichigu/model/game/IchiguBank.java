package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.impl.Manager;
import com.turpgames.ichigu.utils.IchiguSettings;

public final class IchiguBank {
	public static interface IIchiguBankListener {
		void update();
	}
	
	private static final int hintPrice = 20; // 20 ichigus = 1 hint

	private static volatile int ichiguBalance;
	private static volatile int hintCount;

	static {
		ichiguBalance = IchiguSettings.getIchiguBalance();
		hintCount = IchiguSettings.getHintCount();
	}
	
	private static Manager<IchiguBank.IIchiguBankListener> listeners = new Manager<IchiguBank.IIchiguBankListener>() {		
		@Override
		protected void execute(IIchiguBankListener item) {
			item.update();			
		}
	};

	private IchiguBank() {

	}
	
	public static void registerListener(IIchiguBankListener listener) {
		listeners.register(listener);
	}
	
	public static void unregisterListener(IIchiguBankListener listener) {
		listeners.unregister(listener);
	}
	
	private static void notifyListeners() {
		listeners.execute();
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
		
		notifyListeners();
		
		return true;
	}

	public static synchronized void increaseBalance() {
		ichiguBalance++;
		notifyListeners();
	}
	
	public static synchronized void decreaseHintCount() {
		if (hasHint())
			hintCount--;
		notifyListeners();
	}

	public static synchronized void saveData() {
		IchiguSettings.setIchiguBalance(ichiguBalance);
		IchiguSettings.setHintCount(hintCount);
	}
}
