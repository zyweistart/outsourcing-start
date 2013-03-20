package com.start.application.discover.entity;

import java.util.Date;

import com.start.kernel.entity.Root;
/**
 * 评论
 * @author Start
 */
public class Comment extends Root {

	private static final long serialVersionUID = 1L;
	
	public Comment(){}
	/**
	 * 用户编号
	 */
	private String userCode;
	/**
	 * 贴子编号
	 */
	private String invitationCode;
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

	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getInvitationCode() {
		return invitationCode;
	}
	
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
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
	
}