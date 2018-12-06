package com.banry.pscm.web;

import javax.servlet.http.HttpServletRequest;

public class Util {

	 public static String getContextPath(HttpServletRequest request) {
		    String scheme = request.getScheme() + "://";
		    String serverName = request.getServerName();
		    String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
		    String contextPath = request.getContextPath();
		    return scheme + serverName + serverPort + contextPath;
		  }
	 
	 public static String getBaseUrl(HttpServletRequest request) {
		    String scheme = request.getScheme() + "://";
		    String serverName = request.getServerName();
		    String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();		   
		    return scheme + serverName + serverPort;
		  }
	 
}
