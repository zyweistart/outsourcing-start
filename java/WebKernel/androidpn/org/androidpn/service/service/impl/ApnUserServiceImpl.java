package org.androidpn.service.service.impl;

import org.androidpn.service.UserExistsException;
import org.androidpn.service.dao.ApnUserDao;
import org.androidpn.service.entity.ApnUser;
import org.androidpn.service.service.ApnUserService;

import com.start.framework.context.ContextInjection;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;
import com.start.kernel.utils.LogUtils;

@Service("apnUserService")
public final class ApnUserServiceImpl extends RootServiceImpl<ApnUser,Long> 
implements ApnUserService {

	public ApnUserServiceImpl(@Qualifier("apnUserDao")ApnUserDao apnUserDao) {
		super(apnUserDao);
	}
	
	private static ApnUserService instance;
	
	public static ApnUserService getInstance(){
		if(instance==null){
			instance=(ApnUserService)new ContextInjection().getResourceInjectionValue("apnUserService");
		}
		return instance;
	}
	
	@Override
	public int add(ApnUser user) throws UserExistsException {
		try{
			return super.save(user);
		}catch(Exception e){
			LogUtils.printError("User '" + user.getUsername() + "' already exists!");
			throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
		}
	}

	@Override
	public ApnUser getUserById(String id) {
		ApnUser user=new ApnUser();
		user.setId(Long.parseLong(id));
		user.setMybatisMapperId("getUserById");
		return load(user);
	}

	@Override
	public ApnUser getUserByUserName(String name) {
		ApnUser user=new ApnUser();
		user.setUsername(name);
		user.setMybatisMapperId("getUserByUserName");
		return load(user);
	}

}
