package com.start.application.common.service.impl;

import com.start.application.common.dao.AccountDao;
import com.start.application.common.entity.Account;
import com.start.application.common.service.AccountService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;


@Service("accountService")
public final class AccountServiceImpl extends RootServiceImpl<Account,Long> 
implements AccountService {

	public AccountServiceImpl(@Qualifier("accountDao")AccountDao accountDao) {
		super(accountDao);
	}

}
