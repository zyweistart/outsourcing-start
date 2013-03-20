package com.start.kernel.utils;

import java.util.Date;

import sun.net.util.IPAddressUtil;

import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.IMsg;
import com.start.kernel.exception.AppRuntimeException;

/**
 * 字符数据格式检测类
 * @author Start
 */
public class StringCheck {
	 /**
     * 正则表达式检测字符串格式
     */
	public static boolean checkString(String str, String regex) {
    	return str.matches(regex);
	}
	 /**
     * 电子邮件
     */
    public static boolean checkEmail(String email) {
    	email = StringUtils.nullToStrTrim(email);
    	if(StringUtils.isEmpty(email)) {
    		return false;
    	}
    	if(email.length() < 5) {
    		return false;
    	}
    	String regex = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
    	return email.matches(regex);
    }
    /**
     * 移动号码
     */
    public static boolean checkMobile(String mobile) {
    	mobile = StringUtils.nullToStrTrim(mobile);
    	if(mobile.length() != 11) {
    		return false;
    	}
    	String regex = "^(1[3,5,8][0-9])\\d{8}$";
    	return mobile.matches(regex);
    }
    /**
     * 电话号码
     */
    public static boolean checkPhone(String phone) {
    	phone = StringUtils.nullToStrTrim(phone);
    	if(phone.length() != 11 && phone.length() != 12) {
    		return false;
    	}
    	String regex = "[0-9]+";
    	return phone.matches(regex);
    }
    /**
     * IP地址
     */
    public static boolean checkIp(String ip) {
    	if(StringUtils.isEmpty(ip) ||StringUtils.isBlank(ip)) {
    		return false;
    	}
    	if(ip.length() < 7) {
    		return false;
    	}
    	if(!checkString(ip, "[0-9.]+")) {
    		return false;
    	}
		return IPAddressUtil.isIPv4LiteralAddress(ip) || IPAddressUtil.isIPv6LiteralAddress(ip);
    }
    /**
     * Mac地址
     */
    public static boolean checkMac(String mac) {
    	if(StringUtils.isEmpty(mac) || StringUtils.isBlank(mac)) {
    		return false;
    	}
    	if(mac.length() != 17) {
    		return false;
    	}
    	return checkString(mac, "[0-9A-Fa-f]{2}-[0-9A-Fa-f]{2}-[0-9A-Fa-f]{2}-[0-9A-Fa-f]{2}-[0-9A-Fa-f]{2}-[0-9A-Fa-f]{2}");
    }
	/**
	 * 检测时间格式
	 */
	public static boolean checkTime(String time) {
		if(time.length() != 14) {
			return false;
		}
		if(!checkString(time, "[2][0-9]+")) {
			return false;
		}
		return TimeUtils.validTime(time, "yyyyMMddHHmmss");
	}
	/**
	 * 检测MD5的格式
	 */
	public static boolean checkMd5(String md5) {
		if(md5.length() != 32) {
			return false;
		}
		return checkString(md5, "[0-9A-Fa-f]+");
	}
    
	public static boolean checkCoordinates(String str) {
		if(str.length() > 50 || !StringUtils.isHalfAngle(str)) {
			return false;
		}
    	String regex = "[0-9]+_[0-9]+-[0-9]+_[0-9]+";
		return str.matches(regex);
	}
	
	public static boolean checkDigit(String digit) {
		digit = StringUtils.nullToStrTrim(digit);
    	if(StringUtils.isEmpty(digit) || StringUtils.isBlank(digit) || !digit.matches("[0-9]+")) {
    		return false;
    	}
    	return true;
	}
	
	public static boolean checkDigit(String digit, int maxlength, int max) {
		digit = StringUtils.nullToStrTrim(digit);
		if(digit.length() > maxlength || !checkString(digit, "[0-9]+")) {
			return false;
		}
		if(StringUtils.nullToIntZero(digit) > max) {
			return false;
		}
    	return true;
	}
	/**
	 * 金额
	 */
	public static boolean checkMoney(String money) {
		money = StringUtils.nullToStrTrim(money);
		if(money.length() > 20) {
			return false;
		}
		if(StringUtils.isBlank(money) || money.split(".").length > 2 || !money.replace(".", "").matches("[0-9]+")) {
			return false;
    	}
    	if(money.indexOf(".") > 0) {
    		String decimal = money.substring(money.indexOf(".") + 1);
    		if(decimal.length() >= 3) {
    			if(decimal.substring(2).replaceAll("0", "").length() > 0) {
    				return false;
    			}
    		}
    	}
		double moneyD = 0.0D;
    	try{
    		moneyD = Double.valueOf(money);
        } catch(NumberFormatException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			return false;
        }
    	if(moneyD != 0.0D && (moneyD < 0.01D|| moneyD > 1000000000.00D)) {
			return false;
    	}
    	return true;
	}
	/**
	 * 是否为数字
	 */
	public static boolean checkNumber(String number) {
		number = StringUtils.nullToStrTrim(number);
    	if(StringUtils.isBlank(number) || number.split(".").length > 2 || !number.replace(".", "").matches("[0-9]+")) {
    		return false;
    	}
    	try {
        	Double.valueOf(number);
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
    		return false;
		}
    	return true;
	}
	/**
	 * 判断是否为中文
	 */
	@SuppressWarnings("unused")
	public static boolean isChinese(String str){
    	boolean isChinese = false;
    	if (StringUtils.isNotEmpty(str)) {
			for (final int i = 0; i < str.length(); i++) {
				if (!str.substring(i, i+1).matches("[\\u4e00-\\u9fbb]+")) {
					return isChinese;
				} else {
					return true;
				}
			}
		}
    	return isChinese;
    }
	/**
	 * <pre>
	 * @param checkData
	 * 被检测的数据中不能出现;符号
	 * </pre>
	 */
	public static boolean checkConstantConfig(String constantconfig, String checkData){
		return checkData.indexOf(";") == -1 && (";" + constantconfig + ";").indexOf(";" + checkData + ";") >= 0;
	}
	/**
	 * <pre>
	 * 检测属性文件中的业务数据是否正确
	 * @param checkData
	 * 被检测的数据中不能出现;符号
	 * </pre>
	 */
	public static boolean checkBusinessConfig(String configKey, String checkData){
		return checkData.indexOf(";") == -1 && (";" + PropertiesUtils.getBusines(configKey) + ";").indexOf(";" + checkData + ";") >= 0;
	}
	/**
	 * 检测请求时间是否已经超时
	 * @param requesttime
	 * 请求时间
	 * @param timeoutsecond
	 * 超时时间
	 */
	public static void checkRequesttime(String requesttime, int timeoutsecond) {
		if(StringUtils.isEmpty(requesttime)) {
			throw new AppRuntimeException(IMsg._2008);
		}
		if(!StringCheck.checkTime(requesttime)) {
			throw new AppRuntimeException(IMsg._2009);
		}
		Date requestTime = TimeUtils.format(requesttime, TimeUtils.yyyyMMddHHmmss);
		if(requestTime == null) {
			throw new AppRuntimeException(IMsg._2009);
		}
		if(TimeUtils.getSecond(-timeoutsecond).after(requestTime) || TimeUtils.getSecond(timeoutsecond).before(requestTime)) {
			throw new AppRuntimeException(IMsg._2010);
		}
	}
}
