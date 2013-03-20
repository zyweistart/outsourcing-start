package com.start.kernel.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObject;
import com.start.application.system.entity.Storage;
import com.start.framework.config.AppConstant;
import com.start.framework.controller.IActionResult;
import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.config.IMsg;
import com.start.kernel.config.SystemPath;
import com.start.kernel.config.Variable;
import com.start.kernel.exception.AppRuntimeException;
import com.start.kernel.utils.DES;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.StringUtils;
/**
 * 文件下载
 * @author Start
 */
public class Download  implements IActionResult {
	
	private static final String Contenttype="Content-type:";
	private static final String AcceptRanges="Accept-Ranges";
	private static final String AcceptLength="Accept-Length:";
	private static final String ContentDisposition="Content-Disposition";
	private static final String bytes="bytes";
	private static final String attachment="attachment;filename=\"";
	/**
	 * 内容类型
	 */
	private String contentType;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件存储信息
	 */
	private Storage storage;
	/**
	 * 响应头信息
	 */
	private Map<String,String> responseHeader=new HashMap<String,String>();
	
	public Download(Storage storage,String contentType,String fileName){
		this.storage=storage;
		this.contentType=contentType;
		this.fileName=fileName;
		//原始文件大小
		responseHeader.put("originalsize",String.valueOf(storage.getOriginalSize()));
		//原始文件MD5
		responseHeader.put("originalmd5",storage.getOriginalMD5());
		//文件名称
		responseHeader.put("filename",StringUtils.encode(fileName));
	}
	
	public Download(Storage storage,String contentType,String fileName,Map<String,String> responseHeader){
		this.storage=storage;
		this.responseHeader.putAll(responseHeader);
	}
	
	@Override
	public void doExecute(ActionResultInvocation invocation) {
		HttpServletResponse response=invocation.getResponse();
		response.setContentLength(storage.getOriginalSize());
		if(!StringUtils.isEmpty(contentType)){
			response.setContentType(contentType);
			response.setHeader(Contenttype,contentType);
		}
		response.setHeader(AcceptRanges,bytes);
//		response.setHeader("Content-Range", "bytes 0-120/120");
		response.setHeader(AcceptLength, String.valueOf(storage.getOriginalSize()));
		response.setHeader(ContentDisposition, attachment+ StringUtils.encode(fileName) + "\"");
		//输出响应头信息
		if(responseHeader!=null){
			for(String key:responseHeader.keySet()){
				response.setHeader(key,responseHeader.get(key));
			}
		}
		CipherOutputStream cipherOutputStream = null;
		BufferedOutputStream bufferedOutputStream=null;
		BufferedInputStream bufferedInputStream = null;
		try {
			//不管文件存储在哪里都先从本地寻找找不到再到相应的存储区找
			File localFile=new File(SystemPath.DATA_PATH+storage.getSpace()+AppConstant.FILESEPARATOR+storage.getName());
			if(localFile.exists()){
				bufferedInputStream = new BufferedInputStream(new FileInputStream(localFile));
			}else{
				if(storage.getStorage().equals(Variable.StorageMode_ALIYUN)){
					OSSClient oss=new OSSClient(ConfigParameter.ALIYUNACCESSID,ConfigParameter.ALIYUNACCESSKEY);
					OSSObject ossObj=oss.getObject(new GetObjectRequest(ConfigParameter.STORAGEBUCKETNAME, storage.getName()));
					if(ossObj!=null){
						bufferedInputStream = new BufferedInputStream(ossObj.getObjectContent());
					}
				}else{
					LogUtils.printError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim("文件不存在"));
					throw new AppRuntimeException(IMsg.ICommon._102010);
				}
			}
			int len=-1;
			byte[] buffer=new byte[Variable.BUFFER];
			bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
			if(storage.getEncrypt().equals(Variable.EncryptMode_NO)){
				//不加密直接输出
				while ((len = bufferedInputStream.read(buffer)) != -1) {
					bufferedOutputStream.write(buffer, 0, len);
					bufferedOutputStream.flush();
				}
			}else if(storage.getEncrypt().equals(Variable.EncryptMode_DES)){
				//边解密边输出
				Cipher cipher = Cipher.getInstance(DES.ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, DES.getKey(SystemPath.DESKEYKEY));
				cipherOutputStream = new CipherOutputStream(bufferedOutputStream, cipher);
				while ((len = bufferedInputStream.read(buffer)) != -1) {
					cipherOutputStream.write(buffer, 0, len);
					cipherOutputStream.flush();
				}
			}
			response.flushBuffer();
			buffer=null;
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		} finally {
			if(bufferedOutputStream!=null){
				try {
					bufferedOutputStream.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				}finally{
					try {
						bufferedOutputStream.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
					}finally{
						bufferedOutputStream=null;
					}
				}
			}
			if(cipherOutputStream!=null){
				try {
					cipherOutputStream.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				}finally{
					try {
						cipherOutputStream.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
					}finally{
						cipherOutputStream=null;
					}
				}
			}
			if(bufferedInputStream!=null){
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				}finally{
					bufferedInputStream=null;
				}
			}
		}
	}
	
}