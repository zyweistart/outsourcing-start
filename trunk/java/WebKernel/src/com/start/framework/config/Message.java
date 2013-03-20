package com.start.framework.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.utils.StackTraceInfo;
/**
 * 消息资源
 * @author Start
 */
public final class Message {
	
	private final static Log log=LogFactory.getLog(Message.class);
	
	private final static String BUNDLE_NAME_MESSAGE="com.start.framework.META-INF.Message";

	private final static ResourceBundle RESOURCE_BUNDLE_MESSAGE;
	
	static{
		RESOURCE_BUNDLE_MESSAGE = ResourceBundle.getBundle(BUNDLE_NAME_MESSAGE);
	}
	
	public static String getMessage(Integer key,Object...params){
		try{
			return String.format(RESOURCE_BUNDLE_MESSAGE.getString(String.valueOf(key)), params);
		}catch(MissingResourceException e){
			log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
			return null;
		}
	}
	/**
	 * Initializing Root WebApplicationContext.Wait....
	 */
	public final static Integer PM_1000=1000;
	/**
	 * WebApplicationContext Close!
	 */
	public final static Integer PM_1001=1001;
	/**
	 * 容器中不存在该%s对象!
	 */
	public final static Integer PM_1002=1002;
	/**
	 * %s--已经注册，重复注册！
	 */
	public final static Integer PM_1003=1003;
	/**
	 * %s控制类%s方法返回的类型不是IActionResult接口!
	 */
	public final static Integer PM_1004=1004;
	/**
	 * %s控制类中不存在%s执行方法
	 */
	public final static Integer PM_1005=1005;
	/**
	 * 返回值已设置无须重复设置
	 */
	public final static Integer PM_1006=1006;
	/**
	 * 当前Action类实例不是ActionSupport的子类，数据注入失败！
	 */
	public final static Integer PM_1007=1007;
	
}