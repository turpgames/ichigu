package com.turpgames.ichigu.servlet;

import javax.servlet.annotation.WebServlet;

import com.turpgames.servlet.TurpServlet;

@WebServlet("/")
public class IchiguServlet extends TurpServlet {
	private static final long serialVersionUID = 1L;

	public IchiguServlet() {
		super(new IchiguServletProvider());
	}

	public static final class request {
		public static class method {
			public final static String get = "GET";
			public final static String post = "POST";
		}
		
		public static class params {
			public final static String action = "a";
			public final static String mode = "m";
			public final static String facebookId = "f";
			public final static String playerId = "p";
			public final static String email = "e";
			public final static String username = "u";
			public final static String score = "s";
		}

		public static class values {
			public static class action {
				public final static String saveHiScore = "h";
				public final static String getLeadersBoard = "l";
				public final static String registerPlayer = "r";
			}

			public static class mode {
				public final static String miniModeAllHiScores = "ma";
				public final static String miniModeLastMonthHiScores = "mm";
				public final static String miniModeLastWeekHiScores = "mw";
				public final static String miniModeTodaysHiScores = "mt";
				public final static String standartModeAllHiScores = "sa";
				public final static String standartModeLastMonthHiScores = "sm";
				public final static String standartModeLastWeekHiScores = "sw";
				public final static String standartModeTodaysHiScores = "st";
				public final static String timeModeAllHiScores = "ta";
				public final static String timeModeLastMonthHiScores = "tm";
				public final static String timeModeLastWeekHiScores = "tw";
				public final static String timeModeTodaysHiScores = "tt";
			}
		}
	}
}