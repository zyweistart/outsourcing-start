package com.start.application.system.dao.impl;

import org.apache.ibatis.session.SqlSession;

import com.start.application.system.dao.OperatorLogDao;
import com.start.application.system.entity.OperatorLog;
import com.start.framework.context.annnotation.Repository;
import com.start.framework.mybatis.MybatisSessionFactory;
import com.start.kernel.config.Business;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("operatorLogDao")
public final class OperatorLogDaoImpl extends RootDaoImpl<OperatorLog,Long>implements OperatorLogDao {

	private SqlSession sqlSession;
	
	@Override
	public SqlSession getSession() {
		if(sqlSession==null){
			sqlSession=MybatisSessionFactory.getInstance(Business.BACKUPDB).openSession(true);
		}
		return sqlSession;
	}

}