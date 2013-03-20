package com.start.application.discover.dao.impl;

import com.start.application.discover.dao.CommentDao;
import com.start.application.discover.entity.Comment;
import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;

@Repository("commentDao")
public final class CommentDaoImpl extends RootDaoImpl<Comment,Long>implements CommentDao {

}
