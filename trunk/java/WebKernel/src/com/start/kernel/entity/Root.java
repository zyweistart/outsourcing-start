package com.start.kernel.entity;

import java.io.Serializable;

import com.start.kernel.config.Business;

public abstract class Root implements Serializable, Cloneable {

	private static final long serialVersionUID = 43948582629964283L;
	/**
	 * PK主键
	 */
	private Long id;
	/**
	 * 最长32位随机码，需要建立唯一约束
	 */
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Root other = (Root) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	// //////////////////////分页查询//////////////////////////////////

	private Integer currentPage;
	
	private Integer pageSize;

	/**
	 * 开始获取的记录行数
	 */
	public Integer getStartRow() {
		return (getCurrentPage() - 1) * getPageSize();
	}

	public Integer getCurrentPage() {
		if (currentPage == null) {
			// 默认为起码页第一页
			currentPage = 1;
		}
		return currentPage;
	}

	/**
	 * 设置当前的页码
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		if (this.pageSize == null) {
			// 设置默认页的大小
			this.pageSize = Business.DEFAULT_PAGESIZE;
		}
		return pageSize;
	}

	/**
	 * 设置每页的大小
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	// /////////////////////////////mybatis//////////////////////////////////////
	private String mybatisNameAlias;

	/**
	 * 设置当前实体的命名空间
	 */
	public void setMybatisNameAlias(String mybatisNameAlias) {
		this.mybatisNameAlias = mybatisNameAlias;
	}

	private String mybatisMapperId;

	/**
	 * 设置当前要执行的SQLmapper中的ID
	 */
	public void setMybatisMapperId(String mybatisMapperId) {
		this.mybatisMapperId = mybatisMapperId;
	}

	/**
	 * 设置当前实体要执行mapper的完整名称(命名空间.mapperID)
	 */
	public String mybatisFullNameStatement() {
		if (mybatisNameAlias == null) {
			mybatisNameAlias = this.getClass().getSimpleName();
		}
		if (mybatisMapperId == null) {
			throw new NullPointerException("mybatisMapperId is Null");
		}
		return mybatisNameAlias + "." + mybatisMapperId;
	}

}