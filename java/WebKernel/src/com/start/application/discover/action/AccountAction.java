package com.start.application.discover.action;

import java.util.Date;

import com.start.application.common.action.AbstractServerSuperAction;
import com.start.application.common.entity.Account;
import com.start.application.common.entity.LoginLog;
import com.start.application.common.utils.Check;
import com.start.framework.context.annnotation.Controller;
import com.start.kernel.config.Business;
import com.start.kernel.config.IMsg;
import com.start.kernel.config.Variable;
import com.start.kernel.utils.StringCheck;
import com.start.kernel.utils.StringUtils;
import com.start.kernel.utils.TimeUtils;
/**
 * 账户
 * @author Start
 */
@Controller("account")
public class AccountAction extends AbstractServerSuperAction {
	/**
	 * 检测邮箱是否已经存在
	 */
	public void checkEmail(){
		Account account=getRequestUtils().initPostData(Account.class);
		getRequestUtils().signaturesMD5();
		if(StringUtils.isEmpty(account.getEmail())){
			//电子邮件不能为空
			result(IMsg.IDiscover._301004);
		}else if(!StringCheck.checkEmail(account.getEmail())){
			//邮件格式不正确
			result(IMsg.IDiscover._301005);
		}else{
			account.setMybatisMapperId(Variable.LOAD);
			if(accountService.load(account)==null){
				result(IMsg._200);
			}else{
				//邮箱已存在
				result(IMsg.IDiscover._301006);
			}
		}
	}
	/**
	 * 注册
	 */
	public void register(){
		Account account=getRequestUtils().initPostData(Account.class);
		getRequestUtils().signaturesMD5();
		if(StringUtils.isEmpty(account.getEmail())){
			//电子邮件不能为空
			result(IMsg.IDiscover._301004);
		}else if(!StringCheck.checkEmail(account.getEmail())){
			//邮件格式不正确
			result(IMsg.IDiscover._301005);
		}else if(StringUtils.isEmpty(account.getPassword())){
			//密码不能为空
			result(IMsg.IDiscover._301007);
		}else if(!StringCheck.checkMd5(account.getPassword())){
			//密码格式不正确
			result(IMsg.IDiscover._301008);
		}else{
			account.setMybatisMapperId(Variable.LOAD);
			if(accountService.load(account)==null){
				//生成系统的唯一ID
				account.setCode(StringUtils.random(account.getEmail()));
				account.setPassword(StringUtils.doKeyEncrypt(account.getPassword()));
				account.setRegisterTime(new Date());
				account.setMybatisMapperId("register");
				accountService.save(account);
				result(IMsg._200);
			}else{
				//邮箱已存在
				result(IMsg.IDiscover._301006);
			}
		}
	}
	/**
	 * 登陆
	 */
	public void login(){
		Account account=getRequestUtils().initPostData(Account.class);
		if(StringUtils.isEmpty(account.getEmail())){
			//电子邮件不能为空
			result(IMsg.IDiscover._301004);
		}else if(!StringCheck.checkEmail(account.getEmail())){
			//邮件格式不正确
			result(IMsg.IDiscover._301005);
		}else{
			account.setMybatisMapperId(Variable.LOAD);
			account=accountService.load(account);
			Check.checkAccount(account);
			//对密码解码后进行签名
			getRequestUtils().signaturesHmacSHA1(StringUtils.doKeyDecrypt(account.getPassword()));
			LoginLog loginLog=new LoginLog();
			loginLog.setUserCode(account.getCode());
			//使之前的登陆日志信息全部失效
			loginLog.setMybatisMapperId("loginLonger");
			loginLogService.save(loginLog);
			loginLog=new LoginLog();
			loginLog.setUserCode(account.getCode());
			loginLog.setAccessId(StringUtils.random(account.getCode()));
			loginLog.setAccessKey(StringUtils.doKeyEncrypt(loginLog.getAccessId()));
			loginLog.setLoginTime(new Date());
			loginLog.setInvalidTime(TimeUtils.AddHour(Business.USERLOGINLOG_ACTIVATION_HOUR));
			loginLog.setMybatisMapperId("login");
			loginLogService.save(loginLog);
			LoginLog result=new LoginLog();
			result.setAccessId(loginLog.getAccessId());
			result.setAccessKey(loginLog.getAccessKey());
			result(result);
		}
	}
	/**
	 * 修改密码
	 */
	public void modifyPwd(){
		String oldpwd=getRequestUtils().getXMLDocument().get("oldpwd");
		String newpwd=getRequestUtils().getXMLDocument().get("newpwd");
		if(oldpwd.equals(newpwd)){
			//两次密码相同
			result(IMsg.IDiscover._301012);
		}else if(StringUtils.isEmpty(oldpwd)){
			//旧密码不能为空
			result(IMsg.IDiscover._301009);
		}else if(!StringCheck.checkMd5(oldpwd)){
			//旧密码格式不正确
			result(IMsg.IDiscover._301010);
		}else if(StringUtils.isEmpty(newpwd)){
			//密码不能为空
			result(IMsg.IDiscover._301007);
		}else if(!StringCheck.checkMd5(newpwd)){
			//密码格式不正确
			result(IMsg.IDiscover._301008);
		}else{
			LoginLog loginLog=getCurrentLoginLog(true);
			if(oldpwd.equals(StringUtils.doKeyDecrypt(loginLog.getAccount().getPassword()))){
				Account account=new Account();
				account.setCode(loginLog.getUserCode());
				account.setPassword(StringUtils.doKeyEncrypt(newpwd));
				account.setMybatisMapperId("modifyPwd");
				accountService.save(account);
				result(IMsg._200);
			}else{
				//密码不正确
				result(IMsg.IDiscover._301011);
			}
		}
	}
	/**
	 * 退出
	 */
	public void logout(){
		LoginLog loginLog=getCurrentLoginLog(false);
		loginLog.setLoginTime(new Date());
		loginLog.setLoginRemark("客户端正常退出");
		loginLog.setMybatisMapperId("logout");
		loginLogService.save(loginLog);
		result(IMsg._200);
	}
	/**
	 * 发送激活邮件
	 */
	public void sendActivateEmail(){
		
	}
	/**
	 * 绑定邮件
	 */
	public void bindEmail(){
		
	}
	/**
	 * 忘记密码
	 */
	public void forgetPwd(){
		
	}
	/**
	 * 重置密码
	 */
	public void resetPwd(){
		
	}
}