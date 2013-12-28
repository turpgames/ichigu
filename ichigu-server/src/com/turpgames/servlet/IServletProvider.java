package com.turpgames.servlet;

import javax.servlet.http.HttpServletRequest;

import com.turpgames.db.IConnectionProvider;

public interface IServletProvider {
	IServletActionHandler createActionHandler(HttpServletRequest request, String httpMethod);

	IConnectionProvider createConnectionProvider();
}
