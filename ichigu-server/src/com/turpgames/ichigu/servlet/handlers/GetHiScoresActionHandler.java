package com.turpgames.ichigu.servlet.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.turpgames.ichigu.db.IchiguHiScores;
import com.turpgames.ichigu.entity.HiScore;
import com.turpgames.ichigu.entity.JsonEncoders;
import com.turpgames.ichigu.servlet.IchiguServlet;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.Utils;

public class GetHiScoresActionHandler implements IServletActionHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getHiScores(request, response);
	}

	private void getHiScores(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mode = request.getParameter(IchiguServlet.request.params.mode);
		String playerIdStr = request.getParameter(IchiguServlet.request.params.playerId);

		int playerId = 0;
		if (!Utils.isNullOrWhitespace(playerIdStr)) {
			try {
				playerId = Utils.parseInt(playerIdStr);
			} catch (Throwable t) {

			}
		}

		HiScore[] hiscores = getHiscores(mode, playerId).toArray(new HiScore[0]);
		
		String json = JsonEncoders.hiscores.encode(hiscores);
		response.getWriter().write(json);
	}

	private List<HiScore> getHiscores(String mode, int playerId) {
		if (IchiguServlet.request.values.mode.miniModeAllHiScores.equals(mode))
			return IchiguHiScores.miniModeAllTime(playerId);
		if (IchiguServlet.request.values.mode.miniModeLastMonthHiScores.equals(mode))
			return IchiguHiScores.miniModeLastMonth(playerId);
		if (IchiguServlet.request.values.mode.miniModeLastWeekHiScores.equals(mode))
			return IchiguHiScores.miniModeLastWeek(playerId);
		if (IchiguServlet.request.values.mode.miniModeTodaysHiScores.equals(mode))
			return IchiguHiScores.miniModeToday(playerId);

		if (IchiguServlet.request.values.mode.standartModeAllHiScores.equals(mode))
			return IchiguHiScores.standartModeAllTime(playerId);
		if (IchiguServlet.request.values.mode.standartModeLastMonthHiScores.equals(mode))
			return IchiguHiScores.standartModeLastMonth(playerId);
		if (IchiguServlet.request.values.mode.standartModeLastWeekHiScores.equals(mode))
			return IchiguHiScores.standartModeLastWeek(playerId);
		if (IchiguServlet.request.values.mode.standartModeTodaysHiScores.equals(mode))
			return IchiguHiScores.standartModeToday(playerId);

		if (IchiguServlet.request.values.mode.timeModeAllHiScores.equals(mode))
			return IchiguHiScores.timeModeAllTime(playerId);
		if (IchiguServlet.request.values.mode.timeModeLastMonthHiScores.equals(mode))
			return IchiguHiScores.timeModeLastMonth(playerId);
		if (IchiguServlet.request.values.mode.timeModeLastWeekHiScores.equals(mode))
			return IchiguHiScores.timeModeLastWeek(playerId);
		if (IchiguServlet.request.values.mode.timeModeTodaysHiScores.equals(mode))
			return IchiguHiScores.timeModeToday(playerId);

		return new ArrayList<HiScore>();
	}
}