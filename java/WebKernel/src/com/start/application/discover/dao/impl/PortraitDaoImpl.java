package com.start.application.discover.dao.impl;

import com.start.application.discover.dao.PortraitDao;
import com.start.application.discover.entity.Portrait;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("portraitDao")
public final class PortraitDaoImpl extends RootDaoImpl<Portrait,Long>implements PortraitDao {

}
