package com.start.bean;

import java.io.Serializable;
/**
 * 实体基类：实现序列化
 * @author start
 *
 */
public abstract class Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private Result result;
	
	private Notice notice;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	
}
