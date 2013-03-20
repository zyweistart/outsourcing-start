package com.start.kernel.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.transaction.Transaction;

import com.start.kernel.dao.RootDao;
import com.start.kernel.entity.Root;
import com.start.kernel.service.RootService;
import com.start.kernel.utils.StringUtils;

public abstract class RootServiceImpl<T extends Root, PK extends Serializable>
		implements RootService<T, PK> {

	private final RootDao<T, PK> rootDao;

	public RootServiceImpl(RootDao<T, PK> rootDao) {
		this.rootDao = rootDao;
	}

	@Override
	public Transaction getTransaction() throws SQLException {
		return this.rootDao.getTransaction();
	}

	@Override
	public int save(T entity) {
		Boolean flag=entity.getId()==null;
		if(flag){
			// 插入操作时如果为空时自动生成唯一编号
			if (entity.getCode() == null) {
				entity.setCode(StringUtils.random());
			}
		}
		return rootDao.save(entity,flag);
	}

	@Override
	public int delete(T entity) {
		return rootDao.delete(entity);
	}

	@Override
	public T load(T entity) {
		return rootDao.load(entity);
	}

	@Override
	public List<T> getResultList(T entity) {
		return rootDao.getResultList(entity);
	}

	@Override
	public Map<String,Map<String,String>> getResultMap(T entity,String mapKey) {
		return rootDao.getResultMap(entity,mapKey);
	}
	
	@Override
	public int getSingleResult(T entity) {
		return rootDao.getSingleResult(entity);
	}

	@Override
	public void clearSessionCache(){
		rootDao.clearSessionCache();
	}
	
	@Override
	public void closeSession() {
		rootDao.closeSession();
	}

}