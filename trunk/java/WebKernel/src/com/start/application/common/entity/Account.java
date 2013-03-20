package com.start.application.common.entity;

import java.util.Date;

import com.start.kernel.entity.Root;
/**
 * 账户信息
 * @author Start
 */
public class Account extends Root {

	private static final long serialVersionUID = 1L;
	
	public Account(){}
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 邮箱绑定(1:未绑定;2:已绑定)
	 */
	private Integer emailBind=1;
	/**
	 * 注册时间
	 */
	private Date registerTime;
	/**
	 * 密码修改(1:不需要;2:需要)
	 */
	private Integer pwdModifyFlag=1;
	/**
	 * 用户状态(1:正常;2:暂停;3:注销)
	 */
	private Integer status=1;

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getEmailBind() {
		return emailBind;
	}
	
	public void setEmailBind(Integer emailBind) {
		this.emailBind = emailBind;
	}
	
	public Date getRegisterTime() {
		return registerTime;
	}
	
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	
	public Integer getPwdModifyFlag() {
		return pwdModifyFlag;
	}
	
	public void setPwdModifyFlag(Integer pwdModifyFlag) {
		this.pwdModifyFlag = pwdModifyFlag;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}