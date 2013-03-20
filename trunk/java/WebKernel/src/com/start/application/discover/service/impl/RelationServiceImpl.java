package com.start.application.discover.service.impl;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import com.start.application.discover.dao.RelationDao;
import com.start.application.discover.entity.Relation;
import com.start.application.discover.service.RelationService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;
import com.start.kernel.utils.JedisFactory;


@Service("relationService")
public final class RelationServiceImpl extends RootServiceImpl<Relation,Long> 
implements RelationService {
	
	private static final String FOLLOWING="relation_following:";
	private static final String FOLLOWBY="relation_follow_by:";
	
	private Jedis jedis;

	public RelationServiceImpl(@Qualifier("relationDao")RelationDao relationDao) {
		super(relationDao);
		jedis=JedisFactory.getInstance();
	}

	@Override
	public void follow(String userCode, String friendCode) {
		jedis.sadd(FOLLOWING+userCode, friendCode);
		jedis.sadd(FOLLOWBY+friendCode, userCode);
	}

	@Override
	public List<String> following(String userCode,int start,int count) {
		SortingParams sp=new SortingParams();
	    sp.limit(start,count);
	    return jedis.sort(FOLLOWING+userCode, sp);
	}

	@Override
	public List<String> followedBy(String userCode,int start,int count) {
		SortingParams sp=new SortingParams();
	    sp.limit(start,count);
	    return jedis.sort(FOLLOWBY+userCode);
	}

	@Override
	public void unfollow(String userCode, String friendCode) {
		jedis.srem(FOLLOWING+userCode,friendCode);
		jedis.srem(FOLLOWBY+friendCode,userCode);
	}

	@Override
	public boolean isfollowing(String userCode, String friendCode) {
		return jedis.sismember(FOLLOWING+userCode, friendCode);
	}

	@Override
	public boolean isfollowedBy(String userCode, String friendCode) {
		return jedis.sismember(FOLLOWBY+userCode, friendCode);
	}

	@Override
	public Long followingCount(String userCode) {
		return jedis.scard(FOLLOWING+userCode);
	}

	@Override
	public Long followerCount(String userCode) {
		return jedis.scard(FOLLOWBY+userCode);
	}

	@Override
	public Set<String> commonfollowing(String userCode, String friendCode) {
		return jedis.sinter(FOLLOWING+userCode,FOLLOWING+friendCode);
	}

	@Override
	public Set<String> commonfolloweBy(String userCode, String friendCode) {
		return jedis.sinter(FOLLOWBY+userCode,FOLLOWBY+friendCode);
	}

}