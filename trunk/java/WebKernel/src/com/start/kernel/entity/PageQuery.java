package com.start.kernel.entity;

import java.util.List;
import java.util.Map;

public class PageQuery<T extends Root> {
	
	private List<T> entityLists;
	
	private Map<String,Map<String,String>> entityMaps;
	/**
	 * 总记录数
	 */
	private Integer totalCount;
	/**
	 * 当前页
	 */
	private Integer currentPage;
	/**
	 * 每页大小
	 */
	private Integer pageSize;
	/**
	 * @param entityLists     List集合
	 * @param totalCount 	  总记录数
	 * @param currentPage 当前页码
	 * @param pageSize		  每次大小
	 */
	public PageQuery(List<T> entityLists,Integer totalCount,Integer currentPage,Integer pageSize) {
		this.entityLists=entityLists;
		this.pageSize=pageSize;
		this.totalCount=totalCount;
		this.currentPage=currentPage;
	}
	
	/**
	 * @param entityMaps   Map集合
	 * @param totalCount 	  总记录数
	 * @param currentPage 当前页码
	 * @param pageSize		  每次大小
	 */
	public PageQuery(Map<String,Map<String,String>> entityMaps,Integer totalCount,Integer currentPage,Integer pageSize) {
		this.entityMaps=entityMaps;
		this.pageSize=pageSize;
		this.totalCount=totalCount;
		this.currentPage=currentPage;
	}
	
	public List<T> getEntityLists() {
		return entityLists;
	}
	
	public Map<String, Map<String, String>> getEntityMaps() {
		return entityMaps;
	}

	/**
	 * 总页数
	 */
	public Integer getTotalPage() {
		int totalPage=getTotalCount()/getPageSize();
		int num=getTotalCount()%getPageSize();
		if(num>0){
			totalPage++;
		}
		return totalPage;
	}
	/**
	 * 获取总记录数
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	/**
	 * 获取当前页
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}
	/**
	 * 获取每页大小
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	
}