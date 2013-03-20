package com.start.application.system.service.impl;

import com.start.application.system.dao.OperatorLogDao;
import com.start.application.system.entity.OperatorLog;
import com.start.application.system.service.OperatorLogService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;


@Service("operatorLogService")
public final class OperatorLogServiceImpl extends RootServiceImpl<OperatorLog,Long> 
implements OperatorLogService {

	public OperatorLogServiceImpl(@Qualifier("operatorLogDao")OperatorLogDao operatorLogDao) {
		super(operatorLogDao);
	}

}
