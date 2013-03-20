package com.start.application.discover.service.impl;

import com.start.application.discover.dao.CommentDao;
import com.start.application.discover.entity.Comment;
import com.start.application.discover.service.CommentService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.service.impl.RootServiceImpl;


@Service("commentService")
public final class CommentServiceImpl extends RootServiceImpl<Comment,Long> 
implements CommentService {

	public CommentServiceImpl(@Qualifier("commentDao")CommentDao commentDao) {
		super(commentDao);
	}

}
