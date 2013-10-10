package com.start.core;


/**
 * 常量
 * @author Start
 */
public final class Constant {
	/**
	 * true:测试环境
	 * false:正式环境
	 */
	public static final boolean SYSTEMTEST=false;

	public static final String RESTURL=SYSTEMTEST?
			"http://115.236.89.210:8888/accore/http/HttpService"://公司外网
			"http://server.ancun.com:3391/rest/RestService";//正式地址
	/**
	 * 通行证编号
	 */
	public static String ACCESSID="";
	/**
	 * 通行证密钥
	 */
	public static String ACCESSKEY="";
	/**
	 * 应用默认编码
	 */
	public static final String ENCODE="UTF-8";
	/**
	 * 空字符
	 */
	public static final String EMPTYSTR="";
	
	public final static int LISTVIEW_DATA_MORE = 0x01;
	public final static int LISTVIEW_DATA_LOADING = 0x02;
	public final static int LISTVIEW_DATA_FULL = 0x03;
	public final static int LISTVIEW_DATA_EMPTY = 0x04;
	
	public static final int PAGESIZE=8;
	
	public final class BundleConstant{
		/**
		 * 自动登录标记
		 */
		public static final String BUNLE_AUTOLOGINFLAG="autoLoginFlag";
		/**
		 * 退出信息
		 */
		public static final String BUNLE_EXIT_MSG="exitmsg";
	}
	
	/**
	 * SharedPreferences常量
	 * @author Start
	 */
	public final class SharedPreferencesConstant{
		
	}
	/**
	 * 用户接口
	 * @author Start
	 */
	public final class GlobalURL{
		
	}
}