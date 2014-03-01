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
		String modeStr = context.getParam(IchiguServlet.request.params.mode);
		String playerIdStr = context.getParam(IchiguServlet.request.params.playerId);
		String scoreStr = context.getParam(IchiguServlet.request.params.score);

		int mode = Util.Strings.parseInt(modeStr);
		int playerId = Util.Strings.parseInt(playerIdStr);
		int score = Util.Strings.parseInt(scoreStr);

		boolean isFakeRequest = 
				(mode == Score.ModeMini && (score > 120 || score < 0)) ||
				(mode == Score.ModeStandard && score < 30) ||
				(mode == Score.ModeTime && (score > 600 || score < 0));

		if (isFakeRequest) {
			System.err.println(String.format("Fake SaveHiScore request detected. mode: %s, playerId: %s, score: %s, ip: %s",
					modeStr, playerIdStr, scoreStr, context.getClientIP()));
			return;
		}

		Score scr = new Score();
		scr.setMode(mode);
		scr.setPlayerId(playerId);
		scr.setScore(score);
		scr.setTime(Calendar.getInstance().getTimeInMillis());

		Db.Scores.insert(scr);
	}
}
