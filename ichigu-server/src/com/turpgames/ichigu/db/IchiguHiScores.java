package com.turpgames.ichigu.db;

import java.util.List;

import com.turpgames.ichigu.entity.HiScore;
import com.turpgames.ichigu.entity.Score;

public final class IchiguHiScores {
	private IchiguHiScores() {

	}

	private static IchiguModeHiScoreCache miniMode = new IchiguModeHiScoreCache(Score.ModeMini);

	private static IchiguModeHiScoreCache standartMode = new IchiguModeHiScoreCache(Score.ModeStandard);

	private static IchiguModeHiScoreCache timeMode = new IchiguModeHiScoreCache(Score.ModeTime);

	public static List<HiScore> miniModeToday() {
		return miniMode.getToday();
	}

	public static List<HiScore> miniModeLastWeek() {
		return miniMode.getLastWeek();
	}

	public static List<HiScore> miniModeLastMonth() {
		return miniMode.getLastMonth();
	}

	public static List<HiScore> miniModeAllTime() {
		return miniMode.getAllTime();
	}

	public static List<HiScore> standartModeToday() {
		return standartMode.getToday();
	}

	public static List<HiScore> standartModeLastWeek() {
		return standartMode.getLastWeek();
	}

	public static List<HiScore> standartModeLastMonth() {
		return standartMode.getLastMonth();
	}

	public static List<HiScore> standartModeAllTime() {
		return standartMode.getAllTime();
	}

	public static List<HiScore> timeModeToday() {
		return timeMode.getToday();
	}

	public static List<HiScore> timeModeLastWeek() {
		return timeMode.getLastWeek();
	}

	public static List<HiScore> timeModeLastMonth() {
		return timeMode.getLastMonth();
	}

	public static List<HiScore> timeModeAllTime() {
		return timeMode.getAllTime();
	}

	public static List<HiScore> miniModeToday(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScores(Score.ModeMini, 1, playerId);
		return miniModeToday();
	}

	public static List<HiScore> miniModeLastWeek(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScores(Score.ModeMini, 7, playerId);
		return miniModeLastWeek();
	}

	public static List<HiScore> miniModeLastMonth(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScores(Score.ModeMini, 30, playerId);
		return miniModeLastMonth();
	}

	public static List<HiScore> miniModeAllTime(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScoresOfPlayer(Score.ModeMini, playerId);
		return miniModeAllTime();
	}

	public static List<HiScore> standartModeToday(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScores(Score.ModeStandard, 1, playerId);
		return standartModeToday();
	}

	public static List<HiScore> standartModeLastWeek(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScores(Score.ModeStandard, 7, playerId);
		return standartModeLastWeek();
	}

	public static List<HiScore> standartModeLastMonth(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScores(Score.ModeStandard, 30, playerId);
		return standartModeLastMonth();
	}

	public static List<HiScore> standartModeAllTime(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScoresOfPlayer(Score.ModeStandard, playerId);
		return standartModeAllTime();
	}

	public static List<HiScore> timeModeToday(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScores(Score.ModeTime, 1, playerId);
		return timeModeToday();
	}

	public static List<HiScore> timeModeLastWeek(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScores(Score.ModeTime, 7, playerId);
		return timeModeLastWeek();
	}

	public static List<HiScore> timeModeLastMonth(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScores(Score.ModeTime, 30, playerId);
		return timeModeLastMonth();
	}

	public static List<HiScore> timeModeAllTime(int playerId) {
		if (playerId > 0)
			return Db.HiScores.getHiScoresOfPlayer(Score.ModeTime, playerId);
		return timeModeAllTime();
	}
}
