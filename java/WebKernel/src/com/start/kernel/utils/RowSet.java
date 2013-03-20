package com.start.kernel.utils;

import java.sql.SQLException;

import oracle.jdbc.rowset.OracleCachedRowSet;

/**
 * 缓存OracleRowSet
 * @author Start
 */
public class RowSet extends OracleCachedRowSet {

	private static final long serialVersionUID = 1L;

	public RowSet() throws SQLException {

		super();
	}

	public int getRowCount() throws SQLException {
		int count = 0;
		beforeFirst();
		if (next()) {
			last();
			count = getRow();
		}
		beforeFirst();
		return count;
	}

}
