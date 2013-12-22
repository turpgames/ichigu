package com.turpgames.ichigu.server;

import java.util.List;

import com.turpgames.ichigu.server.entity.HiScore;

class IchiguModeHiScoreCache {
	private final static int cacheTimeout = 20;

	private final int mode;

	public IchiguModeHiScoreCache(int mode) {
		this.mode = mode;
	}

	public List<HiScore> getToday() {
		return today.getData();
	}

	public List<HiScore> getLastWeek() {
		return lastWeek.getData();
	}

	public List<HiScore> getLastMonth() {
		return lastMonth.getData();
	}

	public List<HiScore> getAllTime() {
		return allTime.getData();
	}

	private Cache<HiScore> today = new Cache<HiScore>(cacheTimeout) {
		@Override
		protected List<HiScore> load() {
			return HiScore.getHiScoresOfTime(mode, 1);
		}
	};

	private Cache<HiScore> lastWeek = new Cache<HiScore>(cacheTimeout) {
		@Override
		protected List<HiScore> load() {
			return HiScore.getHiScoresOfTime(mode, 7);
		}
	};

	private Cache<HiScore> lastMonth = new Cache<HiScore>(cacheTimeout) {
		@Override
		protected List<HiScore> load() {
			return HiScore.getHiScoresOfTime(mode, 30);
		}
	};

	private Cache<HiScore> allTime = new Cache<HiScore>(cacheTimeout) {
		@Override
		protected List<HiScore> load() {
			return HiScore.getHiScores(mode);
		}
	};
}