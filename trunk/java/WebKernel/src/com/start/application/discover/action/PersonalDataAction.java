package com.start.application.discover.action;

import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.transaction.Transaction;

import com.start.application.common.action.AbstractServerSuperAction;
import com.start.application.common.entity.FileRecord;
import com.start.application.common.entity.LoginLog;
import com.start.application.common.service.FileRecordService;
import com.start.application.discover.entity.PersonalData;
import com.start.application.discover.entity.Portrait;
import com.start.application.discover.service.PersonalDataService;
import com.start.application.discover.service.PortraitService;
import com.start.application.system.entity.Storage;
import com.start.application.system.service.StorageService;
import com.start.framework.context.annnotation.Controller;
import com.start.framework.context.annnotation.Resource;
import com.start.kernel.config.IMsg;
import com.start.kernel.config.Variable;
import com.start.kernel.http.Download;
import com.start.kernel.utils.FileUtils;
import com.start.kernel.utils.StringUtils;

@Controller("personalData")
public final class PersonalDataAction extends AbstractServerSuperAction {
	
	@Resource
	private FileRecordService fileRecordService;
	@Resource
	private StorageService storageService;
	@Resource
	private PortraitService portraitService;
	@Resource
	private PersonalDataService personalDataService;
	/**
	 * 获取个人信息
	 */
	public void get(){
		LoginLog loginLog=getCurrentLoginLog(true);
		PersonalData personal=new PersonalData();
		personal.setUserCode(loginLog.getUserCode());
		personal.setMybatisMapperId(Variable.LOAD);
		PersonalData pd=personalDataService.load(personal);
		if(pd!=null){
			result(pd);
		}else{
			//账户信息不存在
			result(IMsg.IDiscover._302001);
		}
	}
	/**
	 * 获取好友信息
	 */
	public void getfriend(){
		PersonalData personalData=getRequestUtils().initPostData(PersonalData.class);
		getRequestUtils().signaturesMD5();
		if(StringUtils.isEmpty(personalData.getUserCode())){
			//用户编号不能为空
			result(IMsg.IDiscover._301013);
		}else{
			personalData.setMybatisMapperId(Variable.LOAD);
			PersonalData pd=personalDataService.load(personalData);
			if(pd!=null){
				result(pd);
			}else{
				//账户信息不存在
				result(IMsg.IDiscover._302001);
			}
		}
	}
	/**
	 * 修改
	 */
	public void modify(){
		PersonalData personalData=getRequestUtils().initPostData(PersonalData.class);
		//验证数据
		LoginLog loginLog=getCurrentLoginLog(true);
		personalData.setUserCode(loginLog.getUserCode());
		personalData.setMybatisMapperId(Variable.LOAD);
		PersonalData oldPd=personalDataService.load(personalData);
		if(oldPd==null){
			//添加
			personalData.setMybatisMapperId(Variable.INSERT);
		}else{
			//更新
			personalData.setMybatisMapperId(Variable.UPDATE);
		}
		personalDataService.save(personalData);
		result(IMsg._200);
	}
	/**
	 * 上传头像
	 */
	public void uportrait() throws SQLException{
		getRequestUtils().initDataUpload();
		LoginLog loginLog=getCurrentLoginLog(false,true);
		String fileName=getRequestUtils().getHeaderValue("fileName");
		Integer originalSize=Integer.parseInt(getRequestUtils().getHeaderValue("originalSize"));
		String originalMD5=getRequestUtils().getHeaderValue("originalMD5");
		Integer transportMode=Integer.parseInt(getRequestUtils().getHeaderValue("transportMode"));
		Integer transportSize=Integer.parseInt(getRequestUtils().getHeaderValue("transportSize"));
		String transportMD5=getRequestUtils().getHeaderValue("transportMD5");
		Storage storage=storageService.clientUploadFile(request, fileName, originalSize, originalMD5, transportMode, transportSize, transportMD5);
		Transaction transaction=storageService.getTransaction();
		try{
			storage.setMybatisMapperId(Variable.INSERT);
			storageService.save(storage);
			FileRecord fileRecord=new FileRecord();
			//所属用户编号
			fileRecord.setUserCode(loginLog.getUserCode());
			//设置文件名称
			fileRecord.setName(fileName);
			//获取文件扩展名
			fileRecord.setExtension(FileUtils.getExtension(fileName));
			//设置上传的内容类型
			fileRecord.setContentType(request.getContentType());
			//上传时间
			fileRecord.setUploadTime(new Date());
			//文件的存储编号
			fileRecord.setStorageCode(storage.getCode());
			fileRecord.setMybatisMapperId(Variable.INSERT);
			fileRecordService.save(fileRecord);
			Portrait portrait=new Portrait();
			portrait.setUserCode(loginLog.getCode());
			portrait.setFileCode(fileRecord.getCode());
			portrait.setMybatisMapperId(Variable.INSERT);
			portraitService.save(portrait);
			transaction.commit();
			result(IMsg._200);
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
			result(IMsg._500);
		}finally{
			transaction.close();
		}
	}
	/**
	 * 获取头像
	 */
	public void gportrait(){
		FileRecord fileRecord=getRequestUtils().initPostData(FileRecord.class);
		getRequestUtils().signaturesMD5();
		fileRecord.setMybatisMapperId(Variable.LOAD);
		fileRecord=fileRecordService.load(fileRecord);
		if(fileRecord==null){
			//文件记录不存在
			result(IMsg.ICommon._102009);
		}else{
			Storage storage=new Storage();
			storage.setCode(fileRecord.getStorageCode());
			storage.setMybatisMapperId(Variable.LOAD);
			storage=storageService.load(storage);
			if(storage==null){
				//文件存储信息不存在
				result(IMsg.ICommon._102008);
			}else{
				setResult(new Download(storage,fileRecord.getContentType(),fileRecord.getName()));
			}
		}
	}
	
}