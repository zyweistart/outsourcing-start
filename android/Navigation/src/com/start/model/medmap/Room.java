package com.start.model.medmap;

import java.io.Serializable;

import org.mapsforge.core.model.GeoPoint;

public class Room implements Serializable {
	private static final long serialVersionUID = 4869377468535600476L;
	/**
	 * 房间名字
	 */
	private String name;
	/**
	 * 所属地图编号
	 */
	private int mapIndex;
	/**
	 * 房间编号
	 */
	private int roomNumber;
	/**
	 * 所属科室编号（可选）
	 */
	private int departmentIndex;
	/**
	 * 导航中心点（Vertex对象）
	 */
	private Vertex center;
	/**
	 * 多边形bounding box的左限
	 */
	private double left;
	/**
	 * 多边形bounding box的上限
	 */
	private double top;
	/**
	 * 多边形bounding box的右限
	 */
	private double right;
	/**
	 * 多边形bounding box的下限
	 */
	private double bottom;
	private double centerX;
	private double centerY;
	/**
	 * 构成房间的多边形的顶点的坐标（float数组）
	 */
	private double[] vertexX;
	private double[] vertexY;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public int getDepartmentIndex() {
		return departmentIndex;
	}

	public void setDepartmentIndex(int departmentIndex) {
		this.departmentIndex = departmentIndex;
	}

	public Vertex getCenter() {
		return center;
	}

	public void setCenter(Vertex center) {
		this.center = center;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public double getBottom() {
		return bottom;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public double[] getVertexX() {
		return vertexX;
	}

	public void setVertexX(double[] vertexX) {
		this.vertexX = vertexX;
	}

	public double[] getVertexY() {
		return vertexY;
	}

	public void setVertexY(double[] vertexY) {
		this.vertexY = vertexY;
	}

	public boolean contains(GeoPoint geoPoint) {
		double x = geoPoint.longitude;
		double y = geoPoint.latitude;
		if (x > right || x < left || y > top || y < bottom) {
			return false;
		} else {
			return pnpoly(vertexX.length, vertexX, vertexY,x, y);
		}
	}
	
	public boolean pnpoly(int nvert, double[] vertx, double[] verty,
			double testx, double testy) {
		int i, j, c = 0;
		for (i = 0, j = nvert - 1; i < nvert; j = i++) {
			if (((verty[i] > testy) != (verty[j] > testy))
					&& (testx < (vertx[j] - vertx[i]) * (testy - verty[i])
							/ (verty[j] - verty[i]) + vertx[i])) {
				if (c == 0) {
					c = 1;
				} else {
					c = 0;
				}
			}
		}
		return c != 0;
	}
}
