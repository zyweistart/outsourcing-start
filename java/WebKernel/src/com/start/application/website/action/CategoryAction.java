package com.start.application.website.action;

import java.util.List;

import com.start.application.common.action.AbstractWebSuperAction;
import com.start.application.website.entity.Category;
import com.start.application.website.service.CategoryService;
import com.start.framework.context.annnotation.Controller;
import com.start.framework.context.annnotation.Resource;
import com.start.framework.controller.IActionResult;
import com.start.kernel.config.Variable;
import com.start.kernel.http.Redirect;
import com.start.kernel.http.View;

@Controller("category")
public final class CategoryAction extends AbstractWebSuperAction {
	
	private static final String REDIRECT_CATEGORY_MANAGER="/category.do";
	private static final String VIEW_CATEGORY="/admin/category.jsp";
	private static final String VIEW_CATEGORY_MANAGER="/admin/category_manager.jsp";
	
	@Resource
	private CategoryService categoryService;
	
	private Category category;
	
	private List<Category> categorys;
	
	@Override
	public IActionResult execute() {
		getCategory().setMybatisMapperId(Variable.FINDALL);
		categorys=categoryService.getResultList(getCategory());
		return new View(VIEW_CATEGORY_MANAGER);
	}
	
	public IActionResult view(){
		Category ca=new Category();
		ca.setMybatisMapperId("findByParentType");
		categorys=categoryService.getResultList(ca);
		if(getCode()!=null){
			category=new Category();
			category.setCode(getCode());
			category.setMybatisMapperId(Variable.LOAD);
			category=categoryService.load(category);
		}
		return new View(VIEW_CATEGORY);
	}

	public IActionResult save(){
		if(category!=null){
			switch(categoryService.save(category)){
			case 1:
				setMessage("成功");
				break;
			case -1:
				setMessage("上级栏目不存在");
				return view();
			case -2:
				setMessage("上级类型只能为顶级");
				return view();
			}
		}else{
			//为NULL
		}
		return new Redirect(REDIRECT_CATEGORY_MANAGER);
	}
	
	public IActionResult delete(){
		if(category!=null){
			category.setMybatisMapperId(Variable.DELETE);
			categoryService.delete(category);
		}
		return new Redirect(REDIRECT_CATEGORY_MANAGER);
	}

	public Category getCategory() {
		if(category==null){
			category=new Category();
		}
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getCategorys() {
		return categorys;
	}

}