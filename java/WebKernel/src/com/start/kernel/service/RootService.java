package com.start.kernel.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.transaction.Transaction;

import com.start.kernel.entity.Root;

public abstract interface RootService<T extends Root, PK extends Serializable> {
	/**
	 * 事务工厂
	 */
	Transaction getTransaction() throws SQLException;

	/**
	 * 保存或更新根据ID主键自动选择
	 */
	int save(T entity);

	/**
	 * 删除对象
	 */
	int delete(T entity);

	/**
	 * 加载单个对象,主要用于有主键或约一唯一的查询
	 */
	T load(T entity);

	/**
	 * 可分页查询返回List列表
	 */
	List<T> getResultList(T entity);

	/**
	 * 可分页查询返回Map集合
	 * 
	 * @param entity
	 *            查询的实体对象
	 * @param mapKey
	 *            Map对象的主键列
	 */
	Map<String, Map<String, String>> getResultMap(T entity, String mapKey);

	/**
	 * 返回聚合函数值(count,max,min,avg等)
	 */
	int getSingleResult(T entity);

	/**
	 * 清除Session缓存数据
	 */
	void clearSessionCache();

	/**
	 * 关闭Session
	 */
	void closeSession();
}
