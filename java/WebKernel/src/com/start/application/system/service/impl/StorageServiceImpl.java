package com.start.application.system.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.start.application.system.dao.StorageDao;
import com.start.application.system.entity.Storage;
import com.start.application.system.service.StorageService;
import com.start.framework.config.AppConstant;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.framework.controller.interceptor.fileupload.UpLoadFile;
import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.config.IMsg;
import com.start.kernel.config.SystemPath;
import com.start.kernel.config.Variable;
import com.start.kernel.exception.AppRuntimeException;
import com.start.kernel.service.impl.RootServiceImpl;
import com.start.kernel.utils.FileUtils;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.MD5;
import com.start.kernel.utils.StringUtils;
import com.start.kernel.utils.TimeUtils;


@Service("storageService")
public final class StorageServiceImpl extends RootServiceImpl<Storage,Long> 
implements StorageService {
	
	public StorageServiceImpl(@Qualifier("storageDao")StorageDao storageDao) {
		super(storageDao);
	}

	@Override
	public Storage clientUploadFile(HttpServletRequest request, String fileName,
			Integer originalSize, String originalMD5, Integer transportMode,
			Integer transportSize, String transportMD5) {
		if(transportSize==request.getContentLength()){
			//生成临时保存时的文件名称
			String tmpStorageFileName=StringUtils.random(transportMD5);
			try{
				//把输入流中的数据存储到本地的临时目录中返回该文件的MD5值
				if(transportMD5.equals(FileUtils.generateMD5(AppConstant.TMPPATH,tmpStorageFileName,request.getInputStream()))){
					Storage storage=new Storage();
					storage.setOriginalSize(originalSize);
					storage.setOriginalMD5(originalMD5);
					//加密模式
					storage.setEncrypt(ConfigParameter.DATA_ENCRYPT_MODE);
					//存储模式
					storage.setStorage(ConfigParameter.DATA_STORAGE_MODE);
					//保存的文件名称
					storage.setName(tmpStorageFileName);
					//存储空间默认格式为:yyyymmdd
					storage.setSpace(TimeUtils.getSysdateInt());
					storage.setDirectory(TimeUtils.getSysdateInt());
					String fullStoragePath=SystemPath.DATA_PATH+storage.getSpace()+AppConstant.FILESEPARATOR;
					if(transportMode.equals(Variable.TransportMode_NO)){
						//未压缩则传输MD5与原始MD5相同，大小也相同
						if(storage.getOriginalMD5().equals(transportMD5)){
							if(!transportSize.equals(storage.getOriginalSize())){
								//文件大小与原始文件不一致
								throw new AppRuntimeException(IMsg.ICommon._102003);
							}
						}else{
							//传输文件与原始文件校验码不相同
							throw new AppRuntimeException(IMsg.ICommon._102004);
						}
					}else if(transportMode.equals(Variable.TransportMode_GZIP)){
						String[] ms=FileUtils.decompressGZIP(AppConstant.TMPPATH+tmpStorageFileName);
						if(ms!=null){
							if(storage.getOriginalMD5().equals(ms[0])){
								if(!storage.getOriginalSize().equals(StringUtils.nullToIntZero(ms[1]))){
									//文件大小与原始文件不一致
									throw new AppRuntimeException(IMsg.ICommon._102003);
								}
							}else{
								//文件校验码与原始文件不一致
								throw new AppRuntimeException(IMsg.ICommon._102006);
							}
						}else{
							//GZIP解压时发生异常
							throw new AppRuntimeException(IMsg.ICommon._102005);
						}
					}
					String[] ms=FileUtils.storage(AppConstant.TMPPATH+tmpStorageFileName,fullStoragePath, storage.getName(),transportMode);
					if(ms!=null){
						//存储文件的MD5
						storage.setStorageMD5(ms[0]);
						//存储文件的大小
						storage.setStorageSize(StringUtils.nullToIntZero(ms[1]));
						//存储至阿里云
						//正式系统中会根据具体的配置把文件存储到指定的地方
						if(ConfigParameter.SYSTEMFLAG){
							//文件存储到阿里云
							if(Variable.StorageMode_ALIYUN.equals(storage.getStorage())){
								FileUtils.storageByAliyun(storage, fileName, fullStoragePath);
							}
						}
						return storage;
					}
				}else{
					//文件校验码不正确
					throw new AppRuntimeException(IMsg.ICommon._102002);
				}
			} catch (Exception e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			}
			//文件存储时出现异常
			throw new AppRuntimeException(IMsg.ICommon._102007);
		}else{
			//请求数据大小与传输文件大小不一致
			throw new AppRuntimeException(IMsg.ICommon._102001);
		}
	}

	@Override
	public Storage webPostUploadFile(UpLoadFile upLoadFile) throws Exception {
		String storageSpace=TimeUtils.getSysdateInt();
		String storageName=StringUtils.random();
		//完整的存储目录路径
		String fullStoragePath=SystemPath.DATA_PATH+storageSpace+AppConstant.FILESEPARATOR;
		String[] ms=FileUtils.storage(upLoadFile.getFile().getAbsolutePath(),fullStoragePath, storageName,Variable.TransportMode_NO);
		if(ms!=null){
			Storage storage=new Storage();
			//生成随机保存文件名
			storage.setName(storageName);
			//存储空间默认格式为:yyyymmdd
			storage.setSpace(storageSpace);
			storage.setDirectory(TimeUtils.getSysdateInt());
			//加密模式
			storage.setEncrypt(ConfigParameter.DATA_ENCRYPT_MODE);
			//存储模式
			storage.setStorage(ConfigParameter.DATA_STORAGE_MODE);
			//原始文件的MD5
			storage.setOriginalMD5(MD5.md5file(upLoadFile.getFile().getAbsolutePath()));
			//原始文件的大小
			storage.setOriginalSize(Integer.parseInt(String.valueOf(upLoadFile.getFileSize())));
			//存储文件的MD5
			storage.setStorageMD5(ms[0]);
			//存储文件的大小
			storage.setStorageSize(StringUtils.nullToIntZero(ms[1]));
			//存储至阿里云
			//正式系统中会根据具体的配置把文件存储到指定的地方
			if(ConfigParameter.SYSTEMFLAG){
				//文件存储到阿里云
				if(Variable.StorageMode_ALIYUN.equals(storage.getStorage())){
					FileUtils.storageByAliyun(storage, upLoadFile.getName(), fullStoragePath);
				}
			}
			return storage;
		}
		return null;
	}
	
}