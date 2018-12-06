package com.banry.pscm.web.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="upload")
public class SystemConstants {
	
	//系统路径
	private String uploadDirReal;
	private String uploadDir;
	private String attach;
	public String getUploadDirReal() {
		return uploadDirReal;
	}
	public void setUploadDirReal(String uploadDirReal) {
		this.uploadDirReal = uploadDirReal;
	}
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
}
