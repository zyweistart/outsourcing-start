package com.start.application.common.service;

import com.start.application.common.entity.FileRecord;
import com.start.framework.controller.interceptor.fileupload.UpLoadFile;
import com.start.kernel.service.RootService;

public interface FileRecordService extends RootService<FileRecord, Long> {

	/**
	 * Web端Post表单提交上传文件
	 */
	FileRecord webPostUploadFile(UpLoadFile upLoadFile,String userCode) throws Exception;

}
