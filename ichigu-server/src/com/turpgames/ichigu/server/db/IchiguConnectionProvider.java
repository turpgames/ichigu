package com.turpgames.ichigu.server.db;

import com.turpgames.framework.v0.db.IConnectionProvider;

public class IchiguConnectionProvider implements IConnectionProvider {

	@Override
	public String getConnectionProvider() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	public String getConnectionString() {
		return "jdbc:mysql://localhost/ichigu";
	}

	@Override
	public String getUsername() {
		return "root";
	}

	@Override
	public String getPassword() {
		return "123456";
	}
}