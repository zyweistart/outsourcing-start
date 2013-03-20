package com.start.application.discover.service;

import java.util.List;
import java.util.Set;

import com.start.application.discover.entity.Relation;
import com.start.kernel.service.RootService;

public interface RelationService extends RootService<Relation,Long> {
	/**
	 * 关注对应用户编号
	 */
	void follow(String userCode,String friendCode);
	/**
	 * 获取当前用户所有关注的用户编号集合
	 * 分页获取
	 */
	List<String> following(String userCode,int start,int count);
	/**
	 * 获取当前用户被哪些人关注的用户编号集合
	 * 分页获取
	 */
	List<String> followedBy(String userCode,int start,int count);
	/**
	 * 取消关注某人(传入的用户编号标识操作)
	 */
	void unfollow(String userCode,String friendCode);
	/**
	 * 判断当前用户是否关注了对应用户编号
	 */
	boolean isfollowing(String userCode,String friendCode);
	/**
	 * 判断是否存在对应用户编号的粉丝
	 */
	boolean isfollowedBy(String userCode,String friendCode);
	/**
	 * 统计当前用户关注的人数总和
	 */
	Long followingCount(String userCode);
	/**
	 * 统计当前用户有多少粉丝数目
	 */
	Long followerCount(String userCode);
	/**
	 * 获取当前用户和传入用户编号对应的用户共同关注的用户编号集合
	 */
	Set<String> commonfollowing(String userCode,String friendCode);
	/**
	 * 获取当前用户和传入用户编号对应的用户共同粉丝的用户编号集合
	 */
	Set<String> commonfolloweBy(String userCode,String friendCode);
}
