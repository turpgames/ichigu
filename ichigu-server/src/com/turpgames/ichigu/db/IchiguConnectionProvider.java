package com.turpgames.ichigu.db;

import com.turpgames.db.IConnectionProvider;
import com.turpgames.ichigu.server.ServerConfig;

public class IchiguConnectionProvider implements IConnectionProvider {

	@Override
	public String getConnectionProvider() {
		return ServerConfig.getJdbcDriver();
	}

	@Override
	public String getConnectionString() {
		return ServerConfig.getDbConnectionString();
	}

	@Override
	public String getUsername() {
		return ServerConfig.getDbUser();
	}

	@Override
	public String getPassword() {
		return ServerConfig.getDbPassword();
	}
}