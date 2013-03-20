package com.start.application.discover.entity;

import java.util.Date;

import com.start.kernel.entity.Root;
/**
 * 贴子
 * @author Start
 */
public class Invitation extends Root {

	private static final long serialVersionUID = 1L;
	
	public Invitation(){}
	/**
	 * 用户编号
	 */
	private String userCode;
	/**
	 * 内容 
	 */
	private String content;
	/**
	 * 发布时间 
	 */
	private Date pubDate;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 纬度
	 */
	private Double latitude;
	/**
	 * 状态(1:正常2:撤销)
	 */
	private Integer status=1;
	/**
	 * 文件编号
	 */
	private String fileCode;

	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getPubDate() {
		return pubDate;
	}
	
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getFileCode() {
		return fileCode;
	}
	
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

}