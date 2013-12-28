package com.turpgames.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.turpgames.db.DbManager;

public abstract class TurpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final IServletProvider provider;

	protected TurpServlet(IServletProvider provider) {
		this.provider = provider;
	}

	@Override
	public void init() throws ServletException {
		DbManager.setConnectionProvider(provider.createConnectionProvider());
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handle(req, resp, "GET");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handle(req, resp, "POST");
	}

	private void handle(HttpServletRequest req, HttpServletResponse resp, String method) {
		try {
			IServletActionHandler handler = provider.createActionHandler(req, method);
			handler.handle(req, resp);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
