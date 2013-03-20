package com.start.application.common.dao.impl;

import com.start.application.common.dao.FileRecordDao;
import com.start.application.common.entity.FileRecord;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("fileRecordDao")
public final class FileRecordDaoImpl extends RootDaoImpl<FileRecord,Long>implements FileRecordDao {

}
