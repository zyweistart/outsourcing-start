package com.start.framework.jsptag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public class Form extends UIBean {

	private static final long serialVersionUID = -4100509430655476156L;
	
	private String action;

	private String method;
	
	@Override
	public int doStartTag() throws JspException {
		StringBuilder formBuilder=new StringBuilder();
		formBuilder.append("<form method=\""+getMethod()+"\" action=\""+pageContext.getServletContext().getContextPath()+getAction()+"\"");
		//其它一些特性的HTML
		if(!dynamicAttributes.isEmpty()){
			for(String key:dynamicAttributes.keySet()){
				formBuilder.append(" "+key+"=\""+dynamicAttributes.get(key)+"\"");
			}
			dynamicAttributes.clear();
		}
		formBuilder.append(">");
		try {
			pageContext.getOut().print(formBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().print("</form>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		if(method==null){
			method="post";
		}
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}