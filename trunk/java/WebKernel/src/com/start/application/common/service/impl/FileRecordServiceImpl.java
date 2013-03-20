package com.start.application.common.service.impl;

import java.util.Date;

import org.apache.ibatis.transaction.Transaction;

import com.start.application.common.dao.FileRecordDao;
import com.start.application.common.entity.FileRecord;
import com.start.application.common.service.FileRecordService;
import com.start.application.system.entity.Storage;
import com.start.application.system.service.StorageService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Resource;
import com.start.framework.context.annnotation.Service;
import com.start.framework.controller.interceptor.fileupload.UpLoadFile;
import com.start.kernel.service.impl.RootServiceImpl;


@Service("fileRecordService")
public final class FileRecordServiceImpl extends RootServiceImpl<FileRecord,Long> 
implements FileRecordService {

	@Resource
	private StorageService storageService;
	
	public FileRecordServiceImpl(@Qualifier("fileRecordDao")FileRecordDao recordDao) {
		super(recordDao);
	}

	@Override
	public FileRecord webPostUploadFile(UpLoadFile upLoadFile,String userCode) throws Exception {
		Storage storage=storageService.webPostUploadFile(upLoadFile);
		if(storage!=null){
			Transaction transaction=getTransaction();
			try{
				storage.setMybatisMapperId("storage");
				storageService.save(storage);
				FileRecord fileRecord=new FileRecord();
				//所属用户编号
				fileRecord.setUserCode(userCode);
				//设置文件名称
				fileRecord.setName(upLoadFile.getName());
				//获取文件扩展名
				fileRecord.setExtension(upLoadFile.getExtension());
				//设置上传的内容类型
				fileRecord.setContentType(upLoadFile.getContentType());
				//上传时间
				fileRecord.setUploadTime(new Date());
				//文件的存储编号
				fileRecord.setStorageCode(storage.getCode());
				fileRecord.setMybatisMapperId("storage");
				super.save(fileRecord);
				transaction.commit();
				return fileRecord;
			}catch(Exception e){
				transaction.rollback();
			}finally{
				transaction.close();
			}
		}
		return null;
	}

}
