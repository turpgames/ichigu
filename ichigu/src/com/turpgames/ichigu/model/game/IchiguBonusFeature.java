package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.ichigu.utils.R;

public final class IchiguBonusFeature {
	private static final int singleHintPrice = 10;
	private static final int tripleHintPrice = 25;
	private static final int timerStopPrice = 25;
	
	public static final IchiguBonusFeature singleHint;
	public static final IchiguBonusFeature tripleHint;
	public static final IchiguBonusFeature timerStop;
	
	static {
		singleHint = new IchiguBonusFeature(R.settings.singleHintCount, R.strings.singleHint, R.strings.singleHintInfo, singleHintPrice);
		tripleHint = new IchiguBonusFeature(R.settings.tripleHintCount, R.strings.tripleHint, R.strings.tripleHintInfo, tripleHintPrice);
		timerStop = new IchiguBonusFeature(R.settings.timerStopCount, R.strings.timerStop, R.strings.timerStopInfo, timerStopPrice);
	}

	private final String key;
	private final String name;
	private final String info;
	private final int price;
	private int count;

	private IchiguBonusFeature(String key, String name, String info, int price) {
		this.key = key;
		this.name = name;
		this.info = info;
		this.price = price;
		this.count = Settings.getInteger(key, 0);
	}

	public void bought() {
		count++;
		saveData();
	}
	
	public void used() {
		count--;
		saveData();
	}

	public String getName() {
		return name;
	}

	public String getInfo() {
		return info;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getCount() {
		return count;
	}
	
	private void saveData() {
		Settings.putInteger(key, count);
	}
}
