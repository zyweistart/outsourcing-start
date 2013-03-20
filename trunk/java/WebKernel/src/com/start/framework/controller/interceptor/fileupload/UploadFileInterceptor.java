package com.start.framework.controller.interceptor.fileupload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.config.AppConstant;
import com.start.framework.controller.ControllerInvocation;
import com.start.framework.controller.IInterceptor;
import com.start.framework.controller.interceptor.RequestParameterInject;
import com.start.framework.utils.StackTraceInfo;

public class UploadFileInterceptor implements IInterceptor {
	
	private final static Log log = LogFactory.getLog(UploadFileInterceptor.class);

	private final static int LENGTH=1024*8;
	
	private final static String NAME="name=\"";
	private final static String FILENAME="filename=\"";
	private final static String CONTENTDISPOSITIONFORMDATA="Content-Disposition: form-data; ";
	private final static String CONTENTTYPE="Content-Type: ";
	
	@Override
	public void intercept(ControllerInvocation invocation) {
		try{
			HttpServletRequest request=invocation.getRequest();
			String contentType=request.getContentType();
			if(contentType!=null&&contentType.startsWith(RequestParameterInject.MULTIPARTFORMDATA)){
				String boundary=contentType.substring(contentType.indexOf("boundary=") + 9);
				//rfc1867数据包开头
				String startBoundary = "--" +boundary + "\r\n" ;
				//rfc1867数据包结尾
		        String endBoundary = "--" + boundary + "--\r\n";
		        //从request对象中取得流
		        ServletInputStream servletInputStream = request.getInputStream();
		        //读取的内容是否为文件
		        boolean isFileFlag=false;
		        //是否正在读取数据内容
		        boolean isReadFlag=false;
		        //字段
		        Map<String,List<String>> fieldMaps=new HashMap<String,List<String>>();
		        //文件
		        Map<String,List<UpLoadFile>> upLoadFiles=new HashMap<String,List<UpLoadFile>>();
		        int readLength=-1;
		        String fieldName=null;
	        	String fileName=null;
	        	String fileContentType=null;
	        	StringBuilder dataContent=null;
	        	File tmpFile=null;
		        BufferedOutputStream bufferedOutputStream=null;
		        //每次读取的最大缓冲区大小
		        byte[] BUFFER = new byte[LENGTH];
		        while ((readLength = servletInputStream.readLine(BUFFER, 0, LENGTH)) != -1) {
					//把读取到的值转换为String对象
				    String content = new String(BUFFER, 0, readLength,AppConstant.ENCODING);
				    //如果是数据的开头或结尾
				    if(content.equals(startBoundary)||content.equals(endBoundary)){
				    	//数据读取
				    	if(isReadFlag){
				    		//文件写入
				    		if(isFileFlag){
				    			if(bufferedOutputStream!=null){
				    				//文件对象保存
				    				List<UpLoadFile> lists=upLoadFiles.get(fieldName);
					                if(lists==null){
					                	lists=new ArrayList<UpLoadFile>();
					                }
					                long fileSize=tmpFile.length();
					                if(fileSize<=AppConstant.MAXUPLOADSIZE){
					                	lists.add(new UpLoadFile(tmpFile,fileName,fileContentType,fileSize));
					                	upLoadFiles.put(fieldName,lists);
					                }else{
					                	tmpFile.delete();
					                	log.error("文件大小超过了："+AppConstant.MAXUPLOADSIZE);
					                }
					                try{
					                	//清理对象
					                	bufferedOutputStream.flush();
					                	bufferedOutputStream.close();
					                }finally{
					                	bufferedOutputStream=null;
					                	tmpFile=null;
					                }
				    			}
				    		}else{//文本内容
				    			List<String> lists=fieldMaps.get(fieldName);
				                if(lists==null){
				                	lists=new ArrayList<String>();
				                }
				                //-2表示删除最后的\r\n
				                lists.add(dataContent.substring(0,dataContent.length()-2));
				    			fieldMaps.put(fieldName,lists);
				    			dataContent=null;
				    		}	
				    	}
				    	//初始化
				    	isFileFlag=false;
				    	isReadFlag=false;
				    }else{
				    	//数据读取
				    	if(isReadFlag){
				    		//文件写入
				    		if(isFileFlag){
				    			if(bufferedOutputStream!=null){
				    				bufferedOutputStream.write(BUFFER,0,readLength);
				    			}
				    		}else{
				    			//文本内容
				    			if(dataContent!=null){
				    				dataContent.append(content);
				    			}
				    		}
				    	}else{//数据头信息
				    		isReadFlag=true;
				    		//字段名
				        	fieldName=getFieldName(content);
				        	//判断是否为文件
				    		isFileFlag=isFileField(content);
				    		//如果为文件
				    		if(isFileFlag){
				    			fileName=getFileName(content);
				    			if(fileName!=null){
				    				//如果为文件则读取文件的内容类型
					    			readLength =servletInputStream.readLine(BUFFER, 0, LENGTH);
					    			content = new String(BUFFER, 0, readLength);
					    			fileContentType=content.substring(CONTENTTYPE.length(),content.length()-2);
					    			if(isAllowUpload(fileContentType.trim())){
					    				File dir=createDirectory(AppConstant.TMPPATH);
					    				tmpFile = new File(dir,UUID.randomUUID().toString());
					                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tmpFile));
					    			}else{
					    				log.error(fileName+"\t文件类型:"+fileContentType+"\t无法上传该类型文件！");
					    			}
				    			}else{
				    				//读取空行
						    		servletInputStream.readLine(BUFFER, 0,LENGTH);
				    			}
				    		}else{
				    			dataContent=new StringBuilder();
				    		}
				    		//读取空行
				    		servletInputStream.readLine(BUFFER, 0,LENGTH);
				    	}
				    }
				}
				/**
				 *  字段的参数存储
				 */
				for(String fileField:fieldMaps.keySet()){
					List<String> lists=fieldMaps.get(fileField);
					Map<String,Object> param=new HashMap<String,Object>();
					if(lists.size()>1){
						//如果为多个值一般而统称为Checkbox提交上来的数据，否则为文本框的数据
						param.put(RequestParameterInject.PARAMETER_CHECKBOX, lists.toArray());
					}else{
						param.put(RequestParameterInject.PARAMETER_TEXT,lists.get(0));
					}
					invocation.getBundle().put(fileField,param);	
				}
				//文件参数存储
				for(String fileField:upLoadFiles.keySet()){
					Map<String,Object> param=new HashMap<String,Object>();
					param.put(RequestParameterInject.PARAMETER_FILE, upLoadFiles.get(fileField));
					invocation.getBundle().put(fileField,param);	
				}
				RequestParameterInject.inject(invocation);
			}
		}catch(Exception e){
			log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
		}finally{
			//继续执行下一个拦截器
			invocation.doInterceptor();
		}
	}
	/**
	 * 判断是否是文件字段
	 */
	static boolean isFileField(String content){
		if (contentDisposition(content)) {
    		int nIndex = content.indexOf(FILENAME);
    		if(nIndex!=-1){
    			return true;
    		}
		}
		return false;
	}
	/**
	 * 是否是内容描述
	 */
	static boolean contentDisposition(String content){
		return content.startsWith(CONTENTDISPOSITIONFORMDATA);
	}
	/**
	 * 获取字段名
	 */
	static String getFieldName(String content){
		if (contentDisposition(content)) {
    		int nIndex = content.indexOf(NAME);
    		if(nIndex!=-1){
    			int nLastIndex = content.indexOf("\"", nIndex + NAME.length());
    			return content.substring(nIndex + NAME.length(), nLastIndex);
    		}
		}
		return "";
	}
	/**
	 * 获取文件名称
	 */
	static String getFileName(String content){
		if (contentDisposition(content)) {
    		int nIndex = content.indexOf(FILENAME);
    		if(nIndex!=-1){
    			int nLastIndex = content.indexOf("\"", nIndex + FILENAME.length());
        		String fileName=content.substring(nIndex+ FILENAME.length(), nLastIndex);
        		if(!"".equals(fileName.trim())){
        			return fileName;
        		}
    		}
		}
		return null;
	}
	/**
	 * 是否允许上传
	 */
	static boolean isAllowUpload(String contentType){
		for(String all:AppConstant.ALLOWUPLOADTYPES){
			if("*".equals(all.trim())){
				return true;
			}else if(all.trim().equals(contentType.trim())){
				return true;
			}
		}
		return false;
	}
	 /**
	 * 目录不存在则创建目录
	 */
    static File createDirectory(String path) {
        final File dir = new File(path);
        if (!dir.exists()) {
        	dir.mkdirs();
        }
        return dir;
    }
}