package com.start.application.discover.action;

import com.start.application.common.action.AbstractServerSuperAction;
import com.start.application.common.entity.LoginLog;
import com.start.application.discover.service.RelationService;
import com.start.framework.context.annnotation.Controller;
import com.start.framework.context.annnotation.Resource;
import com.start.kernel.config.IMsg;
import com.start.kernel.utils.StringUtils;

@Controller("relation")
public class RelationAction extends AbstractServerSuperAction {
	
	@Resource
	private RelationService relationService;
	/**
	 * 关注好友
	 */
	public void follow(){
		getRequestUtils().initPostData(null);
		String friendCode=getRequestUtils().getXMLDocument().get("friendCode");
		if(StringUtils.isEmpty(friendCode)){
			//好友编号验证
		}else{
			LoginLog loginLog=getCurrentLoginLog(true);
			relationService.follow(loginLog.getUserCode(), friendCode);
			result(IMsg._200);
		}
	}
	/**
	 * 取消关注
	 */
	public void unfollow(){
		getRequestUtils().initPostData(null);
		String friendCode=getRequestUtils().getXMLDocument().get("friendCode");
		if(StringUtils.isEmpty(friendCode)){
			//好友编号验证
		}else{
			LoginLog loginLog=getCurrentLoginLog(true);
			relationService.unfollow(loginLog.getUserCode(), friendCode);
			result(IMsg._200);
		}
	}
	/**
	 * 判断是否关注了对应好友
	 */
	public void isfollowing(){
		getRequestUtils().initPostData(null);
		String friendCode=getRequestUtils().getXMLDocument().get("friendCode");
		if(StringUtils.isEmpty(friendCode)){
			//好友编号验证
		}else{
			LoginLog loginLog=getCurrentLoginLog(true);
			relationService.isfollowing(loginLog.getUserCode(), friendCode);
			result(IMsg._200);
		}
	}
	/**
	 * 判断是否存在对应好友的粉丝
	 */
	public void isfollowedBy(){
		getRequestUtils().initPostData(null);
		String friendCode=getRequestUtils().getXMLDocument().get("friendCode");
		if(StringUtils.isEmpty(friendCode)){
			//好友编号验证
		}else{
			LoginLog loginLog=getCurrentLoginLog(true);
			relationService.isfollowedBy(loginLog.getUserCode(), friendCode);
			result(IMsg._200);
		}
	}
	
}