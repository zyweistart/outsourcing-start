package com.start.framework.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局的实体容器
 */
public final class AppContext {

	/**
	 * 控制层的Action标有@Controller的注解
	 */
	private static final Map<String, Class<?>> actions = new HashMap<String, Class<?>>();
	/**
	 * 一般用于注入向服务层和数据访问层来自于标有@Service和@Repository的注解
	 */
	private static final Map<String, Class<?>> injections = new HashMap<String, Class<?>>();
	/**
	 * 实体层的entity标有@Entity的注解
	 */
	private static final Map<String, Class<?>> entitys= new HashMap<String, Class<?>>();

	public static Map<String, Class<?>> getActions() {
		return actions;
	}

	public static Map<String, Class<?>> getInjections() {
		return injections;
	}

	public static Map<String, Class<?>> getEntitys() {
		return entitys;
	}

}