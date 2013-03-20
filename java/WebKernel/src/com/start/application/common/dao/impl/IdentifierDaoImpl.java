package com.start.application.common.dao.impl;

import com.start.application.common.dao.IdentifierDao;
import com.start.application.common.entity.Identifier;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("identifierDao")
public final class IdentifierDaoImpl extends RootDaoImpl<Identifier,Long>implements IdentifierDao {

}
