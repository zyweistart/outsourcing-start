package com.start.framework.mybatis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.start.framework.exception.MybatisError;
import com.start.framework.utils.StackTraceInfo;

public final class MybatisSessionFactory {

	private final static Log log=LogFactory.getLog(MybatisSessionFactory.class);
	
	private static SqlSessionFactory factory;
	
	public static SqlSessionFactory getInstance(){
		if(factory==null){
			try {
				factory = new SqlSessionFactoryBuilder().build(
						Resources.getResourceAsStream("mybatis-config.xml"));
				factorys.put(factory.getConfiguration().getEnvironment().getId(), factory);
			} catch (Exception e) {
				log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
				throw new MybatisError(e);
			}
		}
		return factory;
	}
	/**
	 * SessionFactory数据库连接集合
	 */
	private static final Map<String,SqlSessionFactory> factorys=new ConcurrentHashMap<String,SqlSessionFactory>();
	
	public static SqlSessionFactory getInstance(String environment){
		SqlSessionFactory factory=factorys.get(environment);
		if(factory==null){
			synchronized (MybatisSessionFactory.class) {
				if(factory==null){
					try {
						factory = new SqlSessionFactoryBuilder().build(
								Resources.getResourceAsStream("mybatis-config.xml"),environment);
						factorys.put(environment, factory);
					} catch (Exception e) {
						log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
						throw new MybatisError(e);
					}
				}
			}
		}
		return factory;
	}
	
}
