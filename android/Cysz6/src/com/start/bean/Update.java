package com.start.bean;

import java.io.Serializable;

/**
 * 应用程序更新实体类
 * @author start
 *
 */
public class Update implements Serializable {

	private static final long serialVersionUID = 1L;

	private int versionCode;
	
	private String versionName;
	
	private String downloadUrl;
	
	private String updateLog;

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}
	
}