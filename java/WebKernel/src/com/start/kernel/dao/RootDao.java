package com.start.kernel.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;

import com.start.kernel.entity.Root;

public abstract interface RootDao<T extends Root, PK extends Serializable> {

	/**
	 * 注：如在子类中重写该方法需要在使用完后手动调用close()方法保证连接正常关闭, 一般需要重写该接口的所有方法，并加入如下代码样例
	 * 
	 * <pre>
	 * 代码样例: try {
	 * 	return super.重写的方法();
	 * } finally {
	 * 	getSession().close();
	 * }
	 * </pre>
	 */
	SqlSession getSession();

	/**
	 * JDBC事务管理对象MySQL中需要把表类型设置为InnoDB,MyISAM表类型不支持事务处理
	 */
	Transaction getTransaction() throws SQLException;

	/**
	 * 插入与更新
	 * 
	 * @param flag
	 *            true:插入(insert)false:更新(update)
	 */
	int save(T entity, Boolean flag);

	/**
	 * 根据实体删除记录
	 */
	int delete(T entity);

	/**
	 * 加载单实体对象
	 */
	T load(T enttiy);

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
	 * 返回聚合函数的结果集(count,max,min,avg等) 如：select count(*) from tablename
	 */
	int getSingleResult(T entity);

	/**
	 * 清除Session中的缓存防止读取重复数据
	 */
	void clearSessionCache();

	/**
	 * 关闭Session
	 */
	void closeSession();
}