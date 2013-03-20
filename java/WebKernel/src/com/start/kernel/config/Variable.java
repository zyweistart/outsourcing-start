package com.start.kernel.config;

import java.util.Date;

import com.start.kernel.utils.TimeUtils;

/**
 * 全局常量
 * @author Start
 */
public interface Variable {
	/**
	 * 一秒=1000毫秒
	 */
	final Integer ONESECOND=1000;
	/**
	 * 十分钟的毫秒数
	 */
	final Integer TEN_MILLISECOND = 600000;
	/**
	 * 一个小时毫秒数
	 */
	final Long HOUR_MILLISECOND = 3600000L;
	/**
	 * 一天的毫秒数
	 */
	final Long DAY_MILLISECOND = 86400000L;
	/**
	 * 明天的凌晨00:30分钟
	 */
	final Date TOMORROWHOURTIME=TimeUtils.getTomorrowHourTime(0,30);
	
	final Integer Byte_Hex = 1024;
	/**
	 * 缓冲区大小，默认8KB
	 */
	final Integer BUFFER=Byte_Hex*8;
	/**
	 * 存储大小的单位
	 */
	final String[] SIZEUNITS = new String[] { "BYTE", "KB", "MB", "GB", "TB", "PB" };
	
	final String UNKNOWN = "unknown";
	// 数据的加密模式
	/**
	 * 不加密
	 */
	final Integer EncryptMode_NO=1;
	/**
	 * DES加密
	 */
	final Integer EncryptMode_DES=2;
	//存储模式
	/**
	 * 本地
	 */
	final Integer StorageMode_LOCAL=1;
	/**
	 * 阿里云
	 */
	final Integer StorageMode_ALIYUN=2;
	//传输方式
	/**
	 * 不压缩
	 */
	final Integer TransportMode_NO=1;
	/**
	 * GZIP压缩
	 */
	final Integer TransportMode_GZIP=2;
	
	final String INSERT="insert";
	final String UPDATE="update";
	final String DELETE="delete";
	final String LOAD="load";
	final String FINDALL="findAll";
	
}