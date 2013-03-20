package com.start.kernel.http;

import java.io.BufferedInputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.start.framework.config.AppConstant;
import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.IMsg;
import com.start.kernel.config.Variable;
import com.start.kernel.entity.Root;
import com.start.kernel.exception.AppRuntimeException;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.MD5;
import com.start.kernel.utils.StringCheck;
import com.start.kernel.utils.StringUtils;
import com.start.kernel.utils.TimeUtils;

public class RequestUtils {

	private static final String SIGNATURE="sigs";
	private static final String METHOD_GET = "GET";
	private static final String METHOD_POST = "POST";
	/**
	 * 文件上传标记
	 */
	private boolean fileUpload=false;
	/**
	 * 请求参数的长度
	 */
	private static final Integer MAX_REQUEST_QUERYSTRING_LENGTH =Variable.Byte_Hex;
	/**
	 * 请求主体内容大小
	 */
	private static final Integer MAX_REQUEST_BODY_CONTENT_LENGTH =Variable.Byte_Hex*Variable.Byte_Hex*4;
	
	private StringBuilder bodyData;
	private HttpServletRequest request;
	private Map<String,String> headers;
	private Map<String,String> xmlDocument;
	
	public RequestUtils(HttpServletRequest request){
		this.request=request;
	}
	
	public <T extends Root> T initGetData(Class<T> prototype){
		return initData(METHOD_GET,prototype);
	}
	
	public <T extends Root> T initPostData(Class<T> prototype){
		return initData(METHOD_POST,prototype);
	}
	
	public <T extends Root> T initData(String method, Class<T> prototype){
		return getData(method, false, prototype,MAX_REQUEST_BODY_CONTENT_LENGTH);
	}
	
	public void initDataUpload() {
		fileUpload=true;
		getData(METHOD_POST, false, null, AppConstant.MAXUPLOADSIZE);
	}
	
