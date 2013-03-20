package org.androidpn.service.entity;

import com.start.kernel.entity.Root;

public class ApnUser extends Root {

	private static final long serialVersionUID = 1L;
	
	public ApnUser(){}
	
	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
