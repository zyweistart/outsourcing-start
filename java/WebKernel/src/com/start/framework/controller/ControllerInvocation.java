package com.start.framework.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.config.AppConfig;
import com.start.framework.config.Message;
import com.start.framework.context.ContextInjection;
import com.start.framework.context.annnotation.Controller;
import com.start.framework.controller.IActionResult.ActionResultInvocation;
import com.start.framework.exception.NotActionResultException;
import com.start.framework.utils.StackTraceInfo;

public final class ControllerInvocation {

	private final static Log log = LogFactory
			.getLog(ControllerInvocation.class);
	
	private final FilterHostConfig fHostConfig;

	private final HttpServletRequest request;

	private final HttpServletResponse response;
	/**
	 * 当前的拦截器列表
	 */
	private final Iterator<Class<?>> interceptors;
	/**
	 * 容器注入
	 */
	private ContextInjection contextInjection;
	/**
	 * 当前的Action类的字节码
	 */
	private final Class<?> clasz;
	/**
	 * 当前要访问的方法
	 */
	private Method method;
	/**
	 * 当前类的方法集合
	 */
	private Method[] methods;
	/**
	 * 当前的Action类
	 */
	private ActionSupport entity;
	/**
	 * 返回值
	 */
	private IActionResult resultValue;
	/**
	 * 调用方法是否已经执行了
	 */
	private Boolean flag = false;

	public ControllerInvocation(HttpServletRequest request,
			HttpServletResponse response, FilterHostConfig fHostConfig,
			Class<?> clasz) {
		this.request = request;
		this.response = response;
		this.fHostConfig = fHostConfig;
		this.clasz = clasz;
		//拦截器列表
		this.interceptors = AppConfig.Interceptors.iterator();
	}

	/**
	 * 执行当前请求的Action方法
	 */
	public void invoke() {
		try {
			if (!flag) {
				getControllerEntity().intercept(new InvokeAction());
			}
		} finally {
			flag = true;
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public FilterHostConfig getFilterHostConfig() {
		return fHostConfig;
	}

	public ContextInjection getContextInjection() {
		if (contextInjection == null) {
			contextInjection = new ContextInjection();
		}
		return contextInjection;
	}

	/**
	 * 设置当前Action访问执行的方法
	 */
	public void setMethod(String methodName) {
		for (Method m : getMethods()) {
			if (m.getName().equals(methodName)) {
				this.method = m;
				return;
			}
		}
		String message = Message.getMessage(Message.PM_1005, 
				clasz.getAnnotation(Controller.class).value(), methodName);
		log.error(StackTraceInfo.getTraceInfo() + message);
		throw new NullPointerException(message);
	}

	/**
	 * 当前Action访问执行的方法
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * 返回所有的方法
	 */
	public Method[] getMethods() {
		if (methods == null) {
			methods = clasz.getMethods();
		}
		return methods;
	}

	/**
	 * 当前Action的实例ActionSupport的子类
	 */
	public ActionSupport getControllerEntity() {
		if (entity == null) {
			Object obj = getContextInjection().createInstance(clasz);
			if (obj instanceof ActionSupport) {
				entity = (ActionSupport) obj;
				entity.setRequest(getRequest());
				entity.setResponse(getResponse());
				entity.setHostConfig(getFilterHostConfig());
				entity.setContextInjection(getContextInjection());
			} else {
				String message = Message.getMessage(Message.PM_1007);
				log.error(StackTraceInfo.getTraceInfo() + message);
				throw new NullPointerException(message);
			}
		}
		return entity;
	}

	/**
	 * 继续执行下一个拦截器，如果一个拦截器不执行该方法则表示操作会暂停
	 */
	public void doInterceptor() {
		if (interceptors.hasNext()) {
			// 迭代执行拦截器方法
			((IInterceptor) getContextInjection().createInstance(
					interceptors.next())).intercept(this);
		} else {
			// Action执行方法
			invoke();
			if (resultValue != null) {
				// 返回值必须实现了IActionResult接口
				if (resultValue instanceof IActionResult) {
					ActionResultInvocation invocation = new ActionResultInvocation();
					invocation.setResponse(getResponse());
					invocation.setRequest(getRequest());
					invocation.setTargetEntity(getControllerEntity());
					// Action返回执行的方法
					resultValue.doExecute(invocation);
				} else {
					String message = Message.getMessage(Message.PM_1004, clasz
							.getAnnotation(Controller.class).value(),
							getMethod().getName());
					log.error(StackTraceInfo.getTraceInfo() + message);
					throw new NotActionResultException(message);
				}
			}
		}
	}

	// /////////////////////////////////////////////
	private Map<Class<?>, Object> caches;

	/**
	 * 参数缓存
	 */
	public Map<Class<?>, Object> getCaches() {
		if (caches == null) {
			caches = new HashMap<Class<?>, Object>();
		}
		return caches;
	}

	private Map<String, Map<String, Object>> bundle;

	/**
	 * 拦截器与拦截器之间的参数传递 key 参数名称 value中的key 参数的类型checkbox,text,file...
	 * value中的value 参数的值 类型常量存于RequestParameterInject类中
	 */
	public Map<String, Map<String, Object>> getBundle() {
		if (bundle == null) {
			bundle = new HashMap<String, Map<String, Object>>();
		}
		return bundle;
	}

	// /////////////////////////////////////////////
	@Override
	protected void finalize() throws Throwable {
		if (contextInjection != null) {
			try {
				contextInjection.finalize();
			} finally {
				contextInjection = null;
			}
		}
		super.finalize();
	}

	public final class InvokeAction {

		public void invoke() throws Exception {
			Object result = getMethod().invoke(getControllerEntity());
			if (result instanceof IActionResult) {
				resultValue = (IActionResult) result;
			}
		}

		public void setActionResult(IActionResult result) {
			if (resultValue == null) {
				resultValue = result;
			} else {
				// ACTION返回值不能重复设置
				String message = Message.getMessage(Message.PM_1006);
				log.error(StackTraceInfo.getTraceInfo() + message);
				throw new IllegalArgumentException(message);
			}
		}

	}

}