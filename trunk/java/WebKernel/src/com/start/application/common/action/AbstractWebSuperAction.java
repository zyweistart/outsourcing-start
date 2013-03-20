package com.start.application.common.action;

import com.start.framework.controller.ControllerInvocation.InvokeAction;
import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.action.RootAction;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.http.View;
import com.start.kernel.listener.CacheContext;
import com.start.kernel.utils.LogUtils;

public abstract class AbstractWebSuperAction extends RootAction {

	private static final String VIEW_SERVICEPAGE="/WEB-INF/pages/service.html";
	
	@Override
	public void intercept(InvokeAction invokeAction) {
		try{
			CacheContext.activeUser();
			if(ConfigParameter.SYSTEMSTATUS){
				invokeAction.invoke();
			}else{
				//跳转到系统维护页面中
				setResult(new View(VIEW_SERVICEPAGE));
			}
		}catch(Exception e){
			//非正式环境打印错误信息
			if(!ConfigParameter.SYSTEMFLAG){
				e.printStackTrace();
			}
			LogUtils.printLogError(StackTraceInfo.getTraceInfo() + e.getMessage());
		}finally{
			if(getResult()!=null){
				invokeAction.setActionResult(getResult());
			}
			CacheContext.inactiveUser();
		}
	}
	
	private String code;
	
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}