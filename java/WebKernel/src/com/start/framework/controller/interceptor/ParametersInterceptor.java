package com.start.framework.controller.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.controller.ControllerInvocation;
import com.start.framework.controller.IInterceptor;
import com.start.framework.utils.StackTraceInfo;

public final class ParametersInterceptor implements IInterceptor {
	
	private final static Log log = LogFactory.getLog(ParametersInterceptor.class);
	
	@Override
	public void intercept(ControllerInvocation invocation){
		HttpServletRequest request=invocation.getRequest();
		try{
			//非multipart/form-data提交方式拦截,各参数设置
			if(!RequestParameterInject.MULTIPARTFORMDATA.equals(request.getContentType())){
				Enumeration<String> enumerations = request.getParameterNames();
				while (enumerations.hasMoreElements()){
					String parameterName = enumerations.nextElement();
					String[] values=request.getParameterValues(parameterName);
					//参数存储
					Map<String,Object> param=new HashMap<String,Object>();
					if(values.length==1){
						//如果参数只为一个则一般为文本框，单选框等提交
						param.put(RequestParameterInject.PARAMETER_TEXT, values[0]);
					}else{
						//如果参数大于一个则一般为复选框提交
						param.put(RequestParameterInject.PARAMETER_CHECKBOX,values);
					}
					invocation.getBundle().put(parameterName,param);	
				}
				RequestParameterInject.inject(invocation);
			}
		}catch(Exception e){
			log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
		}finally{
			invocation.doInterceptor();
		}
	}

}