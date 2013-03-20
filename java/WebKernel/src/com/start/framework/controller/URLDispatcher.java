package com.start.framework.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.config.AppConstant;
import com.start.framework.context.AppContext;
import com.start.framework.utils.StackTraceInfo;

/**
 * URL解析触发类
 */
public final class URLDispatcher {

	private final static Log log = LogFactory.getLog(URLDispatcher.class);
	/**
	 * 默认执行的方法名称
	 */
	private final static String EXECUTE = "execute";

	private FilterHostConfig fHostConfig;

	public URLDispatcher(FilterHostConfig fHostConfig) {
		this.fHostConfig = fHostConfig;
	}

	/**
	 * 解析
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @param controller
	 *            控制层的Action别名
	 * @throws IOException
	 * @throws ServletException
	 * 
	 *             <pre>
	 * 会传递默认执行的方法
	 * 注：却保在调用该方法的时候把该方法放在最后执行,
	 * 因为控制器不存在则会调用FilterChain的doFilter方法继续执行下一个过滤器
	 * </pre>
	 */
	public void startAnalysis(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, String controller)
			throws IOException, ServletException {
		startAnalysis(request, response, chain, controller, EXECUTE);
	}

	/**
	 * 解析
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @param controller
	 *            控制层的Action别名
	 * @param execute
	 *            要在Action类中执行的方法名称
	 * @throws IOException
	 * @throws ServletException
	 * 
	 *             <pre>
	 * 注：却保在调用该方法的时候把该方法放在最后执行,
	 * 因为控制器不存在则会调用FilterChain的doFilter方法继续执行下一个过滤器
	 * </pre>
	 */
	public void startAnalysis(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, String controller,
			String execute) throws IOException, ServletException {
		final Class<?> action = AppContext.getActions().get(controller);
		if (action != null) {
			// 请求编码设置防止乱码
			if (request.getCharacterEncoding() == null) {
				request.setCharacterEncoding(AppConstant.ENCODING);
			}
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			ControllerInvocation ci = null;
			try {
				ci = new ControllerInvocation(request, response, fHostConfig,
						action);
				// 设置执行的方法,方法如果不存在则会引发NullPointerException
				ci.setMethod(execute);
				// 拦截器调用
				ci.doInterceptor();
			} finally {
				if (ci != null) {
					try {
						ci.finalize();
					} catch (Throwable e) {
						log.error(StackTraceInfo.getTraceInfo()
								+ e.getMessage());
						throw new RuntimeException(e.getMessage());
					} finally {
						ci = null;
					}
				}
			}
		} else {
			// 如果当前控制层不存在则会继续执行下一个过滤器
			chain.doFilter(request, response);
		}
	}

	@Override
	public void finalize() throws Throwable {
		fHostConfig = null;
		super.finalize();
	}

}