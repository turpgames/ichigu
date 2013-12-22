package com.turpgames.ichigu.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.turpgames.framework.v0.db.DbManager;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.server.db.IchiguConnectionProvider;
import com.turpgames.ichigu.server.entity.HiScore;

@WebServlet("/hiscores")
public class IchiguHiScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		DbManager.setConnectionProvider(new IchiguConnectionProvider());
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String mode = request.getParameter("m");
			String playerIdStr = request.getParameter("p");

			int playerId = 0;
			if (!Utils.isNullOrWhitespace(playerIdStr)) {
				try {
					playerId = Utils.parseInt(playerIdStr);
				} catch (Throwable t) {

				}
			}

			List<HiScore> hiscores = getHiscores(mode, playerId);
			writeHiScoresToResponse(hiscores, response);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private List<HiScore> getHiscores(String mode, int playerId) {
		if ("ma".equals(mode))
			return IchiguHiScores.miniModeAllTime(playerId);
		if ("mm".equals(mode))
			return IchiguHiScores.miniModeLastMonth(playerId);
		if ("mw".equals(mode))
			return IchiguHiScores.miniModeLastWeek(playerId);
		if ("mt".equals(mode))
			return IchiguHiScores.miniModeToday(playerId);

		if ("sa".equals(mode))
			return IchiguHiScores.standartModeAllTime(playerId);
		if ("sm".equals(mode))
			return IchiguHiScores.standartModeLastMonth(playerId);
		if ("sw".equals(mode))
			return IchiguHiScores.standartModeLastWeek(playerId);
		if ("st".equals(mode))
			return IchiguHiScores.standartModeToday(playerId);

		if ("ta".equals(mode))
			return IchiguHiScores.timeModeAllTime(playerId);
		if ("tm".equals(mode))
			return IchiguHiScores.timeModeLastMonth(playerId);
		if ("tw".equals(mode))
			return IchiguHiScores.timeModeLastWeek(playerId);
		if ("tt".equals(mode))
			return IchiguHiScores.timeModeToday(playerId);

		return new ArrayList<HiScore>();
	}

	private void writeHiScoresToResponse(List<HiScore> hiscores, HttpServletResponse response) throws IOException {
		response.getWriter().write("[");
		for (int i = 0; i < hiscores.size(); i++) {
			HiScore hiscore = hiscores.get(i);
			response.getWriter().write(hiscore.toJson());
			if (i < hiscores.size() - 1)
				response.getWriter().write(",");
		}
		response.getWriter().write("]");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}