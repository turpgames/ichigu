package com.turpgames.ichigu.db;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.db.Cache;
import com.turpgames.ichigu.entity.JsonEncoders;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.server.ServerConfig;

public final class LeadersBoardJsonCache extends Cache<String> {
	private static Map<String, LeadersBoardJsonCache> cache = new HashMap<String, LeadersBoardJsonCache>();

	private static String toJson(LeadersBoard leadersBoard) {
		return JsonEncoders.leadersBoard.encode(leadersBoard);
	}

	public static String getLeadersBoard(int mode, int days, int playerId) {
		if (mode != Score.ModeMini && mode != Score.ModeStandard && mode != Score.ModeTime &&
				days != Score.Daily && days != Score.Weekly && days != Score.Monthly && days != Score.AllTime) {
			return "[]";
		}
		
		if (playerId > 0) {
			return toJson(Db.LeadersBoards.getLeadersBoard(mode, days, playerId));
		}
		else {
			String key = mode + "|" + days + "|" + playerId;

			LeadersBoardJsonCache jsonCache;
			if (cache.containsKey(key)) {
				jsonCache = cache.get(key);
			}
			else {
				jsonCache = new LeadersBoardJsonCache(mode, days, playerId);
				cache.put(key, jsonCache);
			}
			return jsonCache.getData();
		}
	}

	private final int mode;
	private final int days;
	private final int playerId;

	private LeadersBoardJsonCache(int mode, int days, int playerId) {
		super(ServerConfig.getCacheTimeout());
		this.mode = mode;
		this.days = days;
		this.playerId = playerId;
	}

	@Override
	protected String load() {
		return toJson(Db.LeadersBoards.getLeadersBoard(mode, days, playerId));
	}
}