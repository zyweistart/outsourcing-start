package com.start.bean;

import java.io.IOException;
import java.io.InputStream;

import com.start.common.AppException;

/**
 * 数据操作结果实体类
 * @author start
 *
 */
public class Result extends Base {

	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	
	private String errorMessage;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean OK() {
		return errorCode == 1;
	}

	public static Result parse(InputStream stream) throws IOException,AppException{
		return null;
	}

}
