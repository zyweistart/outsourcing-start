package com.start.kernel.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.start.framework.context.annnotation.PersistenceContext;
import com.start.kernel.dao.RootDao;
import com.start.kernel.entity.Root;

public abstract class RootDaoImpl<T extends Root, PK extends Serializable>
		implements RootDao<T, PK> {

	@PersistenceContext
	private SqlSession session;

	@Override
	public SqlSession getSession() {
		return session;
	}

	@Override
	public Transaction getTransaction() throws SQLException {
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Transaction transaction = transactionFactory.newTransaction(getSession().getConnection());
		transaction.getConnection().setAutoCommit(false);
		return transaction;
	}

	@Override
	public int save(T entity, Boolean flag) {
		if (flag) {
			return getSession().insert(entity.mybatisFullNameStatement(),entity);
		} else {
			return getSession().update(entity.mybatisFullNameStatement(),entity);
		}
	}

	@Override
	public int delete(T entity) {
		return getSession().delete(entity.mybatisFullNameStatement(), entity);
	}

	@Override
	public T load(T entity) {
		return getSession().selectOne(entity.mybatisFullNameStatement(), entity);
	}

	@Override
	public List<T> getResultList(T entity) {
		return getSession().selectList(entity.mybatisFullNameStatement(),entity);
	}

	@Override
	public Map<String,Map<String,String>> getResultMap(T entity,String mapKey) {
		return getSession().selectMap(entity.mybatisFullNameStatement(), entity,mapKey);
	}
	
	@Override
	public int getSingleResult(T entity) {
		return getSession().selectOne(entity.mybatisFullNameStatement(), entity);
	}
	
	@Override
	public void clearSessionCache(){
		if(getSession()!=null){
			getSession().clearCache();
		}
	}

	@Override
	public void closeSession() {
		if (getSession() != null) {
			getSession().close();
		}
	}

}