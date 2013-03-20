package com.start.test.discover;

import com.start.kernel.utils.MD5;
import com.start.test.BaseCls;

public class AccountTest extends BaseCls{
	/**
	 * 检测电子邮件是否已经被注册
	 */
	public void testCheckEmail() throws Exception{
		url="account/checkEmail.do";
		params.put("email", "4544242@qq.com");
		accessKey=null;
		sendRequest();
	}
	/**
	 * 注册
	 */
	public void testRegister() throws Exception{
		url="account/register.do";
		params.put("email", "4544242@qq.com");
		params.put("password", MD5.md5("19890624"));
		accessKey=null;
		sendRequest();
	}
	/**
	 * 登陆
	 */
	public void testLogin() throws Exception{
		url="account/login.do";
		params.put("email", "4544242@qq.com");
		accessKey=MD5.md5("19890624");
		sendRequest();
	}
	/**
	 * 修改密码
	 */
	public void testModifyPwd() throws Exception{
		url="account/modifyPwd.do";
		params.put("oldpwd", MD5.md5("19890624"));
		params.put("newpwd", MD5.md5("4544242"));
		sendRequest();
	}
	/**
	 * 退出
	 */
	public void testLogout() throws Exception{
		testLogin();
		url="account/logout.do";
		sendRequest();
	}
}