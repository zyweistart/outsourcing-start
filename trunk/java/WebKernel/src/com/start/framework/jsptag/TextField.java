package com.start.framework.jsptag;

import javax.servlet.jsp.JspException;

public class TextField extends UIBean {

	private static final long serialVersionUID = -3130188582574308569L;
	
	@Override
	public int doStartTag() throws JspException {
		return doJspTag(InputType.text);
	}
	
}