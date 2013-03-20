package com.start.application.website.service.impl;

import com.start.application.website.dao.CategoryDao;
import com.start.application.website.entity.Category;
import com.start.application.website.service.CategoryService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.config.Variable;
import com.start.kernel.service.impl.RootServiceImpl;

@Service("categoryService")
public final class CategoryServiceImpl extends RootServiceImpl<Category,Long> 
implements CategoryService {

	public CategoryServiceImpl(@Qualifier("categoryDao")CategoryDao categoryDao) {
		super(categoryDao);
	}
	/**
	 * @return
	 *  >1:成功
	 * -1:上级栏目不存在
	 * -2:上级类型只能为顶级
	 */
	@Override
	public int save(Category category) {
		if(!"".equals(category.getParent())){
			Category ca=new Category();
			ca.setCode(category.getParent());
			ca.setMybatisMapperId(Variable.LOAD);
			ca=super.load(ca);
			if(ca==null){
				return -1;
			}else{
				if(ca.getType()!=0){
					return -2;
				}
				category.setLevel(ca.getLevel()+1);
			}
		}else{
			category.setLevel(1);
		}
		category.setMybatisMapperId(Variable.INSERT);
		return super.save(category);
	}

}