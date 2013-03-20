package com.start.application.website.action;

import java.util.List;

import com.start.application.common.action.AbstractWebSuperAction;
import com.start.application.website.entity.Category;
import com.start.application.website.entity.Information;
import com.start.application.website.service.CategoryService;
import com.start.application.website.service.InformationService;
import com.start.framework.context.annnotation.Controller;
import com.start.framework.context.annnotation.Resource;
import com.start.framework.controller.IActionResult;
import com.start.kernel.config.Variable;
import com.start.kernel.http.Redirect;
import com.start.kernel.http.View;

@Controller("information")
public final class InformationAction extends AbstractWebSuperAction {
	
	private static final String REDIRECT_INFORMATION_MANAGER="/information.do";
	private static final String VIEW_INFORMATION="/admin/information.jsp";
	private static final String VIEW_INFORMATION_MANAGER="/admin/information_manager.jsp";
	
	@Resource
	private CategoryService categoryService;
	@Resource
	private InformationService informationService;
	
	private Information information;
	
	private List<Category> categorys;
	
	private List<Information> informations;

	@Override
	public IActionResult execute() {
		getInformation().setMybatisMapperId(Variable.FINDALL);
		informations=informationService.getResultList(getInformation());
		return new View(VIEW_INFORMATION_MANAGER);
	}
	
	public IActionResult view(){
		Category category=new Category();
		category.setMybatisMapperId(Variable.FINDALL);
		categorys=categoryService.getResultList(category);
		if(getCode()!=null){
			information=new Information();
			information.setCode(getCode());
			information.setMybatisMapperId(Variable.LOAD);
			information=informationService.load(information);
		}
		return new View(VIEW_INFORMATION);
	}
	
	public IActionResult save(){
		if(information!=null){
			switch(informationService.save(information)){
			case 1:
				setMessage("成功");
				break;
			case -1:
				setMessage("类型不能为顶级");
				return view();
			case -2:
				setMessage("栏目分类不存在");
				return view();
			}
		}else{
			//为NULL
		}
		return new Redirect(REDIRECT_INFORMATION_MANAGER);
	}
	
	public IActionResult delete(){
		if(information!=null){
			information.setMybatisMapperId(Variable.DELETE);
			informationService.delete(information);
		}
		return new Redirect(REDIRECT_INFORMATION_MANAGER);
	}
	
	public Information getInformation() {
		if(information==null){
			information=new Information();
		}
		return information;
	}
	
	public void setInformation(Information information) {
		this.information = information;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public List<Category> getCategorys() {
		return categorys;
	}

}