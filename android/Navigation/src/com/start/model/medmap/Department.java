package com.start.model.medmap;

import java.io.Serializable;

public class Department implements Serializable {

	private static final long serialVersionUID = -1483645085548614670L;
	/**
	 * 科室所在地图编号
	 */
	private int mapIndex;
	/**
	 * 科室所在房间编号
	 */
	private int roomNumber;
	/**
	 * 科室名字
	 */
	private String name;
	/**
	 * 科室简介
	 */
	private String introduction;
	/**
	 * 科室所有医生（Doctor数组）
	 */
	private Doctor[] doctors;

	public int getMapIndex() {
		return mapIndex;
	}

	public void setMapIndex(int mapIndex) {
		this.mapIndex = mapIndex;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Doctor[] getDoctors() {
		return doctors;
	}

	public void setDoctors(Doctor[] doctors) {
		this.doctors = doctors;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
