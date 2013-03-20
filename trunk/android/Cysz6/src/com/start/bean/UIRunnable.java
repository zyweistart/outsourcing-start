package com.start.bean;

import java.util.Map;

public abstract class UIRunnable {
	
	private Map<String, String> infoContent;

	public Map<String, String> getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(Map<String, String> infoContent) {
		this.infoContent = infoContent;
	}

	public abstract void run();
	
}
