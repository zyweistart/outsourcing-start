package com.start.framework.mybatis;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.datasource.DataSourceFactory;

import com.start.framework.exception.DataSourceError;
import com.start.framework.utils.StackTraceInfo;
/**
 * DBCP连接池数据源,使用Tomcat推荐使用该数据源
 * @author Start
 */
public class MybatisDBCPDataSource implements DataSourceFactory {

	private final static Log log=LogFactory.getLog(MybatisDBCPDataSource.class);
	
	private BasicDataSource datasource;
	
	@Override
	public void setProperties(Properties props) {
		try {
			this.datasource =(BasicDataSource) BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
			throw new DataSourceError(e);
		}
	}

	@Override
	public DataSource getDataSource() {
		return datasource;
	}

}
