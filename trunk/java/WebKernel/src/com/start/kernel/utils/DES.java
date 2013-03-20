package com.start.kernel.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.Key;

import javax.crypto.Cipher;

import com.start.framework.config.AppConstant;

public class DES {
	
	public static final String ALGORITHM="DES";
	/**
	 * 获取文件密钥
	 */
	public static Key getKey(String keyFileName) throws Exception {
		InputStream is = null;
		ObjectInputStream ois = null;
		try {
			is = new FileInputStream(keyFileName);
			ois = new ObjectInputStream(is);
			return (Key) ois.readObject();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				}finally{
					ois = null;
				}
			}
			if(is != null) {
				try {
					is.close();
				}finally{
					is = null;
				}
			}
		}
	}
	/**
	 * DES加密
	 */
	public static String encrypt(String str, String keyFileName) throws Exception{
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, getKey(keyFileName));
		byte[] buffer = cipher.doFinal(str.getBytes(AppConstant.ENCODING));
		return Base64.encode(buffer);
	}
	/**
	 * DES解密
	 */
	public static String decrypt(String str, String keyFileName) throws Exception{
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, getKey(keyFileName));
		byte[] buffer = cipher.doFinal(Base64.decode(str));
		return new String(buffer,AppConstant.ENCODING);
	}

}