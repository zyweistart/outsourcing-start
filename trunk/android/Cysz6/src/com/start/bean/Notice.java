package com.start.bean;

import java.io.Serializable;
/**
 * 通知信息实体类
 * @author start
 *
 */
public class Notice implements Serializable{

	private static final long serialVersionUID = 1L;

	private int msgCount;

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}
	
}