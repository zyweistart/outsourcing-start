package com.start.application.common.service.impl;

import com.start.application.common.dao.LoginLogDao;
import com.start.application.common.entity.LoginLog;
import com.start.application.common.service.LoginLogService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;

@Service("loginLogService")
public final class LoginLogServiceImpl extends RootServiceImpl<LoginLog,Long> 
implements LoginLogService {

	public LoginLogServiceImpl(@Qualifier("loginLogDao")LoginLogDao loginLogDao) {
		super(loginLogDao);
	}

}
