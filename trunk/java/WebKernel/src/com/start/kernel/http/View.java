package com.start.kernel.http;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.controller.IActionResult;
import com.start.framework.utils.StackTraceInfo;
/**
 * 转向
 */
public class View implements IActionResult {
	
	private final static Log log=LogFactory.getLog(View.class);
	
	private String dispatcherPage;
	/**
	 * Action方法缓存
	 */
	private static final Map<Class<?>,Map<String,Method>> cacheMethod=new HashMap<Class<?>,Map<String,Method>>();
	
	public View(String dispatcherPage){
		this.dispatcherPage=dispatcherPage;
	}
	
	@Override
	public void doExecute(ActionResultInvocation invocation) {
		try{
			HttpServletRequest request=invocation.getRequest();
			HttpServletResponse response=invocation.getResponse();
			Map<String,Method> methods=cacheMethod.get(invocation.getTargetEntity().getClass());
			if(methods==null){
				methods=new HashMap<String,Method>();
				//设置Request作用域的值
				for(Method method:invocation.getTargetEntity().getClass().getMethods()){
					String methodName=method.getName();
					if(methodName.startsWith("get")||methodName.startsWith("is")){
						String fieldName=null;
						if(methodName.startsWith("is")){
							fieldName=methodName.substring(2,3).toLowerCase()+methodName.substring(3);
						}else  if(methodName.startsWith("get")){
							fieldName=methodName.substring(3,4).toLowerCase()+methodName.substring(4);
						}
						if(fieldName!=null){
							methods.put(fieldName, method);
						}
					}
				}
				cacheMethod.put(invocation.getTargetEntity().getClass(), methods);
			}
			for(String fieldName:methods.keySet()){
				//只添加不存在的键值
				if(request.getAttribute(fieldName)==null){
					Method method=methods.get(fieldName);
					request.setAttribute(fieldName,method.invoke(invocation.getTargetEntity()));
				}
			}
			RequestDispatcher requestDispatcher=request.getRequestDispatcher(dispatcherPage);
			requestDispatcher.forward(request,response);
		}catch(Exception e){
			log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
		}
	}
	
}