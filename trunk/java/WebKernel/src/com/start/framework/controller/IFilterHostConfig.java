package com.start.framework.controller;

import java.util.Iterator;

import javax.servlet.ServletContext;

public interface IFilterHostConfig {

    String getInitParameter(String key);


    Iterator<String> getInitParameterNames();


    ServletContext getServletContext();
    
}
