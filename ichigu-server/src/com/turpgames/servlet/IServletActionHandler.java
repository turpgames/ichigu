package com.turpgames.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IServletActionHandler {
	public static final IServletActionHandler NULL = new  IServletActionHandler() {		
		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		}
	};
	
	void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
