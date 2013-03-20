package com.start.kernel.action;

import com.start.application.system.service.OperatorLogService;
import com.start.framework.context.annnotation.Resource;
import com.start.framework.controller.ActionSupport;
import com.start.framework.controller.IActionResult;

public abstract class RootAction extends ActionSupport {

	private IActionResult result;
	
	@Resource
	protected OperatorLogService operatorLogService;
	
	public IActionResult getResult() {
		return result;
	}

	public void setResult(IActionResult result) {
		this.result = result;
	}
	
}