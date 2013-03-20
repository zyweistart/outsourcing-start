package com.start.kernel.config;

import com.start.kernel.utils.PropertiesUtils;

/**
 * 业务常量busines.properties中的键值对
 * @author Start
 */
public interface Business {
	/**
	 * 备份服务器的负载索引号
	 */
	final String BACKUP_BALANCED_WORKER_INDEX=PropertiesUtils.getBusines("BACKUP_BALANCED_WORKER_INDEX");
	/**
	 * 用户登陆活跃时间(单位:小时)
	 */
	final Integer USERLOGINLOG_ACTIVATION_HOUR=Integer.parseInt(PropertiesUtils.getBusines("USERLOGINLOG_ACTIVATION_HOUR"));
	/**
	 * 激活码有效限(单位:天)
	 */
	final Integer IDENTIFIER_INVALIDTIME_DAY=Integer.parseInt(PropertiesUtils.getBusines("IDENTIFIER_INVALIDTIME_DAY"));
	/**
	 * 分页查询默认页大小
	 */
	final Integer DEFAULT_PAGESIZE=Integer.parseInt(PropertiesUtils.getBusines("DEFAULT_PAGESIZE"));
	/**
	 * 备份数据库
	 */
	final String BACKUPDB=PropertiesUtils.getBusines("BACKUPDB");
}