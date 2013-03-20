package com.start.application.system.entity;

import java.util.Date;

import com.start.kernel.entity.Root;
/**
 * 操作日志
 * @author Start
 */
public class OperatorLog extends Root {

	private static final long serialVersionUID = 1L;
	
	public OperatorLog(){}
	/**
	 * 负载索引
	 */
	private Integer loadIndex;
	/**
	 * 访问路径
	 */
	private String path;
	/**
	 * 动作说明
	 */
	private String action;
	/**
	 * 日志内容
	 */
	private String content;
	/**
	 * 请求IP
	 */
	private String requestIp;
	/**
	 * 请求时间
	 */
	private Date requestTime;
	/**
	 * 服务器时间
	 */
	private Date serverTime;
	/**
	 * 数据库时间
	 */
	private Date databaseTime;

	public Integer getLoadIndex() {
		return loadIndex;
	}
	
	public void setLoadIndex(Integer loadIndex) {
		this.loadIndex = loadIndex;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRequestIp() {
		return requestIp;
	}
	
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	
	public Date getRequestTime() {
		return requestTime;
	}
	
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	public Date getServerTime() {
		return serverTime;
	}
	
	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}
	
	public Date getDatabaseTime() {
		return databaseTime;
	}
	
	public void setDatabaseTime(Date databaseTime) {
		this.databaseTime = databaseTime;
	}
	
}