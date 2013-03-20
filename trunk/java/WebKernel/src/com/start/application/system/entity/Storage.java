package com.start.application.system.entity;

import com.start.kernel.entity.Root;
/**
 * 文件存储信息
 * @author Start
 */
public class Storage extends Root {

	private static final long serialVersionUID = 1L;
	
	public Storage(){}
	/**
	 * 加密模式(NO;DES)
	 */
	private Integer encrypt;
	/**
	 * 存储模式(LOCAL;ALIYUN)
	 */
	private Integer storage;
	/**
	 * 原始文件大小(Byte)
	 */
	private Integer originalSize;
	/**
	 * 原始文件MD5校验码
	 */
	private String originalMD5;
	/**
	 * 存储文件大小(Byte)
	 * 未加密则跟原始文件一样
	 */
	private Integer storageSize;
	/**
	 * 存储文件MD5校验码
	 * 未加密则跟原始文件一样
	 */
	private String storageMD5;
	/**
	 * 存储空间
	 */
	private String space;
	/**
	 * 存储目录
	 */
	private String directory;
	/**
	 * 目录:存储空间/存储目录/随机MD5形式的名称"
	 * 存储名称
	 */
	private String  name;
	
	public Integer getEncrypt() {
		return encrypt;
	}
	
	public void setEncrypt(Integer encrypt) {
		this.encrypt = encrypt;
	}
	
	public Integer getStorage() {
		return storage;
	}
	
	public void setStorage(Integer storage) {
		this.storage = storage;
	}
	
	public Integer getOriginalSize() {
		return originalSize;
	}
	
	public void setOriginalSize(Integer originalSize) {
		this.originalSize = originalSize;
	}
	
	public String getOriginalMD5() {
		return originalMD5;
	}
	
	public void setOriginalMD5(String originalMD5) {
		this.originalMD5 = originalMD5;
	}
	
	public Integer getStorageSize() {
		return storageSize;
	}
	
	public void setStorageSize(Integer storageSize) {
		this.storageSize = storageSize;
	}
	
	public String getStorageMD5() {
		return storageMD5;
	}
	
	public void setStorageMD5(String storageMD5) {
		this.storageMD5 = storageMD5;
	}
	
	public String getSpace() {
		return space;
	}
	
	public void setSpace(String space) {
		this.space = space;
	}
	
	public String getDirectory() {
		return directory;
	}
	
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}