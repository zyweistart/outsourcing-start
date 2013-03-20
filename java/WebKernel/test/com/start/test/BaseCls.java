package com.start.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.start.framework.config.AppConstant;
import com.start.framework.context.Init;
import com.start.kernel.utils.MD5;
import com.start.kernel.utils.StringUtils;
import com.start.kernel.utils.TimeUtils;

public class BaseCls extends TestCase {
	static{
		Init.loadConfigFile();
	}
	private String baseUrl="http://127.0.0.1:8080/";
	protected String url="";
	protected Map<String,String> params=new HashMap<String,String>();
	protected Map<String,String> header=new HashMap<String,String>();
	protected String accessId="8eb379cc5d1809dbb3e30077df07fcc2";
	protected String accessKey="g1Z91+zpB3ImoEN6dm2VP+eARytEi+7EQgicmKDNRetwmmoSZB7Pug==";
	
	public String sendRequest() throws Exception{
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(baseUrl+url);
		
		StringBuilder sb=new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		sb.append("<request>");
		for(String key:params.keySet()){
			sb.append("<"+key+">");
			sb.append(params.get(key));
			sb.append("</"+key+">");
		}
		sb.append("</request>");
		for(String h:header.keySet()){
			httpPost.addHeader(h,StringUtils.encode(header.get(h)));
		}
		if(accessId!=null){
			httpPost.addHeader("accessId",accessId);
		}
		httpPost.addHeader("requesttime",TimeUtils.getSysTimeLong());
		if(accessKey==null){
			httpPost.addHeader("sigs",StringUtils.encode(MD5.md5(sb.toString())));
		}else{
			httpPost.addHeader("sigs",StringUtils.encode(StringUtils.signatureHmacSHA1(sb.toString(), accessKey)));
		}
		AbstractHttpEntity e = new ByteArrayEntity(sb.toString().getBytes(AppConstant.ENCODING));
		httpPost.setEntity(e);
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		InputStream is=entity.getContent();
		int len=-1;
		byte[] buffer=new byte[1024*8];
		try{
			StringBuilder sbstr=new StringBuilder();
			while((len=is.read(buffer))!=-1){
				sbstr.append(new String(buffer,0,len));
			}
			System.out.println(sbstr.toString());
			return sbstr.toString(); 
		}finally{
			is.close();
		}
	}
	/**
	 * 文件上传
	 */
	public String sendFileUpload(File localFile)throws Exception{
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(baseUrl+url);
		//设置访问ID
		if(accessId!=null){
			httpPost.addHeader("accessId",StringUtils.encode(accessId));
		}
		//设置请求时间
		httpPost.addHeader("requesttime",StringUtils.encode(TimeUtils.getSysTimeLong()));
		//设置其它请求头
		for(String h:header.keySet()){
			httpPost.addHeader(h,StringUtils.encode(header.get(h)));
		}
		HttpEntity e=new InputStreamEntity(new FileInputStream(localFile),localFile.length());
		httpPost.setEntity(e);
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		InputStream is=entity.getContent();
		int len=-1;
		byte[] buffer=new byte[1024*8];
		try{
			StringBuilder sbstr=new StringBuilder();
			while((len=is.read(buffer))!=-1){
				sbstr.append(new String(buffer,0,len));
			}
			System.out.println(sbstr.toString());
			return sbstr.toString(); 
		}finally{
			is.close();
		}
	}
	/**
	 * 文件下载
	 */
	public void fileDownload(File downFile) throws Exception{
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(baseUrl+url);
		StringBuilder sb=new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		sb.append("<request>");
		for(String key:params.keySet()){
			sb.append("<"+key+">");
			sb.append(params.get(key));
			sb.append("</"+key+">");
		}
		sb.append("</request>");
		for(String h:header.keySet()){
			httpPost.addHeader(h,StringUtils.encode(header.get(h)));
		}
		if(accessId!=null){
			httpPost.addHeader("accessId",accessId);
		}
		httpPost.addHeader("requesttime",TimeUtils.getSysTimeLong());
		if(accessKey==null){
			httpPost.addHeader("sigs",StringUtils.encode(MD5.md5(sb.toString())));
		}else{
			httpPost.addHeader("sigs",StringUtils.encode(StringUtils.signatureHmacSHA1(sb.toString(), accessKey)));
		}
		AbstractHttpEntity e = new ByteArrayEntity(sb.toString().getBytes(AppConstant.ENCODING));
		httpPost.setEntity(e);
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		InputStream is=entity.getContent();
		int len=-1;
		byte[] buffer=new byte[1024*8];
		FileOutputStream fos=new FileOutputStream(downFile);
		try{
			int le=0;
			while((len=is.read(buffer))!=-1){
				le+=len;
				fos.write(buffer,0,len);
				fos.flush();
			}
			System.out.println("总接收大小:"+le);
		}finally{
			fos.flush();
			fos.close();
		}
	}
	
}