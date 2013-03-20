package com.start.framework.jsptag;

import javax.servlet.jsp.JspException;

public class Radio extends UIBean {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		return doJspTag(InputType.radio);
	}
	
}