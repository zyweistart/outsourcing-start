package com.start.model.medmap;

import java.io.Serializable;

public class Vertex implements Serializable {
	private static final long serialVersionUID = -7059095489884768374L;
	/**
	 * 所属地图编号
	 */
	private int mapIndex;
	/**
	 * Vertex编号
	 */
	private int vertexNumber;
	/**
	 * 纬度
	 */
	private double latitude;
	/**
	 * 经度
	 */
	private double longitude;

	public int getMapIndex() {
		return mapIndex;
	}

	public void setMapIndex(int mapIndex) {
		this.mapIndex = mapIndex;
	}

	public int getVertexNumber() {
		return vertexNumber;
	}

	public void setVertexNumber(int vertexNumber) {
		this.vertexNumber = vertexNumber;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
