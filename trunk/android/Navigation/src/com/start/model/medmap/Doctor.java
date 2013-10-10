package com.start.model.medmap;

import java.io.Serializable;

public class Doctor implements Serializable {
	private static final long serialVersionUID = -2119334898141735970L;
	/**
	 * 医生名字
	 */
	private String name;
	private String sex;
	private String title;
	private String specialty;
	private String workday;
	/**
	 * 医生简介
	 */
	private String introduction;
	/**
	 * 医生所属科室
	 */
	private Department department;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getWorkday() {
		return workday;
	}

	public void setWorkday(String workday) {
		this.workday = workday;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
