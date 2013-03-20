package com.start.kernel.utils;

import java.io.InputStream;
import java.sql.Clob;
import java.sql.SQLException;

import com.start.framework.utils.StackTraceInfo;


public class RowSetUtils {

	public static boolean isEmpty(RowSet rs) {
		return rs == null || getRowCount(rs) == 0;
	}

	public static boolean isNotEmpty(RowSet rs) {
		return rs != null && getRowCount(rs) > 0;
	}

	public static int getRowCount(RowSet rs) {
		int count = 0;
		try {
			count = rs.getRowCount();
		} catch (SQLException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		}
		return count;
	}

	public static boolean next(RowSet rs) {

		boolean flag = false;

		try {
			flag = rs.next();
		} catch (SQLException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		}

		return flag;
	}

	public static boolean previous(RowSet rs) {

		boolean flag = false;

		try {
			flag = rs.previous();
		} catch (SQLException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		}

		return flag;
	}

	public static boolean beforeFirst(RowSet rs) {

		boolean flag = false;

		try {
			rs.beforeFirst();
			flag = true;
		} catch (SQLException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		}

		return flag;
	}

	public static boolean last(RowSet rs) {

		boolean flag = false;

		try {
			flag = rs.last();
		} catch (SQLException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		}

		return flag;
	}

	public static String getString(RowSet rs, String column) {

		String value = "";

		if (rs != null && StringUtils.isNotEmpty(column)) {
			try {
				value = rs.getString(column);
				value = StringUtils.nullToStrTrim(value);
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static String getString(RowSet rs, int index) {

		String value = "";

		if (rs != null) {
			try {
				value = rs.getString(index);
				value = StringUtils.nullToStrTrim(value);
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static int getInt(RowSet rs, String column) {

		int value = 0;

		if (rs != null && StringUtils.isNotEmpty(column)) {
			try {
				value = StringUtils.nullToIntZero(rs.getString(column));
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static int getInt(RowSet rs, int index) {

		int value = 0;

		if (rs != null) {
			try {
				value = StringUtils.nullToIntZero(rs.getString(index));
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static double getDouble(RowSet rs, String column) {

		double value = 0.0D;

		if (rs != null && StringUtils.isNotEmpty(column)) {
			try {
				value = StringUtils.nullToDoubleZero(rs.getString(column));
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static double getDouble(RowSet rs, int index) {

		double value = 0.0D;

		if (rs != null) {
			try {
				value = StringUtils.nullToDoubleZero(rs.getString(index));
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static long getLong(RowSet rs, String column) {

		long value = 0L;

		if (rs != null && StringUtils.isNotEmpty(column)) {
			try {
				value = StringUtils.nullToLongZero(rs.getString(column));
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static long getLong(RowSet rs, int index) {

		long value = 0L;

		if (rs != null) {
			try {
				value = StringUtils.nullToLongZero(rs.getString(index));
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static byte[] getBytes(RowSet rs, String column) {

		byte[] value = null;

		if (rs != null && StringUtils.isNotEmpty(column)) {
			try {
				value = rs.getBytes(column);
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static byte[] getBytes(RowSet rs, int index) {

		byte[] value = null;

		if (rs != null) {
			try {
				value = rs.getBytes(index);
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static String getClob(RowSet rs, String column) {

		String value = "";

		if (rs != null && StringUtils.isNotEmpty(column)) {
			try {
				Clob clob = rs.getClob(column);
				if (clob != null && clob.length() > 0) {
					value = StringUtils.nullToStrTrim(clob.getSubString((long) 0, (int) clob.length()));
				}
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static String getClob(RowSet rs, int index) {

		String value = "";

		if (rs != null) {
			try {
				Clob clob = rs.getClob(index);
				if (clob != null && clob.length() > 0) {
					value = StringUtils.nullToStrTrim(clob.getSubString((long) 0, (int) clob.length()));
				}
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return value;
	}

	public static InputStream getBinaryStream(RowSet rs, String column) {

		if (rs != null && StringUtils.isNotEmpty(column)) {
			try {
				return rs.getBinaryStream(column);
			} catch (SQLException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}

		return null;
	}

	public static InputStream getBinaryStream(RowSet rs, int index) {

		try {
			return rs.getBinaryStream(index);
		} catch (SQLException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		}

		return null;
	}

	public static String[] getResultArray(RowSet rs, String[] columns) {

		int length = columns.length;
		String[] resultArray = new String[length];

		if (RowSetUtils.next(rs)) {
			for (int i = 0; i < length; i++) {
				resultArray[i] = getString(rs, columns[i]);
			}
		}

		return resultArray;
	}

	public static String[] getResultArray(String[] values, RowSet rs, String[] columns) {

		int vlength = values.length;
		int clength = columns.length;
		int length = vlength + clength;

		String[] resultArray = new String[length];

		for (int i = 0; i < vlength; i++) {
			resultArray[i] = values[i];
		}

		if (RowSetUtils.next(rs)) {
			for (int i = 0; i < clength; i++) {
				resultArray[vlength + i] = getString(rs, columns[i]);
			}
		}

		return resultArray;
	}

	public static String[] getResultArray(String[] valuesstart, RowSet rs, String[] columns, String[] valuesend) {

		int vslength = valuesstart.length;
		int clength = columns.length;
		int velength = valuesend.length;
		int length = vslength + clength + velength;

		String[] resultArray = new String[length];

		for (int i = 0; i < vslength; i++) {
			resultArray[i] = valuesstart[i];
		}

		if (RowSetUtils.next(rs)) {
			for (int i = 0; i < clength; i++) {
				resultArray[vslength + i] = getString(rs, columns[i]);
			}
		}

		for (int i = 0; i < velength; i++) {
			resultArray[vslength + clength + i] = valuesend[i];
		}

		return resultArray;
	}

	public static String[][] getResultArrays(RowSet rs, String[] columns) {
		int lengths = RowSetUtils.getRowCount(rs);
		int length = columns.length;
		String[][] resultArrays = new String[lengths][length];
		String[] resultArray = null;
		int i = 0;
		while (RowSetUtils.next(rs)) {
			resultArray = new String[length];
			for (int j = 0; j < length; j++) {
				resultArray[j] = getString(rs, columns[j]);
			}
			resultArrays[i] = resultArray;
			i++;
		}
		return resultArrays;
	}

}