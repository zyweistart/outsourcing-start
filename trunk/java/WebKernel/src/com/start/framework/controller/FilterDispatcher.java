package com.start.framework.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FilterDispatcher implements Filter {

	private URLDispatcher dispatcher;
	
	public void init(FilterConfig fConfig) throws ServletException {
		dispatcher=new URLDispatcher(new FilterHostConfig(fConfig));
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)throws IOException,ServletException{
		final HttpServletRequest request=(HttpServletRequest)req;
		final HttpServletResponse response=(HttpServletResponse)res;
		String servletPath=request.getServletPath();
		if(servletPath.indexOf(".")!=-1){
			servletPath=servletPath.substring(0,servletPath.indexOf("."));
		}
		String[] addressPath=servletPath.substring(1).split("/");
		if(addressPath.length==1){
			dispatcher.startAnalysis(request,response,chain,addressPath[0]);
		}else if(addressPath.length==2){
			dispatcher.startAnalysis(request,response,chain,addressPath[0],addressPath[1]);
		}else{
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
		if(dispatcher!=null){
			try {
				dispatcher.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}finally{
				dispatcher=null;
			}
		}
	}

}