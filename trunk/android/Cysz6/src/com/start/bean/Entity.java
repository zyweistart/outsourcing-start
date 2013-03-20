package com.start.bean;
/**
 * 实体类
 * @author start
 *
 */
public abstract class Entity extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String cacheKey;

	public int getId() {
		return id;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	
}