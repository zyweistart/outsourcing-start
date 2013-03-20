package com.start.kernel.http;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.controller.IActionResult;
import com.start.framework.utils.StackTraceInfo;
/**
 * 重定向
 */
public final class Redirect implements IActionResult {
	
	private final static Log log=LogFactory.getLog(Redirect.class);

	private String dispatcherPage;
	
	public Redirect(String dispatcherPage){
		this.dispatcherPage=dispatcherPage;
	}

	@Override
	public void doExecute(ActionResultInvocation invocation) {
		try {
			invocation.getResponse().sendRedirect(dispatcherPage);
		} catch (IOException e) {
			log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
		}
	}

}
