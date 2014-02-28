package com.turpgames.ichigu.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.utils.Util;

public final class LeadersBoardCache {
	private LeadersBoardCache() {

	}

	private final static String settingsKeyPrefix = "LeadersBoardCache_"; 
	private final static long timeout = 20 * 60 * 1000; // 20 minutes
	private final static Map<String, LeadersBoardCacheItem> cache = new HashMap<String, LeadersBoardCacheItem>();
	
	public static LeadersBoard getLeadersBoard(int mode, int days, int whose, boolean ignoreTimeout) {
		String key = prepareKey(mode, days, whose);
		LeadersBoardCacheItem cacheItem = cache.get(key);

		if (cacheItem == null) {
			cacheItem = readFromSettings(key);
			if (cacheItem == null)
				return null;
			cache.put(key, cacheItem);
		}

		Date now = new Date();
		if (!ignoreTimeout && now.getTime() - cacheItem.getLastLoadDate().getTime() > timeout)
			return null;

		return cacheItem.getLeadersBoard();
	}

	public static void putLeadersBoard(int mode, int days, int whose, LeadersBoard leadersBoard) {
		String key = prepareKey(mode, days, whose);

		LeadersBoardCacheItem cacheItem = new LeadersBoardCacheItem();
		cacheItem.setLeadersBoard(leadersBoard);
		cacheItem.setLastLoadDate(new Date());

		cache.put(key, cacheItem);
		
		writeToSettings(key, cacheItem);
	}

	private static void writeToSettings(String key, LeadersBoardCacheItem cacheItem) {
		String settingsKey = settingsKeyPrefix + key;
		byte[] serialized = Util.IO.serialize(cacheItem);
		String base64SettingsValue = Util.Strings.toBase64String(serialized);
		Game.getSettings().putString(settingsKey, base64SettingsValue);
	}

	private static LeadersBoardCacheItem readFromSettings(String key) {
		String settingsKey = settingsKeyPrefix + key;
		String base64SettingsValue = Game.getSettings().getString(settingsKey, null);
		if (base64SettingsValue == null)
			return null;

		byte[] serialized = Util.Strings.fromBase64String(base64SettingsValue);
		LeadersBoardCacheItem cacheItem = (LeadersBoardCacheItem)Util.IO.deserialize(serialized);
		return cacheItem;
	}

	private static String prepareKey(int mode, int days, int whose) {
		return mode + "" + days + "" + whose;
	}
}
