package com.start.application.common.dao.impl;

import com.start.application.common.dao.AccountDao;
import com.start.application.common.entity.Account;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("accountDao")
public final class AccountDaoImpl extends RootDaoImpl<Account,Long>implements AccountDao {

}
