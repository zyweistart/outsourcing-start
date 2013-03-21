package com.start.test;

import org.androidpn.service.entity.ApnUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.start.kernel.utils.StringUtils;

public class MyBatisTest {

	public static void main(String[] args) throws Exception {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(
				Resources.getResourceAsStream("mybatis-config.xml"));
		SqlSession sqlSession=factory.openSession(true);
		try{
			ApnUser apnUser=new ApnUser();
			apnUser.setCode(StringUtils.random());
			apnUser.setUsername("11111");
			apnUser.setPassword(":123456");
			sqlSession.insert("ApnUser.insert",apnUser);
			System.out.println("success");
		}finally{
			sqlSession.close();
		}
	}

}
