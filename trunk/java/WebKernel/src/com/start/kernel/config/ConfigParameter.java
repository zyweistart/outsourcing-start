package com.start.kernel.config;

import com.start.framework.config.AppConfig;
import com.start.kernel.utils.StringUtils;
/**
 * 配置常量
 * @author Start
 */
public interface ConfigParameter {
	/**
	 * 系统状态
	 */
	final Boolean SYSTEMSTATUS = StringUtils.nullToBoolean(AppConfig.Constants.get("SYSTEMSTATUS"));
	/**
	 * 是否为正式环境
	 */
	final Boolean SYSTEMFLAG = StringUtils.nullToBoolean(AppConfig.Constants.get("SYSTEMFLAG"));
	/**
	 * 服务初始化
	 */
	final Boolean INITSTATUS = StringUtils.nullToBoolean(AppConfig.Constants.get("INITSTATUS"));
	/**
	 * 负载索引一般用于标明在哪台服务器上
	 */
	final String BALANCED_WORKER_INDEX=StringUtils.nullToStrTrim(AppConfig.Constants.get("BALANCED_WORKER_INDEX"));
	/**
	 * 系统名称
	 */
	final String SYSTEMNAME = StringUtils.nullToStrTrim(AppConfig.Constants.get(SYSTEMFLAG ?"SYSTEMNAME":"SYSTEMNAME_TEST"));
	/**
	 * 系统版本
	 */
	final String SYSTEMINFO = SYSTEMNAME + AppConfig.Constants.get("VERSION");
	/**
	 * 系统的完整信息
	 */
	final String SYSTEMINFO_FULL = SYSTEMINFO + "(" + BALANCED_WORKER_INDEX+")";
	
	/**
	 * 日志文件的扩展名
	 */
	final String LOGSUFFIX=StringUtils.nullToStrTrim(AppConfig.Constants.get("LOGSUFFIX"));
	/**
	 * 日志文件最大的文件大小
	 */
	final Long LOGMAXFILESIZE=StringUtils.convertSize(AppConfig.Constants.get("LOGMAXFILESIZE"));
	
	/**
	 * 电子邮件主机SMTP
	 */
	final String SMTPHOST=StringUtils.nullToStrTrim(AppConfig.Constants.get("SMTPHOST"));
	/**
	 * 电子邮件发送用户名
	 */
	final String SMTPUSERNAME=StringUtils.nullToStrTrim(AppConfig.Constants.get("SMTPUSERNAME"));
	/**
	 * 电子邮件发送登陆密码DES加密
	 */
	final String SMTPPASSWORD=StringUtils.doKeyDecrypt(StringUtils.nullToStrTrim(AppConfig.Constants.get("SMTPPASSWORD")));
	/**
	 * 电子邮件发送主题
	 */
	final String SMTPFROM=StringUtils.nullToStrTrim(AppConfig.Constants.get("SMTPFROM"));
	/**
	 * 系统管理员的电子邮件
	 */
	final String SYSTEMEMAIL=StringUtils.nullToStrTrim(AppConfig.Constants.get("SYSTEMEMAIL"));
	
	/**
	 * CPU报警值
	 */
	final Integer CPUALARMPERC=StringUtils.nullToIntZero(AppConfig.Constants.get("CPUALARMPERC"));
	/**
	 * 监控的磁盘
	 */
	final String MONITORDISK=StringUtils.nullToStrTrim(AppConfig.Constants.get("MONITORDISK"));
	/**
	 * 硬盘报警值
	 */
	final Long DISKALARMSIZE=StringUtils.convertSize(AppConfig.Constants.get("DISKALARMSIZE"));
	/**
	 * 内存报警值
	 */
	final Long MEMALARMSIZE=StringUtils.convertSize(AppConfig.Constants.get("MEMALARMSIZE"));
	/**
	 * 数据库大小
	 */
	final Long DBALARMSIZE=StringUtils.convertSize(AppConfig.Constants.get("DBALARMSIZE"));
	/**
	 * 数据加密模式
	 */
	final Integer DATA_ENCRYPT_MODE=Integer.parseInt(AppConfig.Constants.get("DATA_ENCRYPT_MODE"));
	/**
	 * 数据存储模式
	 */
	final Integer DATA_STORAGE_MODE=Integer.parseInt(AppConfig.Constants.get("DATA_STORAGE_MODE"));
	
	/**
	 * 阿里云AccessID
	 */
	final String ALIYUNACCESSID=AppConfig.Constants.get("ALIYUNACCESSID");
	/**
	 * 阿里云AccessKey
	 */
	final String ALIYUNACCESSKEY=AppConfig.Constants.get("ALIYUNACCESSKEY");
	/**
	 * 存储的BUCKET
	 */
	final String STORAGEBUCKETNAME=AppConfig.Constants.get("STORAGEBUCKETNAME");
	/**
	 * 存储的目录
	 */
	final String STORAGEBUCKDIR=AppConfig.Constants.get("STORAGEBUCKDIR");
	
}