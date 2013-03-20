package com.start.application.discover.entity;

import com.start.kernel.entity.Root;
/**
 * 个人资料
 * @author Start
 */
public class PersonalData extends Root {

	private static final long serialVersionUID = 1L;
	
	public PersonalData(){}
	/**
	 * 用户编号
	 */
	private String userCode;
	/**
	 * 真实姓名
	 */
	private String name;
	/**
	 * 出生年月日
	 */
	private String birthday;
	/**
	 * 个人说明
	 */
	private String statement;
	/**
	 * 专业
	 */
	private String profession;
	/**
	 * 公司
	 */
	private String company;
	/**
	 * 学校
	 */
	private String school;
	/**
	 * 所在地
	 */
	private String location;
	/**
	 * 个人主页
	 */
	private String homepage;

	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getProfession() {
		return profession;
	}
	
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getSchool() {
		return school;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getHomepage() {
		return homepage;
	}
	
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	
}