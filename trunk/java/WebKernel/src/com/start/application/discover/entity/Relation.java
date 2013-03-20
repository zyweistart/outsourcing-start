package com.start.application.discover.entity;

import com.start.kernel.entity.Root;
/**
 * 好友关系
 * @author Start
 */
public class Relation extends Root {

	private static final long serialVersionUID = 1L;
	
	public Relation(){}
	/**
	 * 用户编号
	 */
	private String userCode;
	/**
	 * 好友编号
	 */
	private String friendCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getFriendCode() {
		return friendCode;
	}

	public void setFriendCode(String friendCode) {
		this.friendCode = friendCode;
	}
	
}
