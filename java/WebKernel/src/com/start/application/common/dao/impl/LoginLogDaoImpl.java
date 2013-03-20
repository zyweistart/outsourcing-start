package com.start.application.common.dao.impl;

import com.start.application.common.dao.LoginLogDao;
import com.start.application.common.entity.LoginLog;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("loginLogDao")
public final class LoginLogDaoImpl extends RootDaoImpl<LoginLog,Long>implements LoginLogDao {

}
