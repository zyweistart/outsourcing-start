package com.start.application.system.dao.impl;

import com.start.application.system.dao.StorageDao;
import com.start.application.system.entity.Storage;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("storageDao")
public final class StorageDaoImpl extends RootDaoImpl<Storage,Long>implements StorageDao {

}
