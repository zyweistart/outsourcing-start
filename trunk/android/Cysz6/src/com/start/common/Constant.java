package com.start.common;

public class Constant {
	/**
	 * true:测试环境
	 * false:正式环境
	 */
	public static final Boolean SYSTEMTEST=false;
	/**
	 * 服务地址
	 */
	public static final String HOST=SYSTEMTEST?
			"http://115.236.89.210:8888/accore/rest/RestService":
				"http://server.ancun.com:3391/rest/RestService";
	/**
	 * 全局编码
	 */
	public static final String ENCODE="UTF-8";
	/**
	 * 全局共享配置文件名称
	 */
	public static final String SharedPreferencesName="SPCysz6";
	/**
	 * SQLite数据库名称
	 */
	public static final String SQLiteDataBaseName="DBCysz6.db";
	/**
	 * 服务端返回的XML标签
	 * @author Start
	 * 
	 */
	public final class ResponseXML{
		
		public static final String INFO="info";
		
		public static final String PAGEINFO="pageinfo";
		
		public static final String CONTENT="content";
		
		public static final String CODE="code";
		
		public static final String MSG="msg";
		
		public static final String SUCCESSCODE="200";
	
	}
	/**
	 * 用户接口
	 * @author Start
	 * 
	 */
	public final class HttpAction{
		
	}
}
