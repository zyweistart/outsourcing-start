package com.start.framework.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.exception.JdbcRuntimeException;
import com.start.framework.utils.StackTraceInfo;

public final class Session {

	private static final Log log=LogFactory.getLog(Session.class);
	
	private Connection connection = null;
	
	Session(){}
	
	public PreparedStatement executeQuery(String executeQuery, Object...params) throws SQLException{
		PreparedStatement pstmt=getConnection().prepareStatement(executeQuery);
		if(params!=null){
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
		}
		return pstmt;
	}

	public int executeUpdate(String executeUpdate,Object...params) throws SQLException{
		return executeUpdate1(executeUpdate,false,params);
	}
	/**
	 * @param GENERATED_KEYS_FLAG 是否返回生成的主键值
	 */
	public int executeUpdate1(String executeUpdate,boolean GENERATED_KEYS_FLAG, Object...params)throws SQLException{
		PreparedStatement pstmt=null;
		try{
			int resultValue=0;
			pstmt=GENERATED_KEYS_FLAG?
					getConnection().prepareStatement(executeUpdate,Statement.RETURN_GENERATED_KEYS):
						getConnection().prepareStatement(executeUpdate);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					pstmt.setObject(i+1, params[i]);
				}
			}
			resultValue=pstmt.executeUpdate();
			if(GENERATED_KEYS_FLAG){
				ResultSet rSet=null;
				try{
					rSet=pstmt.getGeneratedKeys();
					while(rSet.next()){
						resultValue=rSet.getInt(1);
					}
				}finally{
					Session.closeResultSet(rSet);
				}
			}
			return resultValue;
		}finally{
			Session.closePreparedStatement(pstmt);
		}
	}
	/**
	 * SQL批处理
	 */
	public int[] executeBatch(String... executeBatchs) throws SQLException{
		Statement stmt=null;
		try{
			stmt=getConnection().createStatement();
			for(String batch:executeBatchs){
				stmt.addBatch(batch);
			}
			return stmt.executeBatch();
		}finally{
			Session.closeStatement(stmt);
		}
	}
	/**
	 * 获取连接对象
	 */
	public Connection getConnection() throws SQLException {
		if(connection==null||connection.isClosed()){
			connection=DBCPDataSourceManager.getConnection();
		}
		return connection;
	}
	////////////////////////////关闭对象/////////////////////
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
	     		throw new JdbcRuntimeException(e);
			}finally{
				connection = null;
			}
		}
	}
	
	public static void closeResultSet(ResultSet rset) {
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException e) {
				log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
	     		throw new JdbcRuntimeException(e);
			}finally{
				rset = null;
			}
		}
	}
	
	public static void closeStatement(Statement stmt){
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
	     		throw new JdbcRuntimeException(e);
			}finally{
				stmt = null;
			}
		}
	}
	
	public static void closePreparedStatement(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
	     		throw new JdbcRuntimeException(e);
			}finally{
				pstmt = null;
			}
		}
	}

	@Override
	public void finalize() throws Throwable {
		closeConnection();
		super.finalize();
	}
	
}