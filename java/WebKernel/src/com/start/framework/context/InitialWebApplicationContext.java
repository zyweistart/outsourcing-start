package com.start.framework.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.config.Message;

/**
 * @WebListener 注册监听器 注解需要服务器环境：Tomcat7及以上
 */
// @WebListener
public class InitialWebApplicationContext implements ServletContextListener {

	private final static Log log = LogFactory
			.getLog(InitialWebApplicationContext.class);

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info(Message.getMessage(Message.PM_1000));
		try {
			Init.loadConfigFile();
			Init.loadContextClass();
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e.getMessage());
		}
	}

	/**
	 * 关闭服务器容器时触发
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		log.info(Message.getMessage(Message.PM_1001));
	}

}