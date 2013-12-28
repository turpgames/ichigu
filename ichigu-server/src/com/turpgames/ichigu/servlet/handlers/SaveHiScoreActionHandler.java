package com.turpgames.ichigu.servlet.handlers;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.servlet.IchiguServlet;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.Utils;

public class SaveHiScoreActionHandler implements IServletActionHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter(IchiguServlet.request.params.mode);
		String playerIdStr = request.getParameter(IchiguServlet.request.params.playerId);
		String scoreStr = request.getParameter(IchiguServlet.request.params.score);

		Score score = new Score();
		score.setMode(Utils.parseInt(mode));
		score.setPlayerId(Utils.parseInt(playerIdStr));
		score.setScore(Utils.parseInt(scoreStr));
		score.setTime(Calendar.getInstance().getTimeInMillis());
		score.insert();
	}
}
