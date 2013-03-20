package com.start.application.discover.dao.impl;

import com.start.application.discover.dao.PersonalDataDao;
import com.start.application.discover.entity.PersonalData;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("personalDataDao")
public final class PersonalDataDaoImpl extends RootDaoImpl<PersonalData,Long>implements PersonalDataDao {

}
