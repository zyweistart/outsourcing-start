package com.start.kernel.exception;

/**
 * 应用程序异常,必须进行异常的捕获并处理
 * @author Start
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public AppException(String message){
		super(message);
	}
	
}