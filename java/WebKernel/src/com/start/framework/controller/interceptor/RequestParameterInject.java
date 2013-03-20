package com.start.framework.controller.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.controller.ControllerInvocation;
import com.start.framework.utils.StackTraceInfo;


/**
 * 提交请求时，给Action类注入参数的辅助类
 * @author Start
 */
public final class RequestParameterInject {
	
	private final static Log log = LogFactory.getLog(RequestParameterInject.class);
	/**
	 * 文件上传提交方式
	 */
	public static final String MULTIPARTFORMDATA="multipart/form-data";
	/**
	 * 文本
	 */
	public static final String PARAMETER_TEXT="text";
	/**
	 * 复选框
	 */
	public static final String PARAMETER_CHECKBOX="checkbox";
	/**
	 * 文件
	 */
	public static final String PARAMETER_FILE="file";
	
	public static void inject(ControllerInvocation invocation) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		for(String parameter:invocation.getBundle().keySet()){
			Map<String,Object> keyVals=invocation.getBundle().get(parameter);
			for(String type:keyVals.keySet()){
				Object val=keyVals.get(type);
				if(PARAMETER_TEXT.equals(type)){
					injectParameter(parameter,val,invocation.getMethods(),invocation.getControllerEntity(),invocation.getCaches());
				}else if(PARAMETER_CHECKBOX.equals(type)){
					Object[] vals=(Object[]) val;
					StringBuilder strVals=new StringBuilder();
					for(Object v:vals){
						strVals.append(v+",");
					}
					strVals.deleteCharAt(strVals.length()-1);
					injectParameter(parameter,strVals.toString(),invocation.getMethods(),invocation.getControllerEntity(),invocation.getCaches());
				}else if(PARAMETER_FILE.equals(type)){
					for(Method method:invocation.getMethods()){
						if(method.getName().equals("set"+parameter.substring(0,1).toUpperCase()+parameter.substring(1))){
				       		try {
								method.invoke(invocation.getControllerEntity(),val);
							} catch (Exception e) {
								log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
							}
				       	}
					}
				}
			}
		}
	}
	/**
	 * Request参数注入
	 * @param parameter
	 * 参数名称
	 * @param value
	 * 参数所对应的值
	 * @param methods
	 * 对应类的所有方法
	 * @param entity
	 * 要注入的实体类对象
	 * @param caches
	 * 参数缓存
	 */
	static void injectParameter(String parameter,Object value,Method[] methods,Object entity,Map<Class<?>,Object> caches) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object injectObj=entity;
		String[] params=parameter.split("\\.");
		for(int i=0;i<params.length;i++){
			for (Method method : methods){
				String methodName=method.getName();
				if (methodName.startsWith("set")) {
					String fieldName=methodName.substring(3,4).toLowerCase()+methodName.substring(4);
					if (fieldName.equals(params[i].trim())){
						//只注入代参数且为一个的
						if(method.getParameterTypes().length==1){
							//如果是最后一个参数则注入实际的值
							if(params.length-1==i){
								Class<?> param=method.getParameterTypes()[0];
								String val=String.valueOf(value);
								if(String.class.equals(param)){
									method.invoke(injectObj, val);
								}else{
									if(!"".equals(val)){
										if(Boolean.class.equals(param)){
											method.invoke(injectObj, Integer.parseInt(val)==1?true:false);
										}else if(Integer.class.equals(param)){
											method.invoke(injectObj, Integer.parseInt(val));
										}else if(Long.class.equals(param)){
											method.invoke(injectObj, Long.parseLong(val));
										}else if(Float.class.equals(param)){
											method.invoke(injectObj, Float.parseFloat(val));
										}else if(Double.class.equals(param)){
											method.invoke(injectObj, Double.parseDouble(val));
										}else if(Date.class.equals(param)){
											String format=null;
											if(val.length()==8){
												format="HH:mm:ss";
											}else if(val.length()==10){
												format="yyyy-MM-dd";
											}else{
												format="yyyy-MM-dd HH:mm:ss";
											}
											DateFormat df=new SimpleDateFormat(format);
											try {
												Date date = df.parse(val);
												method.invoke(injectObj,date);
											} catch (ParseException e) {
												e.printStackTrace();
											}
										}else{
											method.invoke(injectObj, value);
										}
									}
								}
							}else{
								//如果不是最后一个参数则表明是一个对象则创建实例
								Class<?> prototype=method.getParameterTypes()[0];
								Object tmpObj=caches.get(prototype);
								if(tmpObj==null){
									//加入缓存
									try {
										tmpObj=prototype.newInstance();
									} catch (Exception e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									}
									caches.put(prototype, tmpObj);
								}
								if(i==0){
									try {
										method.invoke(entity,tmpObj);
									} catch (Exception e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									}
								}else{
									try {
										method.invoke(injectObj,tmpObj);
									} catch (Exception e) {
										log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
									}
								}
								injectObj=tmpObj;
								methods=prototype.getMethods();
								tmpObj=null;
							}
							break;
						}
					}
				}
			}
		}
	}
}