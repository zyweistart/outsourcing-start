package com.start.application.website.entity;

import com.start.kernel.entity.Root;

public class Category extends Root {

	private static final long serialVersionUID = 1L;
	
	public Category(){}
	//名称
	private String name;
	//类型(0:顶级(注：信息分类不能为顶级)、1：单页面、2：列表、3：图文列表)
	private Integer type=0;
	//级别
	private Integer level;
	//父级
	private String parent;
	//描述
	private String description;
	//状态(1:正常，2:禁用)
	private Integer status=1;
	//排序
	private Integer sort=0;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}