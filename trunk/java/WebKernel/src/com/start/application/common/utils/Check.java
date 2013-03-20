package com.start.application.common.utils;

import com.start.application.common.entity.Account;
import com.start.kernel.config.IMsg;
import com.start.kernel.exception.AppRuntimeException;
/**
 * 实体业务检测类
 * @author Start
 */
public class Check {

	public static Account checkAccount(Account account){
		if(account==null){
			//账户不存在
			throw new AppRuntimeException(IMsg.IDiscover._301001);
		}
		if(account.getPwdModifyFlag()!=1){
			//请先修改密码
			throw new AppRuntimeException(IMsg.IDiscover._301002);
		}
		if(account.getStatus()!=1){
			//账户存在异常
			throw new AppRuntimeException(IMsg.IDiscover._301003);
		}
		return account;
	}
	
}
