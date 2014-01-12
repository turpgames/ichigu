package com.turpgames.ichigu.servlet.handlers;

import java.io.IOException;
import java.util.Calendar;

import com.turpgames.ichigu.db.Db;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.servlet.IchiguServlet;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.RequestContext;
import com.turpgames.utils.Util;

public class SaveHiScoreActionHandler implements IServletActionHandler {
	@Override
	public void handle(RequestContext context) throws IOException {
		String mode = context.getParam(IchiguServlet.request.params.mode);
		String playerIdStr = context.getParam(IchiguServlet.request.params.playerId);
		String scoreStr = context.getParam(IchiguServlet.request.params.score);

		Score score = new Score();
		score.setMode(Util.Strings.parseInt(mode));
		score.setPlayerId(Util.Strings.parseInt(playerIdStr));
		score.setScore(Util.Strings.parseInt(scoreStr));
		score.setTime(Calendar.getInstance().getTimeInMillis());

		Db.Scores.insert(score);
	}
}
