package com.turpgames.ichigu.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.turpgames.db.IConnectionProvider;
import com.turpgames.ichigu.db.IchiguConnectionProvider;
import com.turpgames.ichigu.servlet.handlers.GetLeadersBoardActionHandler;
import com.turpgames.ichigu.servlet.handlers.RegisterPlayerActionHandler;
import com.turpgames.ichigu.servlet.handlers.SaveHiScoreActionHandler;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.IServletProvider;

public class IchiguServletProvider implements IServletProvider {
	private static final Map<String, IServletActionHandler> actionHandlers = new HashMap<String, IServletActionHandler>();

	@Override
	public IConnectionProvider createConnectionProvider() {
		return new IchiguConnectionProvider();
	}

	@Override
	public IServletActionHandler createActionHandler(HttpServletRequest request, String httpMethod) {
		String action = request.getParameter(IchiguServlet.request.params.action);

		String key = httpMethod + "." + action;
		
		synchronized (actionHandlers) {
			if (actionHandlers.containsKey(key))
				return actionHandlers.get(key);

			IServletActionHandler handler;

			if (IchiguServlet.request.method.get.equals(httpMethod))
				handler = createGetActionHandler(action);
			else
				handler = createPostActionHandler(action);

			actionHandlers.put(key, handler);
			return handler;
		}
	}

	private IServletActionHandler createGetActionHandler(String action) {
		if (IchiguServlet.request.values.action.getLeadersBoard.equals(action))
			return new GetLeadersBoardActionHandler();

		return IServletActionHandler.NULL;
	}

	private IServletActionHandler createPostActionHandler(String action) {
		if (IchiguServlet.request.values.action.saveHiScore.equals(action))
			return new SaveHiScoreActionHandler();

		if (IchiguServlet.request.values.action.registerPlayer.equals(action))
			return new RegisterPlayerActionHandler();

		return IServletActionHandler.NULL;
	}
}
