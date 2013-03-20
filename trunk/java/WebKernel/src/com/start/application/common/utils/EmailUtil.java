package com.start.application.common.utils;

import com.start.kernel.config.ConfigParameter;
import com.start.kernel.utils.SMTP;

/**
 * 邮件发送
 * @author Start
 */
public class EmailUtil {
	
	private static final SMTP smtp = new SMTP(
			ConfigParameter.SMTPHOST,
			ConfigParameter.SMTPUSERNAME,
			ConfigParameter.SMTPPASSWORD);
	

	public static void send(String to,String subject,String content){
		smtp.send(ConfigParameter.SMTPFROM,to,subject,content);
	}
	/**
	 * 账户注册激活邮件
	 */
	public static void sendAccountRegister(String email,String identifier){
		send(email,"网上工作","账户激活"+identifier);
	}
	/**
	 * 账户忘记密码邮件
	 */
	public static void sendAccountForgetPassword(String email,String identifier){
		send(email,"网上工作","亲是不是忘记密码了啊"+identifier);
	}
	/**
	 * 系统管理员发邮件
	 */
	public static void sendAlarmEmail(String subject,String content){
		send(ConfigParameter.SYSTEMEMAIL,subject,content);
	}

}