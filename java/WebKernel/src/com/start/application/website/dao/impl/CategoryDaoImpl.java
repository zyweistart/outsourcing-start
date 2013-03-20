package com.start.application.website.dao.impl;

import com.start.framework.context.annnotation.Repository;
import com.start.kernel.dao.impl.RootDaoImpl;
import com.start.application.website.dao.CategoryDao;
import com.start.application.website.entity.Category;

@Repository("categoryDao")
public final class CategoryDaoImpl extends RootDaoImpl<Category,Long>implements CategoryDao {

}
