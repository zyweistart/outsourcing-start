package com.start.model.medmap;

import java.io.Serializable;

public class Map implements Serializable {
	private static final long serialVersionUID = -4675524185551219395L;
	/**
	 * 地图编号
	 */
	private int mapIndex;
	/**
	 * 地图名字
	 */
	private String name;
	/**
	 * 描述某个地图上所有可点击的房间（Room对象数组）
	 */
	private Room[] rooms;

	public int getMapIndex() {
		return mapIndex;
	}

	public void setMapIndex(int mapIndex) {
		this.mapIndex = mapIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Room[] getRooms() {
		return rooms;
	}

	public void setRooms(Room[] rooms) {
		this.rooms = rooms;
	}

}
