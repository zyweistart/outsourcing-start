package com.start.kernel.config;

import com.start.framework.config.AppConfig;
import com.start.framework.config.AppConstant;
import com.start.kernel.utils.StringUtils;

/**
 * 资源路径
 * @author Start
 */
public interface SystemPath {
	/**
	 * 数据文件的路径
	 */
	final String DATA_PATH=AppConstant.ROOTPATH+AppConstant.FILESEPARATOR+"Data"+AppConstant.FILESEPARATOR;
	/**
	 * 日志文件路径
	 */
	final String LOG_PATH=AppConstant.ROOTPATH+AppConstant.FILESEPARATOR+"Logs"+AppConstant.FILESEPARATOR;
	/**
	 * Tomcat日志目录
	 */
	final String TOMCATLOG_PATH=StringUtils.nullToStrTrim(AppConfig.Constants.get("TOMCATLOG_PATH_"+ConfigParameter.BALANCED_WORKER_INDEX));
	
	/**
	 * 备份目录主路径
	 */
	final String BACKUP_PATH=AppConstant.ROOTPATH+AppConstant.FILESEPARATOR+"BackUp"+AppConstant.FILESEPARATOR;
	/**
	 * 数据文件备份路径
	 */
	final String DATABACKUP_PATH=BACKUP_PATH+"Data"+AppConstant.FILESEPARATOR;
	/**
	 * 程序级日志备份路径
	 */
	final String LOGBACKUP_PATH=BACKUP_PATH+"Logs"+AppConstant.FILESEPARATOR+"Program"+AppConstant.FILESEPARATOR;
	/**
	 * 系统级日志备份路径
	 */
	final String SYSTEMLOGBACKUP_PATH=BACKUP_PATH+"Logs"+AppConstant.FILESEPARATOR+"System"+AppConstant.FILESEPARATOR;
	/**
	 * 服务器日志备份路径
	 */
	final String TOMCATLOGBACKUP_PATH=BACKUP_PATH+"Logs"+AppConstant.FILESEPARATOR+"Tomcat"+AppConstant.FILESEPARATOR;
	/**
	 * 程序源文件备份路径
	 */
	final String PROGRAMBACKUP_PATH=BACKUP_PATH+"Program"+AppConstant.FILESEPARATOR;
	/**
	 * 数据库文件备份路径
	 */
	final String DATABASEBACKUP_PATH=BACKUP_PATH+"DataBase"+AppConstant.FILESEPARATOR;
	/**
	 * 邮件模版目录
	 */
	final String EMAILPATH = AppConfig.RESOURCEPATH + "data" + AppConstant.FILESEPARATOR  + "email" + AppConstant.FILESEPARATOR;
	/**
	 * DES签名密钥
	 */
	final String DESKEYKEY = AppConfig.RESOURCEPATH + "data" + AppConstant.FILESEPARATOR   + "key" + AppConstant.FILESEPARATOR +
			(ConfigParameter.SYSTEMFLAG ? "deskey" : "deskeytest") + "key.data";
}
