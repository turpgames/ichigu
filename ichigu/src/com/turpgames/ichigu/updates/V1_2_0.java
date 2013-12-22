package com.turpgames.ichigu.updates;

import com.turpgames.framework.v0.IUpdateProcess;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Version;
import com.turpgames.ichigu.model.game.IchiguBank;
import com.turpgames.ichigu.utils.IchiguSettings;
import com.turpgames.ichigu.utils.R;

public class V1_2_0 implements IUpdateProcess {
	private final Version version = new Version("1.2.0");

	@Override
	public Version getVersion() {
		return version;
	}

	@Override
	public void execute() {
		changeSingleHintCountSettingsKey();
		setNewFeatureCounts();		
		giveBonusBalance();
	}

	private void changeSingleHintCountSettingsKey() {
		int singleHintCount = Settings.getInteger("hint-count", 0);		
		Settings.putInteger(R.settings.singleHintCount, singleHintCount + 5);
	}

	private void setNewFeatureCounts() {
		Settings.putInteger(R.settings.tripleHintCount, 5);
		Settings.putInteger(R.settings.timerPauseCount, 5);
	}

	private void giveBonusBalance() {
		int balance = IchiguBank.getBalance();
		IchiguSettings.setIchiguBalance(balance + 100);
	}
}