package com.start.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Action方法返回基接口
 */
public interface IActionResult {
	/**
	 * Action返回后执行的方法，在里面发生的任何异常都需要在该方法里面进行捕获，框架不会做出任何响应
	 */
	void doExecute(ActionResultInvocation invocation);
	
	final class ActionResultInvocation {
		
		private HttpServletRequest request;
		
		private HttpServletResponse response;
		
		private Object targetEntity;

		public HttpServletRequest getRequest() {
			return request;
		}

		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}

		public HttpServletResponse getResponse() {
			return response;
		}

		public void setResponse(HttpServletResponse response) {
			this.response = response;
		}

		public Object getTargetEntity() {
			return targetEntity;
		}

		public void setTargetEntity(Object targetEntity) {
			this.targetEntity = targetEntity;
		}

	}
	
}