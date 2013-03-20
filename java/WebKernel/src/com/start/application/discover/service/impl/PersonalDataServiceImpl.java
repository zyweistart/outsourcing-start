package com.start.application.discover.service.impl;

import com.start.application.discover.dao.PersonalDataDao;
import com.start.application.discover.entity.PersonalData;
import com.start.application.discover.service.PersonalDataService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;


@Service("personalDataService")
public final class PersonalDataServiceImpl extends RootServiceImpl<PersonalData,Long> 
implements PersonalDataService {

	public PersonalDataServiceImpl(@Qualifier("personalDataDao")PersonalDataDao personalDataDao) {
		super(personalDataDao);
	}

}
