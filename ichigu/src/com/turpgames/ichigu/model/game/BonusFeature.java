package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.ichigu.utils.R;

public final class BonusFeature {
	public static interface IListener {
		void onFeatureUpdated(BonusFeature feature);
	}
	
	public static final BonusFeature singleHint;
	public static final BonusFeature tripleHint;
	public static final BonusFeature timerPause;

	static {
		singleHint = new BonusFeature(R.settings.singleHintCount, R.strings.singleHint, R.strings.singleHintInfo, R.prices.singleHintPrice);
		tripleHint = new BonusFeature(R.settings.tripleHintCount, R.strings.tripleHint, R.strings.tripleHintInfo, R.prices.tripleHintPrice);
		timerPause = new BonusFeature(R.settings.timerPauseCount, R.strings.pauseTimer, R.strings.pauseTimerInfo, R.prices.timerPausePrice);
	}

	private final String key;
	private final String name;
	private final String info;
	private final int price;
	private int count;

	private IListener listener;

	private BonusFeature(String key, String name, String info, int price) {
		this.key = key;
		this.name = name;
		this.info = info;
		this.price = price;
		this.count = Settings.getInteger(key, 0);
	}

	private void notifyListener() {
		if (listener != null)
			listener.onFeatureUpdated(this);
	}

	public void setListener(IListener listener) {
		this.listener = listener;
	}

	public void bought() {
		count++;
		saveData();
		notifyListener();
	}

	public void used() {
		count--;
		saveData();
		notifyListener();
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
