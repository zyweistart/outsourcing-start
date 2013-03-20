package com.start.application.discover.service.impl;

import com.start.application.discover.dao.InvitationDao;
import com.start.application.discover.entity.Invitation;
import com.start.application.discover.service.InvitationService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;


@Service("invitationService")
public final class InvitationServiceImpl extends RootServiceImpl<Invitation,Long> 
implements InvitationService {

	public InvitationServiceImpl(@Qualifier("invitationDao")InvitationDao invitationDao) {
		super(invitationDao);
	}

}
