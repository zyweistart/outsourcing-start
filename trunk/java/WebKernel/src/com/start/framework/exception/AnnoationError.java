package com.start.framework.exception;

/**
 * 容器注解出错
 * 注解定义出错，系统的严重错误
 * @author Start
 */
public class AnnoationError extends Error {

	private static final long serialVersionUID = 1L;

	public AnnoationError(String message) {
		super(message);
	}
}