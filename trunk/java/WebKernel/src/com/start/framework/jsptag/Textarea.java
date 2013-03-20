package com.start.framework.jsptag;

import javax.servlet.jsp.JspException;

public class Textarea extends TextField {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		try{
			StringBuilder htmlBuilder=new StringBuilder();
			htmlBuilder.append("<textarea");
			htmlBuilder.append(" name=\""+getName()+"\"");
			if(getCssStyle()!=null){
				htmlBuilder.append(" class=\""+getCssStyle()+"\"");
			}
			//其它一些特性的HTML
			if(!dynamicAttributes.isEmpty()){
				for(String key:dynamicAttributes.keySet()){
					htmlBuilder.append(" "+key+"=\""+dynamicAttributes.get(key)+"\"");
				}
				dynamicAttributes.clear();
			}
			htmlBuilder.append(">");
			htmlBuilder.append((getInputValue()!=null?getInputValue():inputValue()));
			htmlBuilder.append("</textarea>");
			pageContext.getOut().print(htmlBuilder.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			setInputValue(null);
		}
		return EVAL_PAGE;
	}
	
}
