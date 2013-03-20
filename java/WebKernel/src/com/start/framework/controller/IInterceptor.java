package com.start.framework.controller;


/**
 * 拦截器基接口
 */
public interface IInterceptor{

	void intercept(ControllerInvocation invocation);
	
}