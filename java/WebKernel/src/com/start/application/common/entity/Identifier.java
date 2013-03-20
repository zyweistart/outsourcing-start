package com.start.application.common.entity;

import java.util.Date;

import com.start.kernel.entity.Root;
/**
 * 账户识别码
 * @author Start
 */
public class Identifier extends Root {

	private static final long serialVersionUID = 1L;
	
	public Identifier(){}
	/**
	 * 用户编号 
	 */
	private String userCode;
	/**
	 * 类别(1:邮箱验证2:忘记密码)
	 */
	private Integer type;
	/**
	 * 密钥
	 */
	private String passKey;
	/**
	 * 状态(1:未使用2:已使用3:已失效)
	 */
	private Integer status=1;
	/**
	 * 生成时间
	 */
	private Date generateTime;
	/**
	 * 生成IP
	 */
	private String generateIp;
	/**
	 * 失效时间
	 */
	private Date invalidTime;
	/**
	 * 处理时间
	 */
	private Date handleTime;
	/**
	 * 处理IP
	 */
	private String handleIp;

	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getPassKey() {
		return passKey;
	}
	
	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getGenerateTime() {
		return generateTime;
	}
	
	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}
	
	public String getGenerateIp() {
		return generateIp;
	}
	
	public void setGenerateIp(String generateIp) {
		this.generateIp = generateIp;
	}
	
	public Date getInvalidTime() {
		return invalidTime;
	}
	
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}
	
	public Date getHandleTime() {
		return handleTime;
	}
	
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	
	public String getHandleIp() {
		return handleIp;
	}
	
	public void setHandleIp(String handleIp) {
		this.handleIp = handleIp;
	}
	
}