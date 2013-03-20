package com.start.framework.exception;

/**
 * 未实现IActionResult
 * @author Start
 */
public class NotActionResultException extends RuntimeException{

	private static final long serialVersionUID = -8144104251636456722L;
	
	public NotActionResultException(String message){
		super(message);
	}
}