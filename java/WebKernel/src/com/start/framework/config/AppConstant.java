package com.start.framework.config;



/**
 * 框架配置的常量
 * @author Start
 */
public final class AppConstant {
	/**
	 * 开发模式
	 * <pre>
	 * 可选值：
	 * 	true: 会打印一些帮助信息
	 * 	false:关闭打印信息
	 * </pre>
	 */
	public static final Boolean DEVMODE=Boolean.parseBoolean(AppConfig.Constants.get("DEVMODE"));
	/**
	 * 默认编码格式 
	 *	<pre>
	 *	GBK、UTF-8
	 *	</pre>
	 */
	public static final String ENCODING=AppConfig.Constants.get("ENCODING");
	/**
	 * 需要自动打扫的类路径
	 * <pre>
	 * 	类的路径示例：com.start.framework则打扫该包下的所有类包概所有子包
	 * </pre>
	 */
	public static final String CLASSSCANPATH=AppConfig.Constants.get("CLASSSCANPATH");
	/**
	 * 数据保存主路径
	 */
	public static final String ROOTPATH=AppConfig.Constants.get("ROOTPATH");
	/**
	 * 临时文件目录
	 */
	public static final String TMPPATH=ROOTPATH+AppConfig.Constants.get("TMPPATH");
	/**
	 * 文件上传大小限制BYTE为单位
	 */
	public static final Integer MAXUPLOADSIZE=Integer.parseInt(AppConfig.Constants.get("MAXUPLOADSIZE"));
	/**
	 * 允许上传的文件类型"*"代表允许所有
	 * <pre>
	 * 可选值：*或其他文件类型
	 * </pre>
	 */
	public static final String[] ALLOWUPLOADTYPES=AppConfig.Constants.get("ALLOWUPLOADTYPES").trim().split(",");
	
	public static final String FILESEPARATOR = System.getProperty("file.separator");
	
}