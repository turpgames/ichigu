package com.turpgames.ichigu.db;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.db.Cache;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.server.ServerConfig;

public final class LeadersBoardCache extends Cache<LeadersBoard> {
	private static Map<String, LeadersBoardCache> cache = new HashMap<String, LeadersBoardCache>();

	public static LeadersBoard getLeadersBoard(int mode, int days, int playerId) {
		if (mode != Score.ModeMini && mode != Score.ModeStandard && mode != Score.ModeTime &&
				days != Score.Daily && days != Score.Weekly && days != Score.Monthly && days != Score.AllTime) {
			return new LeadersBoard();
		}
		
		if (playerId > 0) {
			return Db.LeadersBoards.getLeadersBoard(mode, days, playerId, 10);
		}
		else {
			String key = mode + "|" + days;

			LeadersBoardCache jsonCache;
			if (cache.containsKey(key)) {
				jsonCache = cache.get(key);
			}
			else {
				jsonCache = new LeadersBoardCache(mode, days, playerId);
				cache.put(key, jsonCache);
			}
			return jsonCache.getData();
		}
	}

	private final int mode;
	private final int days;
	private final int playerId;

	private LeadersBoardCache(int mode, int days, int playerId) {
		super(ServerConfig.getCacheTimeout());
		this.mode = mode;
		this.days = days;
		this.playerId = playerId;
	}

	@Override
	protected LeadersBoard load() {
		return Db.LeadersBoards.getLeadersBoard(mode, days, playerId, 10);
	}
}