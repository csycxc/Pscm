package com.banry.pscm.workflow;

import java.io.File;

public class Util {
	
	public static String[] list(){
		String basePath=Util.class.getResource("/").getPath();
		basePath=basePath.substring(1,basePath.length());
		return new File(basePath+File.separator+"deployments").list();
	}

}
