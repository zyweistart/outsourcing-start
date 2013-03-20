package com.start.framework.exception;

/**
 * mybatis系统异常
 * @author Start
 */
public class MybatisError extends Error {

	private static final long serialVersionUID = 1L;
	
	public MybatisError(Throwable e){
		super(e);
	}
	
	public MybatisError(String message){
		super(message);
	}
}