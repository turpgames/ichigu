package com.turpgames.ichigu.utils;

import java.util.Date;

import com.turpgames.ichigu.entity.LeadersBoard;

public class LeadersBoardCacheItem {
	private LeadersBoard leadersBoard;
	private Date lastLoadDate;

	public LeadersBoard getLeadersBoard() {
		return leadersBoard;
	}

	public void setLeadersBoard(LeadersBoard leadersBoard) {
		this.leadersBoard = leadersBoard;
	}

	public Date getLastLoadDate() {
		return lastLoadDate;
	}

	public void setLastLoadDate(Date lastLoadDate) {
		this.lastLoadDate = lastLoadDate;
	}
}
