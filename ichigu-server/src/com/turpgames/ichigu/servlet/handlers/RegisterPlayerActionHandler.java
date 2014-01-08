package com.turpgames.ichigu.servlet.handlers;

import java.io.IOException;

import com.turpgames.ichigu.db.Db;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.servlet.IchiguServlet;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.RequestContext;

public class RegisterPlayerActionHandler implements IServletActionHandler {
	@Override
	public void handle(RequestContext context) throws IOException {
		String facebookId = context.getParam(IchiguServlet.request.params.facebookId);
		String email = context.getParam(IchiguServlet.request.params.email);
		String username = context.getParam(IchiguServlet.request.params.username);

		Player player = Db.Players.findByFacebookId(facebookId);

		if (player == null) {

			player = new Player();

			player.setFacebookId(facebookId);
			player.setEmail(email);
			player.setUsername(username);
			player.setPassword("");

			Db.Players.insert(player);
		}

		context.writeToResponse("" + player.getId());
	}
}