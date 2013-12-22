package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.impl.Manager;
import com.turpgames.ichigu.utils.IchiguSettings;

public final class IchiguBank {
	public static interface IIchiguBankListener {
		void update();
	}

	private static volatile int balance;

	static {
		balance = IchiguSettings.getIchiguBalance();
	}

	private IchiguBank() {

	}

	private static Manager<IchiguBank.IIchiguBankListener> listeners = new Manager<IchiguBank.IIchiguBankListener>() {
		@Override
		protected void execute(IIchiguBankListener item) {
			item.update();
		}
	};

	private static void notifyListeners() {
		listeners.execute();
	}

	public static void registerListener(IIchiguBankListener listener) {
		listeners.register(listener);
	}

	public static void unregisterListener(IIchiguBankListener listener) {
		listeners.unregister(listener);
	}

	public static boolean buy(BonusFeature feature) {
		if (balance < feature.getPrice())
			return false;
		
		balance -= feature.getPrice();
		feature.bought();
		
		saveData();
		notifyListeners();
		
		return true;
	}

	public static synchronized int getBalance() {
		return balance;
	}

	public static synchronized void increaseBalance() {
		balance++;
		saveData();
		notifyListeners();
	}

	private static synchronized void saveData() {
		IchiguSettings.setIchiguBalance(balance);
	}
}
