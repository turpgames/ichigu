package com.turpgames.ichigu.servlet.handlers;

import java.io.IOException;

import com.turpgames.ichigu.db.LeadersBoardJsonCache;
import com.turpgames.ichigu.entity.Score;
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
		String modeStr = context.getParam(IchiguServlet.request.params.mode);
		String daysStr = context.getParam(IchiguServlet.request.params.days);
		String playerIdStr = context.getParam(IchiguServlet.request.params.playerId);

		int mode = Util.Strings.parseInt(modeStr, Score.ModeTime);
		int days = Util.Strings.parseInt(daysStr, Score.Daily);
		int playerId = Util.Strings.parseInt(playerIdStr, -1);
				
		String json = LeadersBoardJsonCache.getLeadersBoard(mode, days, playerId);
		context.writeToResponse(json);
	}
}