package com.start.test;

import org.androidpn.service.entity.ApnUser;
import org.androidpn.service.service.ApnUserService;
import org.androidpn.service.service.impl.ApnUserServiceImpl;

import com.start.framework.context.Init;
import com.start.kernel.config.Variable;

public class TestMain {

	public static void main(String[] args) {
		
		Init.loadConfigFile();
		Init.loadContextClass();
//		ContextInjection in=new ContextInjection();
//		ApnUserService anservice=(ApnUserService)in.getResourceInjectionValue("apnUserService");
		ApnUserService anservice=ApnUserServiceImpl.getInstance();
		
//		System.out.println(anservice.getUserById("308"));
		ApnUser user=new ApnUser();
		user.setCode("12s");
		user.setUsername("12s");
		user.setPassword("12s");
		user.setMybatisMapperId(Variable.INSERT);
		try{
			anservice.save(user);
		}catch(Exception e){
			System.out.println("报错了");
			e.printStackTrace();
		}
//		File file=new File("d:/test.png");
//		System.out.println(file.getAbsolutePath());
	}

}
