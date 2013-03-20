package com.start.framework.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.start.framework.config.AppConfig;
import com.start.framework.exception.DataSourceError;
import com.start.framework.utils.StackTraceInfo;

public final class DBCPDataSourceManager {
	
	private static final Log log=LogFactory.getLog(DBCPDataSourceManager.class);
	
	private static final Map<String,BasicDataSource> dataSources=new HashMap<String,BasicDataSource>();
	/**
	 * 数据库方向
	 */
	private static final String DIALECT=AppConfig.Persistents.get("DIALECT");
	
	static{
		try {
			//初始化连接信息
			for(String ds:AppConfig.ConnectionConfigs.keySet()){
				dataSources.put(ds,  (BasicDataSource) BasicDataSourceFactory.createDataSource(AppConfig.ConnectionConfigs.get(ds)));
			}
		} catch (Exception e) {
			log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
			throw new DataSourceError(e);
		}
	}
	/**
	 * 获取默认的数据源对象
	 */
	public static BasicDataSource getDataSource() {
		return getDataSource(DIALECT);
	}
	/**
	 * 根据数据库方向获取数据源对象
	 */
	public static BasicDataSource getDataSource(String dialect) {
		return dataSources.get(dialect);
	}
	/**
	 * 获取默认的连接对象
	 */
	public static Connection getConnection() throws SQLException {
		return getConnection(DIALECT);
	}
	/**
	 * 获取连接对象
	 */
	public static Connection getConnection(String dialect) throws SQLException {
		return getDataSource(dialect).getConnection();
	}
	/**
	 * 关闭数据源对象
	 */
	public static void closeDataSource() throws SQLException {
		for(BasicDataSource bds:dataSources.values()){
			bds.close();
		}
	}
}
