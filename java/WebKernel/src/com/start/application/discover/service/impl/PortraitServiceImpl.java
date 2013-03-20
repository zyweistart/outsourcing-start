package com.start.application.discover.service.impl;

import com.start.application.discover.dao.PortraitDao;
import com.start.application.discover.entity.Portrait;
import com.start.application.discover.service.PortraitService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;


@Service("portraitService")
public final class PortraitServiceImpl extends RootServiceImpl<Portrait,Long> 
implements PortraitService {

	public PortraitServiceImpl(@Qualifier("portraitDao")PortraitDao portraitDao) {
		super(portraitDao);
	}

}
