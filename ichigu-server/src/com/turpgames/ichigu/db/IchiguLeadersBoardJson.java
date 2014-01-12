package com.turpgames.ichigu.db;

import com.turpgames.ichigu.entity.JsonEncoders;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.entity.LeadersBoard;

public final class IchiguLeadersBoardJson {
	private IchiguLeadersBoardJson() {

	}

	private static IchiguLeadersBoardJsonCache miniMode = new IchiguLeadersBoardJsonCache(Score.ModeMini);

	private static IchiguLeadersBoardJsonCache standartMode = new IchiguLeadersBoardJsonCache(Score.ModeStandard);

	private static IchiguLeadersBoardJsonCache timeMode = new IchiguLeadersBoardJsonCache(Score.ModeTime);

	public static String miniModeToday() {
		return miniMode.getToday();
	}

	public static String miniModeLastWeek() {
		return miniMode.getLastWeek();
	}

	public static String miniModeLastMonth() {
		return miniMode.getLastMonth();
	}

	public static String miniModeAllTime() {
		return miniMode.getAllTime();
	}

	public static String standartModeToday() {
		return standartMode.getToday();
	}

	public static String standartModeLastWeek() {
		return standartMode.getLastWeek();
	}

	public static String standartModeLastMonth() {
		return standartMode.getLastMonth();
	}

	public static String standartModeAllTime() {
		return standartMode.getAllTime();
	}

	public static String timeModeToday() {
		return timeMode.getToday();
	}

	public static String timeModeLastWeek() {
		return timeMode.getLastWeek();
	}

	public static String timeModeLastMonth() {
		return timeMode.getLastMonth();
	}

	public static String timeModeAllTime() {
		return timeMode.getAllTime();
	}
	
	private static String toJson(LeadersBoard leadersBoard) {
		return JsonEncoders.leadersBoard.encode(leadersBoard);
	}

	public static String miniModeToday(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getLeadersBoard(Score.ModeMini, 1, playerId));
		return miniModeToday();
	}

	public static String miniModeLastWeek(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getLeadersBoard(Score.ModeMini, 7, playerId));
		return miniModeLastWeek();
	}

	public static String miniModeLastMonth(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getLeadersBoard(Score.ModeMini, 30, playerId));
		return miniModeLastMonth();
	}

	public static String miniModeAllTime(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getHiScoresOfPlayer(Score.ModeMini, playerId));
		return miniModeAllTime();
	}

	public static String standartModeToday(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getLeadersBoard(Score.ModeStandard, 1, playerId));
		return standartModeToday();
	}

	public static String standartModeLastWeek(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getLeadersBoard(Score.ModeStandard, 7, playerId));
		return standartModeLastWeek();
	}

	public static String standartModeLastMonth(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getLeadersBoard(Score.ModeStandard, 30, playerId));
		return standartModeLastMonth();
	}

	public static String standartModeAllTime(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getHiScoresOfPlayer(Score.ModeStandard, playerId));
		return standartModeAllTime();
	}

	public static String timeModeToday(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getLeadersBoard(Score.ModeTime, 1, playerId));
		return timeModeToday();
	}

	public static String timeModeLastWeek(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getLeadersBoard(Score.ModeTime, 7, playerId));
		return timeModeLastWeek();
	}

	public static String timeModeLastMonth(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getLeadersBoard(Score.ModeTime, 30, playerId));
		return timeModeLastMonth();
	}

	public static String timeModeAllTime(int playerId) {
		if (playerId > 0)
			return toJson(Db.LeadersBoards.getHiScoresOfPlayer(Score.ModeTime, playerId));
		return timeModeAllTime();
	}
}
