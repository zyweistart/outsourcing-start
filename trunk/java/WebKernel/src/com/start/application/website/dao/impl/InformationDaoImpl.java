package com.start.application.website.dao.impl;

import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;
import com.start.application.website.dao.InformationDao;
import com.start.application.website.entity.Information;

@Repository("informationDao")
public final class InformationDaoImpl extends RootDaoImpl<Information,Long>implements InformationDao {

}
