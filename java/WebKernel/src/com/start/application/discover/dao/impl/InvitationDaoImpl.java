package com.start.application.discover.dao.impl;

import com.start.application.discover.dao.InvitationDao;
import com.start.application.discover.entity.Invitation;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("invitationDao")
public final class InvitationDaoImpl extends RootDaoImpl<Invitation,Long>implements InvitationDao {

}
