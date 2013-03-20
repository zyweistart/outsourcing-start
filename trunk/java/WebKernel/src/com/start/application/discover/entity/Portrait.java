package com.start.application.discover.entity;

import com.start.kernel.entity.Root;
/**
 * 头像
 * @author Start
 */
public class Portrait extends Root {

	private static final long serialVersionUID = 1L;
	
	public Portrait(){}
	/**
	 * 用户编号
	 */
	private String userCode;
	/**
	 * 文件编号
	 */
	private String fileCode;
	/**
	 * 排序
	 */
	private Integer orderby;

	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getFileCode() {
		return fileCode;
	}
	
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	
	public Integer getOrderby() {
		return orderby;
	}
	
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
	
}
