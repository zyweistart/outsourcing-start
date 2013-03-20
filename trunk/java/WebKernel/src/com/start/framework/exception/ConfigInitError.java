package com.start.framework.exception;

/**
 * 配置文件初始化错误，系统初始化错误
 * @author Start
 */
public class ConfigInitError extends Error {

	private static final long serialVersionUID = 1L;

	public ConfigInitError(Throwable e){
		super(e);
	}
	
}
