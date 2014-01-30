package com.turpgames.ichigu.utils;

import java.util.ArrayList;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.utils.Util;

public final class IchiguSettings {
	public static int getHintCount() {
		return Settings.getInteger(R.settings.singleHintCount, 0);
	}
	
	public static int getIchiguBalance() {
		return Settings.getInteger(R.settings.ichiguBalance, 0);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Score> getScoresToSend() {
		try {
			String encoded = Settings.getString(R.settings.scoresToSend, "");
			byte[] serialized = Util.Strings.fromBase64String(encoded);
			return (ArrayList<Score>) Util.IO.deserialize(serialized);
		} catch (Throwable t) {
			t.printStackTrace();
			return new ArrayList<Score>();
		}
	}

	public static void setSingleHintCount(int hintCount) {
		Settings.putInteger(R.settings.singleHintCount, hintCount);
	}
	
	public static void setIchiguBalance(int balance) {
		Settings.putInteger(R.settings.ichiguBalance, balance);
	}

	public static void setScoresToSend(ArrayList<Score> scoresToSend) {
		byte[] serialized = Util.IO.serialize(scoresToSend);
		String encoded = Util.Strings.toBase64String(serialized);
		Settings.putString(R.settings.scoresToSend, encoded);
	}
	
	private IchiguSettings() {
		
	}
}
