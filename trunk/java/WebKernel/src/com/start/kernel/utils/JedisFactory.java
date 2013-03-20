package com.start.kernel.utils;

import redis.clients.jedis.Jedis;
/**
 * Redis工厂类
 * @author Start
 */
public class JedisFactory {

	private static Jedis jedis;
	
	private JedisFactory(){}
	
	public static Jedis getInstance(){
		if(jedis==null){
			jedis=new Jedis("localhost");
		}
		return jedis;
	}

}