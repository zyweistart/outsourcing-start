package com.start.framework.exception;

/**
 * jdbc执行异常
 * @author Start
 */
public class JdbcRuntimeException extends RuntimeException{

	private static final long serialVersionUID = -8144104251636456722L;
	
	public JdbcRuntimeException(Throwable e){
		super(e);
	}
	
	public JdbcRuntimeException(String message){
		super(message);
	}
}