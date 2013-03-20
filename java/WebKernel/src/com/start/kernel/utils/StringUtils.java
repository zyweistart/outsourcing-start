package com.start.kernel.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.start.framework.config.AppConstant;
import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.IMsg;
import com.start.kernel.config.SystemPath;
import com.start.kernel.config.Variable;
import com.start.kernel.exception.AppRuntimeException;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

/**
 * 字符串处理类
 * @author Start
 */
public class StringUtils {
	
	public static Boolean isEmpty(String str){
		return str==null||str.isEmpty();
	}
	
	public static Boolean isNotEmpty(String str){
		return str!=null&&!str.isEmpty();
	}
	
	public static boolean isBlank(String str) {
    	int length = 0;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
	/**
	 * 读取字符串的字节长度
	 */
	public static int getRealLength(String str) {
    	if(isEmpty(str)) {
    		return 0;
    	}
    	return str.getBytes().length;
    }
	/**
	 * 字符NULL转""
	 */
    public static String nullToStr(String str) {
    	if (str == null) {
        	str = "";
        }
        return str;
    }
    /**
     * 字符NULL转""并去掉左右空格
     */
    public static String nullToStrTrim(String str) {
    	if (str == null) {
        	str = "";
        }
        return str.trim();
    }
    /**
     * 0=false
     * 1=true
     */
    public static boolean nullToBoolean(String str) {
    	if (str == null || str.trim().length() == 0) {
        	return false;
        }
    	if("0".equals(str)){
    		return false;
    	}else if("1".equals(str)){
    		return true;
    	}
        return Boolean.valueOf(str.trim());
    }
    
    public static int nullToIntZero(String str) {
    	if (str == null || str.trim().length() == 0) {
        	str = "0";
        }
        return Integer.valueOf(str.trim(), 10);
    }

    public static long nullToLongZero(String str) {
    	if (str == null || str.trim().length() == 0) {
        	str = "0";
        }
        return Long.valueOf(str.trim(), 10);
    }
    
    public static double nullToDoubleZero(String str) {
    	if (str == null || str.trim().length() == 0) {
        	str = "0.0";
        }
        return Double.valueOf(str.trim());
    }

    public static String nullToUnknown(String str) {
        if (isEmpty(str)) {
        	str = Variable.UNKNOWN;
        }
        return str.trim();
    }
    /**
     * 字符空转HTML的&nbsp;
     */
    public static String nullToHtmlSpace(String str) {
    	if (str == null || str.length() == 0) {
        	str = "&nbsp;";
        }
        return str;
    }
    /**
     * 字符空转HTML的&nbsp;去左右空格
     */
    public static String nullToHtmlSpaceTrim(String str) {
    	if (str == null || str.trim().length() == 0) {
        	str = "&nbsp;";
        }
        return str.trim();
    }
    
	public static String encodeHTML(String str) {
		if(str == null || str.length() == 0) {
     		return "";
     	}
     	char content[] = new char[str.length()];
     	str.getChars(0, str.length(), content, 0);
     	StringBuffer stringBuffer = new StringBuffer();
     	for (int i = 0; i < content.length; i++) {
     		switch (content[i]) {
     			case '<':
     				stringBuffer.append("&lt;");
     				break;
                case '>':
                	stringBuffer.append("&gt;");
                    break;
                case '&':
                	stringBuffer.append("&amp;");
                    break;
                case '"':
                	stringBuffer.append("&quot;");
                    break;
                default:
                	stringBuffer.append(content[i]);
     		}
     	}
     	return stringBuffer.toString();
	}

	public static String nullToHtmlSpaceTrimEncode(String str) {
		return nullToHtmlSpaceTrim(encodeHTML(str));
	}

    public static String addQuote(String str) {
        str = "'" + encodeSingleQuotedString(str) + "'";
        return str;
    }

    public static String encodeSingleQuotedString(String str) {
    	str = nullToStr(str);
    	if(str.length() == 0) {
    		return str;
    	} else {
            StringBuffer stringBuffer = new StringBuffer(128);
            for(int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if(c == '\'')
                	stringBuffer.append("''");
                else
                	stringBuffer.append(c);
            }
            return stringBuffer.toString();
    	}
    }
    /**
	 * HmacSHA1签名
	 */
	public static String signatureHmacSHA1(String data,String key){
		try {
			return new String(Base64.encode(HmacSHA1.signature(data, key,AppConstant.ENCODING)));
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._2007);
		}
	}
    /**
     * DES加密
     */
    public static String doKeyEncrypt(String str){
    	try {
			return DES.encrypt(str, SystemPath.DESKEYKEY);
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._1001);
		}
	}
    /**
     * DES解密
     */
	public static String doKeyDecrypt(String str){
		try {
			return DES.decrypt(str, SystemPath.DESKEYKEY);
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._1002);
		}
	}
    /**
     * URL编码
     */
	public static String encode(String str) {
		return encode(str,AppConstant.ENCODING);
	}
	/**
     * URL编码
     */
	public static String encode(String str, String enc) {
		String strEncode = "";
		try {
			if (str != null){
				strEncode = URLEncoder.encode(str, enc);
			}
		} catch (UnsupportedEncodingException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._1003);
		}
		return strEncode;
	}
	/**
	 * URL解码
	 */
	public static String decode(String str) {
		return decode(str,AppConstant.ENCODING);
	}
	/**
	 * URL解码
	 */
	public static String decode(String str, String enc) {
		String strDecode = "";
		try {
			if (str != null){
				strDecode = URLDecoder.decode(str, enc);
			}
		} catch (UnsupportedEncodingException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._1004);
		}
		return strDecode;
	}

	public static String convertCharset(String str, String charsetNameFrom, String charsetNameTo) {
		str = nullToStrTrim(str);
		if(isNotEmpty(str)) {
			try {
				str = new String(str.getBytes(charsetNameFrom),charsetNameTo);
			} catch (UnsupportedEncodingException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}
		return str;
	}

	public static String encode(HttpServletRequest request, String str) {
		String user_agent = nullToStrTrim(request.getHeader("User-Agent"));
		try {
			if (user_agent.indexOf("MSIE") >= 0) {
				str = convertCharset(str, "GBK", "ISO8859-1");
			} else if (user_agent.indexOf("Firefox") >= 0) {
				str = MimeUtility.encodeText(str, "UTF-8", "B");
			} else {
				str = encode(str,AppConstant.ENCODING);
			}
		} catch (UnsupportedEncodingException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		}
		return str;
	}

	public static String decimalFormat(String str, String pattern) {
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(Double.valueOf(nullToDoubleZero(str)));
	}

	public static String decimalFormat(Double num, String pattern) {
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(num);
	}
    
    public static int getLength(String str){
    	int length =0;
    	if (isNotEmpty(str)) {
			for (int i = 0; i < str.length(); i++) {
				if (str.substring(i, i + 1).matches("[\\u4e00-\\u9fbb]+")) {
					length = length + 2;
				} else {
					length = length + 1;
				}
			}
		}
    	return length;
    }

    public static int getWordLength(String str) {
        str = nullToStr(str);
        return str.replaceAll("[^\\x00-\\xff]","**").length();
    }

    public static boolean isHalfAngle(String str) {
    	str = nullToStrTrim(str);
    	return str.length() == getWordLength(str);
    }

    public static String rightQuote(String str, int length) {
		for (int i = 0; i < length; i++) {
			str = str + formatASCIIToString("0");
		}
		return str;
	}
    
    public static String formatASCIIToString(String s) {
		StringBuffer stringBuffer = new StringBuffer();
		String[] chars = s.split(" ");
		for (int i = 0; i < chars.length; i++) {
			stringBuffer.append((char) Integer.parseInt(chars[i], 16));
		}
		return stringBuffer.toString();
	}

    public static long convertSize(String strSize) {
		strSize = nullToStrTrim(strSize.toUpperCase());
		int tmp = 0;
		if(!strSize.equalsIgnoreCase(Variable.UNKNOWN) && strSize.length() > 2) {
			tmp = nullToIntZero(strSize.substring(0, strSize.length() - 2));
		}
		long size = 0;
		if(strSize.endsWith("KB")) {
			size = Variable.Byte_Hex * tmp;
		} else if(strSize.endsWith("MB")) {
			size = Variable.Byte_Hex * Variable.Byte_Hex * tmp;
		} else if(strSize.endsWith("GB")) {
			size = Variable.Byte_Hex * Variable.Byte_Hex * Variable.Byte_Hex * tmp;
		}else{
			size=Long.parseLong(strSize);
		}
		return size;
	}

    public static String convertSize(long size) {
    	int i = 0;
    	double tmpSize = size;
    	if(tmpSize < 0) {
    		tmpSize = 0;
    	}
    	while(i <= 5 && tmpSize >= Variable.Byte_Hex) {
    		i ++;
        	tmpSize = tmpSize / Variable.Byte_Hex;
    	}
		return decimalFormat(tmpSize, "#0.##") + Variable.SIZEUNITS[i];
	}

    public static String convertSize(long size, int hex) {
    	return convertSize(size * hex);
    }

    public static String convertKBSize(long size) {
    	return convertSize(size * Variable.Byte_Hex);
    }

	public static String leftPad(String str, int length, String padchar) {
		return String.format("%" + length + "s", str).replaceAll(" ", padchar);
	}
	/**
	 * 获取当前的UUID值
	 * 36位值：f6f457e6-cba4-49c8-88a8-1e5cde733907
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	/**
	 * 生成随机数
	 * 32位值：6fc089dfbbac4a76989e353d1397da3d
	 */
	public static String random(){
		return random("");
	}
	/**
	 * 生成随机数
	 * 32位值：6fc089dfbbac4a76989e353d1397da3d
	 */
	public static String random(String input){
		Random ra=new Random();
		return MD5.md5(getUUID() + System.currentTimeMillis() + ra.nextInt(999999999) + input);
	}
	/**
	 * 统一路径定位
	 */
	public static String unifiedPath(String path) {
		return path.replaceAll("/", "\\");
	}
	/**
	 * List对象转换为字符串并以space分隔
	 */
	public static String listToString(List<String> list, String space) {
		StringBuilder stringBuilder = new StringBuilder();
		if (list!=null) {
			for (String l : list) {
				stringBuilder.append(l + space);
				l=null;
			}
		}
		return stringBuilder.toString();
	}
	/**
	 * 把中文转成Unicode码
	 */
	public static String chinaToUnicode(String str){
		String result="";
		for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
            	result+=str.charAt(i);
            }
        }
		return result;
	}

	/**
	 * 判断是否为中文字符
	 */
	public static  boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
	
}