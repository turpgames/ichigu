package com.turpgames.ichigu.entity;

import java.util.Calendar;
import java.util.Date;

public class Score {
	public static final int ModeMini = 1;
	public static final int ModeStandard = 2;
	public static final int ModeTime = 3;

	private int playerId;
	private int mode;
	private int score;
	private long time;

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal.getTime();
	}
}
