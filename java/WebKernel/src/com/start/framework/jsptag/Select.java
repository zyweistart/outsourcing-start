package com.start.framework.jsptag;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.servlet.jsp.JspException;


/**
 * Select标签插件
 * @author Start
 */
public class Select extends UIBean {

	private static final long serialVersionUID = 5404942887845389763L;
	
	private Object list;
	
	private String headKey;
	
	private String headValue;
	
	private String listKey;
	
	private String listValue;
	
	@Override
	public int doStartTag() throws JspException {
		try{
			if(getList() instanceof Collection){
				StringBuilder selecthtml=new StringBuilder();
				selecthtml.append(String.format("<select name=\"%s\" class=\"%s\"",getName(),getCssStyle()));
				//其它一些特性的HTML
				if(!dynamicAttributes.isEmpty()){
					for(String key:dynamicAttributes.keySet()){
						selecthtml.append(" "+key+"=\""+dynamicAttributes.get(key)+"\"");
					}
				}
				selecthtml.append(">");
				//选项头
				if(getHeadKey()!=null&&getHeadValue()!=null){
					selecthtml.append(String.format("<option value=\"%s\">%s</option>",getHeadKey(),getHeadValue()));
				}
				for(Object obj:(Collection<?>)getList()){
					String fileName="get"+getListKey().substring(0,1).toUpperCase()+getListKey().substring(1);
					Method method= obj.getClass().getMethod(fileName);
					Object vO=method.invoke(obj);
					fileName="get"+getListValue().substring(0,1).toUpperCase()+getListValue().substring(1);
					method= obj.getClass().getMethod(fileName);
					Object hO=method.invoke(obj);
					if(inputValue().equals(vO+"")){
						selecthtml.append(String.format("<option value=\"%s\" selected=\"selected\">%s</option>",vO,hO));
					}else{
						selecthtml.append(String.format("<option value=\"%s\">%s</option>",vO,hO));
					}
				}
				selecthtml.append("</select>");
				pageContext.getOut().print(selecthtml.toString());
			}else{
				throw new IllegalArgumentException("当前参数有误参数list不为Collection集合对象！");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}

	public String getHeadKey() {
		return headKey;
	}

	public void setHeadKey(String headKey) {
		this.headKey = headKey;
	}

	public String getHeadValue() {
		return headValue;
	}

	public void setHeadValue(String headValue) {
		this.headValue = headValue;
	}

	public String getListKey() {
		return listKey;
	}

	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

	public String getListValue() {
		return listValue;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}
	
}
