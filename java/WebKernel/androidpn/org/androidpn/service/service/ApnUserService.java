package org.androidpn.service.service;

import org.androidpn.service.UserExistsException;
import org.androidpn.service.entity.ApnUser;

import com.start.kernel.service.RootService;

public interface ApnUserService extends RootService<ApnUser,Long> {
	
	int add(ApnUser user) throws UserExistsException;
	
	ApnUser getUserById(String id);
	
	ApnUser getUserByUserName(String name);
	
}
