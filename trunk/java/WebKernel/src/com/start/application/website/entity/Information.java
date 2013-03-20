package com.start.application.website.entity;

import java.util.Date;

import com.start.kernel.entity.Root;

public class Information extends Root {

	private static final long serialVersionUID = 1L;
	
	public Information(){}
	//标题
	private String title;
	//作者
	private String author;
	//主体内容
	private String content;
	//所属类别(注：类别分类不能为<0：顶级>)
	private String category;
	//来源时间
	private Date sourceDate;
	//主页显示(true:是false:否)
	private Boolean home=true;
	//是否置顶(true:是false:否)
	private Boolean top=false;
	//热点标记(true:是false:否)
	private Boolean hot=false;
	//发布状态(true:是false:否)
	private Boolean status=false;
	//发布时间
	private Date releaseDate;
	//结束发布时间
	private Date endReleaseDate;
	//点击数
	private Integer hits;
	//关键字
	private String keywords;
	//描述信息
	private String description;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getSourceDate() {
		return sourceDate;
	}
	
	public void setSourceDate(Date sourceDate) {
		this.sourceDate = sourceDate;
	}
	
	public Boolean getHome() {
		return home;
	}
	
	public void setHome(Boolean home) {
		this.home = home;
	}
	
	public Boolean getTop() {
		return top;
	}
	
	public void setTop(Boolean top) {
		this.top = top;
	}
	
	public Boolean getHot() {
		return hot;
	}
	
	public void setHot(Boolean hot) {
		this.hot = hot;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public Date getEndReleaseDate() {
		return endReleaseDate;
	}
	
	public void setEndReleaseDate(Date endReleaseDate) {
		this.endReleaseDate = endReleaseDate;
	}
	
	public Integer getHits() {
		return hits;
	}
	
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}