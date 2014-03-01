package com.turpgames.ichigu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.turpgames.ichigu.server.FakeScoreGenerator;
import com.turpgames.servlet.TurpServlet;

@WebServlet("/")
public class IchiguServlet extends TurpServlet {
	private static final long serialVersionUID = 1L;

	public IchiguServlet() {
		super(new IchiguServletProvider());
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		FakeScoreGenerator.start();
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
			public final static String whose = "w";
			public final static String days = "d";
		}

		public static class values {
			public static class action {
				public final static String saveHiScore = "h";
				public final static String getLeadersBoard = "l";
				public final static String registerPlayer = "r";
			}
		}
	}
}