package com.turpgames.ichigu.servlet.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.servlet.IchiguServlet;
import com.turpgames.servlet.IServletActionHandler;

public class RegisterPlayerActionHandler implements IServletActionHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String facebookId = request.getParameter(IchiguServlet.request.params.facebookId);
		String email = request.getParameter(IchiguServlet.request.params.email);
		String username = request.getParameter(IchiguServlet.request.params.username);

		Player player = new Player();

		player.setFacebookId(facebookId);
		player.setEmail(email);
		player.setUsername(username);
		player.setPassword("");

		player.insert();

		response.getWriter().write("" + player.getId());
	}
}