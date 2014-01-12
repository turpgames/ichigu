package com.turpgames.ichigu.servlet.handlers;

import java.io.IOException;

import com.turpgames.ichigu.db.IchiguLeadersBoardJson;
import com.turpgames.ichigu.servlet.IchiguServlet;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.RequestContext;
import com.turpgames.utils.Util;

public class GetLeadersBoardActionHandler implements IServletActionHandler {
	@Override
	public void handle(RequestContext context) throws IOException {
		getLeadersBoard(context);
	}

	private void getLeadersBoard(RequestContext context) throws IOException {
		String mode = context.getParam(IchiguServlet.request.params.mode);
		String playerIdStr = context.getParam(IchiguServlet.request.params.playerId);

		int playerId = 0;
		if (!Util.Strings.isNullOrWhitespace(playerIdStr)) {
			try {
				playerId = Util.Strings.parseInt(playerIdStr);
			} catch (Throwable t) {

			}
		}

		String json = getLeadersBoardJson(mode, playerId);
		context.writeToResponse(json);
	}

	private String getLeadersBoardJson(String mode, int playerId) {
		if (IchiguServlet.request.values.mode.miniModeAllHiScores.equals(mode))
			return IchiguLeadersBoardJson.miniModeAllTime(playerId);
		if (IchiguServlet.request.values.mode.miniModeLastMonthHiScores.equals(mode))
			return IchiguLeadersBoardJson.miniModeLastMonth(playerId);
		if (IchiguServlet.request.values.mode.miniModeLastWeekHiScores.equals(mode))
			return IchiguLeadersBoardJson.miniModeLastWeek(playerId);
		if (IchiguServlet.request.values.mode.miniModeTodaysHiScores.equals(mode))
			return IchiguLeadersBoardJson.miniModeToday(playerId);

		if (IchiguServlet.request.values.mode.standartModeAllHiScores.equals(mode))
			return IchiguLeadersBoardJson.standartModeAllTime(playerId);
		if (IchiguServlet.request.values.mode.standartModeLastMonthHiScores.equals(mode))
			return IchiguLeadersBoardJson.standartModeLastMonth(playerId);
		if (IchiguServlet.request.values.mode.standartModeLastWeekHiScores.equals(mode))
			return IchiguLeadersBoardJson.standartModeLastWeek(playerId);
		if (IchiguServlet.request.values.mode.standartModeTodaysHiScores.equals(mode))
			return IchiguLeadersBoardJson.standartModeToday(playerId);

		if (IchiguServlet.request.values.mode.timeModeAllHiScores.equals(mode))
			return IchiguLeadersBoardJson.timeModeAllTime(playerId);
		if (IchiguServlet.request.values.mode.timeModeLastMonthHiScores.equals(mode))
			return IchiguLeadersBoardJson.timeModeLastMonth(playerId);
		if (IchiguServlet.request.values.mode.timeModeLastWeekHiScores.equals(mode))
			return IchiguLeadersBoardJson.timeModeLastWeek(playerId);
		if (IchiguServlet.request.values.mode.timeModeTodaysHiScores.equals(mode))
			return IchiguLeadersBoardJson.timeModeToday(playerId);

		return "[]";
	}
}