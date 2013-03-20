package com.start.framework.jsptag;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class InputPropertys extends BodyTagSupport {

	private static final long serialVersionUID = 4912969567183504495L;
	/**
	 * 字段名称
	 */
	private String name;
	/**
	 * 对应的值
	 */
	private String inputValue;
	/**
	 * 样式
	 */
	private String cssStyle;
	/**
	 * 是否选重
	 */
	private Boolean checked;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCssStyle() {
		return cssStyle;
	}
	
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	
	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
}