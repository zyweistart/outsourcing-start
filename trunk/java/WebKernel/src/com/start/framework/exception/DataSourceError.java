package com.start.framework.exception;

/**
 * 获取数据源出错，WEB系统的致命错误
 * @author Start
 */
public class DataSourceError extends Error {

	private static final long serialVersionUID = 1L;
	
	public DataSourceError(Throwable e){
		super(e);
	}
	
	public DataSourceError(String message){
		super(message);
	}
}