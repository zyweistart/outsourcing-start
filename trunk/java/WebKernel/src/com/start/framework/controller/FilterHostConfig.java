package com.start.framework.controller;

import java.util.Iterator;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import com.start.framework.utils.MakeIterator;


public final class FilterHostConfig implements IFilterHostConfig {

	private FilterConfig config;
	
	public FilterHostConfig(FilterConfig config) {
		this.config = config;
	}

	@Override
	public String getInitParameter(String key) {
		return config.getInitParameter(key);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterator<String> getInitParameterNames() {
		 return MakeIterator.convert(config.getInitParameterNames());
	}

	@Override
	public ServletContext getServletContext() {
		return config.getServletContext();
	}

}