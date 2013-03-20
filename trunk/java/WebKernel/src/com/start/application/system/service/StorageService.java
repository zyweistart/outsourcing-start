package com.start.application.system.service;

import javax.servlet.http.HttpServletRequest;

import com.start.application.system.entity.Storage;
import com.start.framework.controller.interceptor.fileupload.UpLoadFile;
import com.start.kernel.service.RootService;

public interface StorageService extends RootService<Storage, Long> {

	/**
	 * 文件上传必须要提交的值:传输模式,传输文件大小,传输校验码,原始文件大小,原始文件校验码,文件名称
	 * 注:适应于接口访问模式,未对Storage对象进行持久化操作
	 * @param fileName
	 *            文件名称
	 * @param originalSize
	 *            原始的大小存于请求头中
	 * @param originalMD5
	 *            原始的MD5存于请求头中
	 * @param transportMode
	 *            传输模式存于请求头中
	 * @param transportSize
	 *            传输过来的大小存于请求头中
	 * @param transportMD5
	 *            传输过来的MD5存于请求头中
	 */
	Storage clientUploadFile(HttpServletRequest request, String fileName,
			Integer originalSize, String originalMD5, Integer transportMode,
			Integer transportSize, String transportMD5);

	/**
	 * Web端Post表单提交上传文件
	 * 注:适应于网页访问模式,未对Storage对象进行持久化操作
	 */
	Storage webPostUploadFile(UpLoadFile upLoadFile) throws Exception;

}
