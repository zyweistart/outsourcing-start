package com.start.application.common.entity;

import java.util.Date;

import com.start.kernel.entity.Root;
/**
 * 登陆日志
 * @author Start
 */
public class LoginLog extends Root {

	private static final long serialVersionUID = 1L;
	
	public LoginLog(){}
	/**
	 * 用户编号
	 */
	private String userCode;
	/**
	 * 通行证编号
	 */
	private String accessId;
	/**
	 * 通行证密钥
	 */
	private String accessKey;
	/**
	 * 登陆时间
	 */
	private Date loginTime;
	/**
	 * 失效时间
	 */
	private Date invalidTime;
	/**
	 * 登陆备注
	 */
	private String loginRemark;
	/**
	 * 退出时间
	 */
	private Date quitTime;
	/**
	 * 退出备注
	 */
	private String quitRemark;
	/**
	 * 账户
	 */
	private Account account;

	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getAccessId() {
		return accessId;
	}
	
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	
	public String getAccessKey() {
		return accessKey;
	}
	
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
	public Date getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public Date getInvalidTime() {
		return invalidTime;
	}
	
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}
	
	public String getLoginRemark() {
		return loginRemark;
	}
	
	public void setLoginRemark(String loginRemark) {
		this.loginRemark = loginRemark;
	}
	
	public Date getQuitTime() {
		return quitTime;
	}
	
	public void setQuitTime(Date quitTime) {
		this.quitTime = quitTime;
	}
	
	public String getQuitRemark() {
		return quitRemark;
	}
	
	public void setQuitRemark(String quitRemark) {
		this.quitRemark = quitRemark;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}