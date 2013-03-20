package org.androidpn.service.dao.impl;

import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;
import org.androidpn.service.dao.ApnUserDao;
import org.androidpn.service.entity.ApnUser;

@Repository("apnUserDao")
public final class ApnUserDaoImpl extends RootDaoImpl<ApnUser,Long>implements ApnUserDao {

}
