package com.turpgames.ichigu.servlet;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.db.IConnectionProvider;
import com.turpgames.ichigu.db.IchiguConnectionProvider;
import com.turpgames.ichigu.servlet.handlers.GetLeadersBoardActionHandler;
import com.turpgames.ichigu.servlet.handlers.RegisterPlayerActionHandler;
import com.turpgames.ichigu.servlet.handlers.SaveHiScoreActionHandler;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.IServletProvider;
import com.turpgames.servlet.RequestContext;

public class IchiguServletProvider implements IServletProvider {
	private static final Map<String, IServletActionHandler> actionHandlers = new HashMap<String, IServletActionHandler>();

	@Override
	public IConnectionProvider createConnectionProvider() {
		return new IchiguConnectionProvider();
	}

	@Override
	public IServletActionHandler createActionHandler(RequestContext context) {
		String action = context.getParam(IchiguServlet.request.params.action);

		String key = context.getMethod() + "." + action;
		
		synchronized (actionHandlers) {
			if (actionHandlers.containsKey(key))
				return actionHandlers.get(key);

			IServletActionHandler handler;

			if (IchiguServlet.request.method.get.equals(context.getMethod()))
				handler = createGetActionHandler(action);
			else
				handler = createPostActionHandler(action);

			actionHandlers.put(key, handler);
			return handler;
		}
	}

	private IServletActionHandler createGetActionHandler(String action) {
		return IServletActionHandler.NULL;
	}

	private IServletActionHandler createPostActionHandler(String action) {
		if (IchiguServlet.request.values.action.saveHiScore.equals(action))
			return new SaveHiScoreActionHandler();
		
		if (IchiguServlet.request.values.action.getLeadersBoard.equals(action))
			return new GetLeadersBoardActionHandler();

		if (IchiguServlet.request.values.action.registerPlayer.equals(action))
			return new RegisterPlayerActionHandler();

		return IServletActionHandler.NULL;
	}
}
