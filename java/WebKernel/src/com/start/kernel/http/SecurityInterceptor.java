package com.start.kernel.http;

import com.start.framework.controller.ControllerInvocation;
import com.start.framework.controller.IInterceptor;

public class SecurityInterceptor implements IInterceptor  {

	@Override
	public void intercept(ControllerInvocation invocation) {
		try{
			
		}finally{
			invocation.doInterceptor();
		}
	}

}
