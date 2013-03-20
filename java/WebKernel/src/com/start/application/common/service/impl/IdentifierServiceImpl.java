package com.start.application.common.service.impl;

import com.start.application.common.dao.IdentifierDao;
import com.start.application.common.entity.Identifier;
import com.start.application.common.service.IdentifierService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;


@Service("identifierService")
public final class IdentifierServiceImpl extends RootServiceImpl<Identifier,Long> 
implements IdentifierService {

	public IdentifierServiceImpl(@Qualifier("identifierDao")IdentifierDao identifierDao) {
		super(identifierDao);
	}

}
