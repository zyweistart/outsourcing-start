package com.start.framework.jta;

import javax.transaction.xa.Xid;

public class MyXid implements Xid {

	private int formatId;
	private byte[] branchQualifier;
	private byte[] globalTransactionId;
	
	public MyXid(int formatId,byte[] globalTransactionId,byte[] branchQualifier) {
		super();
		this.branchQualifier = branchQualifier;
		this.formatId = formatId;
		this.globalTransactionId = globalTransactionId;
	}

	@Override
	public int getFormatId() {
		return formatId;
	}
	/**
	 * 全局事务标识符
	 */
	@Override
	public byte[] getGlobalTransactionId() {
		return globalTransactionId;
	}
	/**
	 * 分支修饰词标识符
	 */
	@Override
	public byte[] getBranchQualifier() {
		return branchQualifier;
	}
}