	private static final Map<String,List<Method>> cacheSetMethods=new HashMap<String,List<Method>>();
	/**
	 * @param method
	 * 请求的方法GET、POST
	 * @param nullflag
	 * 请求内容是否可以为空
	 * @param prototype
	 * @param length
	 * 请求的内容长度
	 * @return
	 */
	private <T extends Root> T getData(String method, boolean nullflag, Class<T> prototype, long length) {
		//访问方式验证
		if (!method.equalsIgnoreCase(request.getMethod())) {
			throw new AppRuntimeException(IMsg._405);
		}
		if(method.equalsIgnoreCase(METHOD_GET)) {
			//请求参数的长度验证例：http://127.0.0.1/webcore.htm?id=1&name=test中的id=1&name=test的长度
			if(StringUtils.nullToStrTrim(request.getQueryString()).length() > MAX_REQUEST_QUERYSTRING_LENGTH) {
				throw new AppRuntimeException(IMsg._414);
			}
		}else if(method.equalsIgnoreCase(METHOD_POST)) {
			//主体内容的大小
			long contentLength = request.getContentLength();
			if(isFileUpload()) {
				if(contentLength == 0) {
					if(!nullflag) {
						throw new AppRuntimeException(IMsg._411);
					}
				} else {
					if(contentLength > length) {
						throw new AppRuntimeException(IMsg._413);
					}
				}
			} else {
				if(contentLength > MAX_REQUEST_BODY_CONTENT_LENGTH) {
					throw new AppRuntimeException(IMsg._413);
				}
			}
		}
		T entity=null;
		//如果上传的为文件则不进行注入操作
		if(!isFileUpload()&&prototype!=null){
			try {
				entity = prototype.newInstance();
				Boolean flag=false;
				Method[] methods;
				List<Method> ms=cacheSetMethods.get(prototype.getName());
				if(ms==null){
					flag=true;
					methods=prototype.getMethods();
					ms=new ArrayList<Method>();
				}else{
					methods=new Method[ms.size()];
					ms.toArray(methods);
				}
				for(Method m:methods){
					if(m.getName().startsWith("set")){
						//只注入一个参数的方法
						if(m.getParameterTypes().length==1){
							if(flag){
								ms.add(m);
							}
							String value=getXMLDocument().get(m.getName().substring(3).toLowerCase());
							if(value!=null){
								Class<?> type=m.getParameterTypes()[0];
								if(type.equals(Integer.class)){
									m.invoke(entity, Integer.parseInt(value));
								}else if(type.equals(Long.class)){
									m.invoke(entity, Long.parseLong(value));
								}else if(type.equals(Float.class)){
									m.invoke(entity, Float.parseFloat(value));
								}else if(type.equals(Double.class)){
									m.invoke(entity, Double.parseDouble(value));
								}else if(type.equals(String.class)){
									m.invoke(entity, value);
								}else{
									throw new RuntimeException(type+"不支持该数据类型");
								}
							}
						}
					}
				}
				if(flag){
					cacheSetMethods.put(prototype.getName(), ms);
				}
			} catch (Exception e) {
				if(!AppRuntimeException.class.equals(e.getClass())){
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
				}
				throw new AppRuntimeException(IMsg._2011);
			}
		}
		return entity;
	}
	/**
	 * 设置MD5签名数据.
	 */
	public void signaturesMD5(){
		if(!StringUtils.nullToStrTrim(MD5.md5(getBodyData())).
				equalsIgnoreCase(StringUtils.nullToStrTrim(getHeaderValue(SIGNATURE)))) {
			throw new AppRuntimeException(IMsg._2006);
		}
	}
	/**
	 * 设置HmacSHA1签名数据
	 */
	public void signaturesHmacSHA1(String key) {
		if(!StringUtils.signatureHmacSHA1(getBodyData(), key).
				equalsIgnoreCase(StringUtils.nullToStrTrim(getHeaderValue(SIGNATURE)))){
			throw new AppRuntimeException(IMsg._2006);
		}
	}
	/**
	 * 获取请求头中的值可以不加请求前缀
	 */
	public String getHeaderValue(String headerName){
		return getHeaders().get(StringUtils.nullToStrTrim(headerName.toLowerCase()));
	}
	/**
	 * 获取请求头解码后的键值对
	 */
	public Map<String,String> getHeaders(){
		if(headers==null){
			headers=new HashMap<String,String>();
			Enumeration<String> headerNames=request.getHeaderNames();
			while(headerNames.hasMoreElements()){
				String headerName=headerNames.nextElement();
				//把所有请求头名称转为小写并对请求头的值进行URL解码操作还原原始数据
				headers.put(headerName.toLowerCase(),StringUtils.decode(request.getHeader(headerName)));
			}
		}
		return headers;
	}
	/**
	 * 获取主体XML内容
	 */
	public Map<String,String> getXMLDocument(){
		if(xmlDocument==null){
			xmlDocument=new HashMap<String,String>();
			try {
				Document document = new SAXBuilder().build(new StringReader(getBodyData()));
				Element root=document.getRootElement();
				List<Element> eles=root.getChildren();
				for(Element ele:eles){
					xmlDocument.put(ele.getName().toLowerCase(), ele.getValue());
				}
			} catch (Exception e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
				throw new AppRuntimeException(IMsg._2013);
			}
		}
		return xmlDocument;
	}
	/**
	 * 获取主体内容
	 */
	public String getBodyData(){
		if(bodyData==null){
			int len=-1;
			bodyData=new StringBuilder();
			byte[] buffer=new byte[Variable.BUFFER];
			try{
				BufferedInputStream bis=new BufferedInputStream(request.getInputStream());
				while((len=bis.read(buffer))!=-1){
					bodyData.append(new String(buffer,0,len));
				}
			}catch(Exception e){
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
				throw new AppRuntimeException(IMsg._2014);
			}finally{
				buffer=null;
			}
		}
		return bodyData.toString();
	}
	/**
	 * 获取请求方的IP地址
	 */
	public String getRequestIP() {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if (StringUtils.isEmpty(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (StringUtils.isEmpty(ip)) {
					ip=getHeaderValue("IP");
					if(StringUtils.isEmpty(ip)){
						ip = request.getRemoteAddr();
					}
				}
			}
		}
		if(!StringUtils.isEmpty(ip)){
			String[] ips = ip.split(",");
			if(ips.length > 1) {
				for(int i=0; i<ips.length; i++) {
					ip= StringUtils.nullToStrTrim(ips[i]);
					if(!StringUtils.isEmpty(ip)) {
						//如果存在多个IP地址则默认取第一个IP地址
						break;
					}
				}
			}
		}else{
			//IP地址不能为空
			throw new AppRuntimeException(IMsg._2015);
		}
		if(!StringCheck.checkIp(ip)){
			//IP地址格式不正确
			throw new AppRuntimeException(IMsg._2016);
		}
		return ip;
	}
	/**
	 * 获取客户端发送的请求时间
	 */
	public Date getRequesttime(){
		String requesttime=getHeaderValue("requesttime");
		if(StringUtils.isEmpty(requesttime)) {
			throw new AppRuntimeException(IMsg._2008);
		}
		if(!StringCheck.checkTime(requesttime)) {
			throw new AppRuntimeException(IMsg._2009);
		}
		Date requestTime = TimeUtils.format(requesttime, TimeUtils.yyyyMMddHHmmss);
		if(requestTime == null) {
			throw new AppRuntimeException(IMsg._2009);
		}
		return requestTime;
	}

	public boolean isFileUpload() {
		return fileUpload;
	}
	
}