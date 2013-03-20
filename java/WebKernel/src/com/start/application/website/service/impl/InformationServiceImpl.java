package com.start.application.website.service.impl;

import com.start.application.website.dao.InformationDao;
import com.start.application.website.entity.Category;
import com.start.application.website.entity.Information;
import com.start.application.website.service.CategoryService;
import com.start.application.website.service.InformationService;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Resource;
import com.start.framework.context.annnotation.Service;
import com.start.kernel.config.Variable;
import com.start.kernel.service.impl.RootServiceImpl;

@Service("informationService")
public final class InformationServiceImpl extends
		RootServiceImpl<Information, Long> implements InformationService {

	@Resource
	private CategoryService categoryService;

	public InformationServiceImpl(@Qualifier("informationDao") InformationDao informationDao) {
		super(informationDao);
	}

	/**
	 * @return 
	 * >1:成功 
	 * -1:类型不能为顶级 
	 * -2:栏目分类不存在
	 */
	@Override
	public int save(Information information) {
		Category category = new Category();
		category.setCode(information.getCategory());
		category.setMybatisMapperId(Variable.LOAD);
		category = categoryService.load(category);
		if (category != null) {
			if (category.getType() != 0) {
				information.setHits(0);
				information.setMybatisMapperId(Variable.INSERT);
				return super.save(information);
			} else {
				return -1;
			}
		} else {
			return -2;
		}
	}

}