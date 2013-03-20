package com.start.kernel.listener;

/**
 * 缓存容器
 * 
 * @author Start
 */
public final class CacheContext {
	
	/**
	 * 当前活跃的用户数
	 */
	private static long activeUsers = 0L;

	/**
	 * 初始化
	 */
	public static synchronized void initactiveUser() {
		activeUsers = 0;
	}

	/**
	 * 添加一个活跃用户
	 */
	public static synchronized void activeUser() {
		activeUsers++;
	}

	/**
	 * 去除一个活跃用户
	 */
	public static synchronized void inactiveUser() {
		if (activeUsers >= 1) {
			activeUsers--;
		}
	}

	/**
	 * 获取活跃用户数
	 */
	public static long getActiveUsers() {
		return activeUsers;
	}

}