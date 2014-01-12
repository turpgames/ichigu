package com.turpgames.ichigu.db;

import com.turpgames.db.Cache;
import com.turpgames.ichigu.entity.JsonEncoders;
import com.turpgames.ichigu.entity.LeadersBoard;

class IchiguLeadersBoardJsonCache {
	private final static int cacheTimeout = 20;

	private final int mode;

	public IchiguLeadersBoardJsonCache(int mode) {
		this.mode = mode;
	}

	public String getToday() {
		return today.getData();
	}

	public String getLastWeek() {
		return lastWeek.getData();
	}

	public String getLastMonth() {
		return lastMonth.getData();
	}

	public String getAllTime() {
		return allTime.getData();
	}
	
	private static String toJson(LeadersBoard leadersBoard) {
		return JsonEncoders.leadersBoard.encode(leadersBoard);
	}

	private Cache<String> today = new Cache<String>(cacheTimeout) {
		@Override
		protected String load() {
			return toJson(Db.LeadersBoards.getLeadersBoardOfTime(mode, 1));
		}
	};

	private Cache<String> lastWeek = new Cache<String>(cacheTimeout) {
		@Override
		protected String load() {
			return toJson(Db.LeadersBoards.getLeadersBoardOfTime(mode, 7));
		}
	};

	private Cache<String> lastMonth = new Cache<String>(cacheTimeout) {
		@Override
		protected String load() {
			return toJson(Db.LeadersBoards.getLeadersBoardOfTime(mode, 30));
		}
	};

	private Cache<String> allTime = new Cache<String>(cacheTimeout) {
		@Override
		protected String load() {
			return toJson(Db.LeadersBoards.getLeadersBoard(mode));
		}
	};
}