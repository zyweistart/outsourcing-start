package com.start.framework.jsptag;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;


public class UIBean extends InputPropertys implements DynamicAttributes {

	private static final long serialVersionUID = 5956742720796116170L;
	/**
	 * 动态特性的一些HTML
	 */
	protected Map<String,Object> dynamicAttributes = new HashMap<String,Object>();

	@Override
	public void setDynamicAttribute(String arg0, String attributeKey, Object attributeValue)throws JspException {
		dynamicAttributes.put(attributeKey, attributeValue);
	}
	/**
	 * 获取表单所对应的值
	 * @return
	 * @throws Exception
	 */
	protected String inputValue() throws Exception{
		/**
		 * 当前数据字段对应的值
		 */
		Object curValue=null;
		curValue=pageContext.getRequest().getAttribute(getName());
		if(curValue==null){
			String[] names=getName().trim().split("\\.");
			Object entity=pageContext.getRequest().getAttribute(names[0]);
			if(entity!=null){
				Class<?> prototype=entity.getClass();
				for(int j=1;j<names.length;j++){
					String fileName="get"+names[j].substring(0,1).toUpperCase()+names[j].substring(1);
					Method method=prototype.getMethod(fileName);
					curValue=method.invoke(entity);
					prototype=method.getReturnType();
				}
			}
		}
		return curValue!=null?curValue+"":"";
	}
	
	protected String printHtml(InputType inputType) throws Exception{
		StringBuilder htmlBuilder=new StringBuilder();
		htmlBuilder.append("<input type=\""+inputType+"\"");
		htmlBuilder.append(" name=\""+getName()+"\"");
		htmlBuilder.append(" value=\""+(getInputValue()!=null?getInputValue():inputValue())+"\"");
		if(getCssStyle()!=null){
			htmlBuilder.append(" class=\""+getCssStyle()+"\"");
		}
		//如果为radio或checkbox则判断
		if(isChecked(inputType)){
			htmlBuilder.append(" checked=\"checked\"");
		}
		//其它一些特性的HTML
		if(!dynamicAttributes.isEmpty()){
			for(String key:dynamicAttributes.keySet()){
				htmlBuilder.append(" "+key+"=\""+dynamicAttributes.get(key)+"\"");
			}
			dynamicAttributes.clear();
		}
		htmlBuilder.append("/>");
		return htmlBuilder.toString();
	}
	/**
	 * 是否选重
	 */
	protected Boolean isChecked(InputType inputType) throws Exception{
		Boolean flag=false;
		if((inputType==InputType.radio)||(inputType==InputType.checkbox)){
			String curVal=inputValue();
			if(getInputValue()!=null&&getInputValue().equals(curVal)){
				flag=true;
			}else if("".equals(curVal)&&getChecked()!=null&&getChecked()){
				flag=true;
			}
		}
		return flag;
	}

	public int doJspTag(InputType inputType){
		try{
			pageContext.getOut().print(printHtml(inputType));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			setInputValue(null);
		}
		return EVAL_PAGE;
	}
	
}