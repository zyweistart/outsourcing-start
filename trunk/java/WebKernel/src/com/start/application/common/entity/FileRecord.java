package com.start.application.common.entity;

import java.util.Date;

import com.start.kernel.entity.Root;
/**
 * 文件记录信息
 * @author Start
 */
public class FileRecord extends Root {

	private static final long serialVersionUID = 1L;
	
	public FileRecord(){}
	/**
	 * 用户编号
	 */
	private String userCode;
	/**
	 * 文件名称 
	 */
	private String name;
	/**
	 * 扩展名
	 */
	private String extension;
	/**
	 * 内容类型
	 */
	private String contentType;
	/**
	 * 上传时间
	 */
	private Date uploadTime;
	/**
	 * 存储名称
	 */
	private String storageCode;

	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public Date getUploadTime() {
		return uploadTime;
	}
	
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	public String getStorageCode() {
		return storageCode;
	}
	
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}

}