package com.start.framework.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 全局配置
 * 
 * @author Start
 */
public final class AppConfig {

	/**
	 * 获取当前类的路径
	 */
	public static final String RESOURCEPATH = new Object() {
		public String getResourcePath() {
			/**
			 * 当前方法需要放在该类的最后，原因该类都是静态成员加载顺序由上而下FILESEPARATOR需先加载后方可使用
			 */
			return this.getClass().getClassLoader().getResource("").getPath();
		}
	}.getResourcePath();
	/**
	 * 常量配置
	 */
	public static final Map<String, String> Constants = new HashMap<String, String>();
	/**
	 * 拦截器列表
	 */
	public static final List<Class<?>> Interceptors = new ArrayList<Class<?>>();
	/**
	 * 持久化配置
	 */
	public static final Map<String, String> Persistents = new HashMap<String, String>();
	/**
	 * 连接池对象
	 */
	public static final Map<String, Properties> ConnectionConfigs = new HashMap<String, Properties>();

}