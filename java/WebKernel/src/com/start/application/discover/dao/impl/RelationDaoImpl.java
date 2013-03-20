package com.start.application.discover.dao.impl;

import com.start.application.discover.dao.RelationDao;
import com.start.application.discover.entity.Relation;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("relationDao")
public final class RelationDaoImpl extends RootDaoImpl<Relation,Long>implements RelationDao {

}
