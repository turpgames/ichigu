package com.turpgames.ichigu.servlet.handlers;

import java.io.IOException;
import java.util.List;

import com.turpgames.ichigu.db.Db;
import com.turpgames.ichigu.db.LeadersBoardCache;
import com.turpgames.ichigu.entity.JsonEncoders;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Player;
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
		String whoseStr = context.getParam(IchiguServlet.request.params.whose);

		int mode = Util.Strings.parseInt(modeStr, Score.ModeTime);
		int days = Util.Strings.parseInt(daysStr, Score.Daily);
		int playerId = Util.Strings.parseInt(playerIdStr, -1);
		int whose = Util.Strings.parseInt(whoseStr, Score.General);

		LeadersBoard leadersBoard;
		if (whose == Score.FriendsScores) {
			String requestBody = context.getRequestContentAsUtf8String();
			String[] facebookIds = requestBody.split(",");
			leadersBoard = getFriendsHiScores(mode, days, playerId, facebookIds);
		}
		else if (whose == Score.General) {
			leadersBoard = getGeneralLeadersBoard(mode, days, playerId);
		}
		else if (whose == Score.MyScores) {
			leadersBoard = getMyHiScores(mode, days, playerId);
		}
		else {
			leadersBoard = new LeadersBoard();
		}

		String json = toJson(leadersBoard);
		context.writeToResponse(json);
	}

	public static LeadersBoard getMyHiScores(int mode, int days, int playerId) {
		return LeadersBoardCache.getLeadersBoard(mode, days, playerId);
	}

	public static LeadersBoard getGeneralLeadersBoard(int mode, int days, int playerId) {
		LeadersBoard leadersBoard = LeadersBoardCache.getLeadersBoard(mode, days, -1);

		if (playerId > 0) {
			Score ownScore = Db.LeadersBoards.getPlayersHiScore(mode, days, playerId);
			if (ownScore != null) {
				int rank = Db.LeadersBoards.getRank(mode, days, ownScore.getScore());
				if (rank > 10) {
					leadersBoard.setOwnRank(rank);
					leadersBoard.setOwnScore(ownScore);
				}
			}
		}

		return leadersBoard;
	}

	public static LeadersBoard getFriendsHiScores(int mode, int days, int playerId, String[] facebookIds) throws IOException {
		Player self = Db.Players.get(playerId);
		if (self == null)
			return new LeadersBoard();

		List<String> tmp = Util.Misc.toList(facebookIds);
		tmp.add(self.getFacebookId());
		facebookIds = tmp.toArray(new String[0]);

		LeadersBoard leadersBoard = Db.LeadersBoards.getFriendsLeadersBoard(mode, days, playerId, 10, facebookIds);

		if (playerId > 0) {
			Score ownScore = Db.LeadersBoards.getPlayersHiScore(mode, days, playerId);
			if (ownScore != null) {
				int rank = Db.LeadersBoards.getRank(mode, days, ownScore.getScore(), facebookIds);
				if (rank > 10) {
					leadersBoard.setOwnRank(rank);
					leadersBoard.setOwnScore(ownScore);
				}
			}
		}

		return leadersBoard;
	}

	private static String toJson(LeadersBoard leadersBoard) {
		return JsonEncoders.leadersBoard.encode(leadersBoard);
	}
}