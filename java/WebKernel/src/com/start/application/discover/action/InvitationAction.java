package com.start.application.discover.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.start.application.common.action.AbstractServerSuperAction;
import com.start.application.common.entity.LoginLog;
import com.start.application.discover.entity.Comment;
import com.start.application.discover.entity.Invitation;
import com.start.application.discover.service.CommentService;
import com.start.application.discover.service.InvitationService;
import com.start.framework.context.annnotation.Controller;
import com.start.framework.context.annnotation.Resource;
import com.start.kernel.config.IMsg;
import com.start.kernel.config.Variable;
import com.start.kernel.utils.StringCheck;
import com.start.kernel.utils.StringUtils;
/**
 * 帖子
 * @author Start
 */
@Controller("invitation")
public class InvitationAction extends AbstractServerSuperAction {

	@Resource
	private InvitationService invitationService;
	@Resource
	private CommentService commentService;
	/**
	 * 发布
	 */
	public void post() {
		Invitation invitation=getRequestUtils().initPostData(Invitation.class);
		if(StringUtils.isEmpty(invitation.getContent())){
			//内容不能为空
			result(IMsg.IDiscover._303001);
		}else if(invitation.getLongitude()==null){
			//经度不能为空
			result(IMsg.IDiscover._303002);
		}else if(invitation.getLatitude()==null){
			//纬度不能为空
			result(IMsg.IDiscover._303003);
		}else{
			LoginLog loginLog=getCurrentLoginLog(true);
			invitation.setPubDate(new Date());
			invitation.setUserCode(loginLog.getUserCode());
			invitation.setMybatisMapperId(Variable.INSERT);
			invitationService.save(invitation);
			result(IMsg._200);
		}
	}
	/**
	 * 详细
	 */
	public void load(){
		Invitation invitation=getRequestUtils().initPostData(Invitation.class);
		getRequestUtils().signaturesMD5();
		if(StringUtils.isEmpty(invitation.getCode())){
			//贴子编号不能为空
			result(IMsg.IDiscover._303004);
		}else if(!StringCheck.checkMd5(invitation.getCode())){
			//贴子编号格式不正确
			result(IMsg.IDiscover._303005);
		}else{
			invitation.setMybatisMapperId(Variable.LOAD);
			invitation=invitationService.load(invitation);
			if(invitation!=null){
				result(invitation);
			}else{
				//贴子不存在
				result(IMsg.IDiscover._303006);
			}
		}
	}
	/**
	 * 删除
	 */
	public void remove(){
		Invitation invitation=getRequestUtils().initPostData(Invitation.class);
		if(StringUtils.isEmpty(invitation.getCode())){
			//贴子编号不能为空
			result(IMsg.IDiscover._303004);
		}else if(!StringCheck.checkMd5(invitation.getCode())){
			//贴子编号格式不正确
			result(IMsg.IDiscover._303005);
		}else{
			LoginLog loginLog=getCurrentLoginLog(true);
			invitation.setUserCode(loginLog.getUserCode());
			invitation.setMybatisMapperId(Variable.DELETE);
			if(invitationService.delete(invitation)>0){
				//TODO:删除贴子对应的评论
				result(IMsg._200);
			}else{
				//贴子不存在
				result(IMsg.IDiscover._303006);
			}
		}
	}
	/**
	 * 获取我的贴子列表
	 * <pre>
	 *     <invitation>
 	 *      <content>this is post main content!</content>
 	 *      <status>1</status>
 	 *      <usercode>2f08f7b02c2b5bd98511ac8ef8c40840</usercode>
 	 *      <longitude>127.04382</longitude>
 	 *      <latitude>92.348</latitude>
  	 *     <pubdate>Fri Sep 21 21:24:29 CST 2012</pubdate>
  	 *    </invitation>
  	 *    ............
     * </pre>
	 */
	public void my(){
		LoginLog loginLog=getCurrentLoginLog(true);
		Invitation invitation=new Invitation();
		invitation.setUserCode(loginLog.getUserCode());
		invitation.setMybatisMapperId("my");
		List<Invitation> invitations=invitationService.getResultList(invitation);
		invitation.setMybatisMapperId("mycount");
		Integer totalCount=invitationService.getSingleResult(invitation);
		result(invitations,totalCount,invitation.getCurrentPage(),invitation.getPageSize());
	}
	/**
	 * 获取好友的贴子列表
	 */
	public void friend(){
		LoginLog loginLog=getCurrentLoginLog(true);
		Invitation invitation=new Invitation();
		invitation.setUserCode(loginLog.getUserCode());
		invitation.setMybatisMapperId("friend");
		invitationService.getResultList(invitation);
	}
	/**
	 * 获取附近的贴子列表
	 */
	public void nearby(){
		Invitation invitation=getRequestUtils().initPostData(Invitation.class);
		getRequestUtils().signaturesMD5();
		if(invitation.getLongitude()==null){
			//经度不能为空
			result(IMsg.IDiscover._303002);
		}else if(invitation.getLatitude()==null){
			//纬度不能为空
			result(IMsg.IDiscover._303003);
		}else{
			invitation.setMybatisMapperId("nearby");
			Map<String,Map<String,String>> invitations=invitationService.getResultMap(invitation,"code");
			invitation.setMybatisMapperId("nearbycount");
			Integer totalCount=invitationService.getSingleResult(invitation);
			result(invitations,totalCount,invitation.getCurrentPage(),invitation.getPageSize());
		}
	}
	/**
	 * 提交评论
	 */
	public void comment(){
		Comment comment=getRequestUtils().initPostData(Comment.class);
		if(StringUtils.isEmpty(comment.getInvitationCode())){
			//贴子编号不能为空 
			result(IMsg.IDiscover._303004);
		}else if(!StringCheck.checkMd5(comment.getInvitationCode())){
			//贴子编号格式不正确 
			result(IMsg.IDiscover._303005);
		}else if(StringUtils.isEmpty(comment.getContent())){
			//内容不能为空
			result(IMsg.IDiscover._303001);
		}else if(comment.getLongitude()==null){
			//经度不能为空
			result(IMsg.IDiscover._303002);
		}else if(comment.getLatitude()==null){
			//纬度不能为空
			result(IMsg.IDiscover._303003);
		}else{
			LoginLog loginLog=getCurrentLoginLog(true);
			comment.setPubDate(new Date());
			comment.setUserCode(loginLog.getUserCode());
			comment.setMybatisMapperId(Variable.INSERT);
			commentService.save(comment);
			result(IMsg._200);
		}
	}
	/**
	 * 获取贴子的评论
	 * <pre>
	 * <context>
	 * 
	 * </context>
	 * <pre>
	 */
	public void invitcommlist(){
		Comment comment=getRequestUtils().initPostData(Comment.class);
		getRequestUtils().signaturesMD5();
		if(comment.getInvitationCode()==null){
			//贴子编号不能为空 
			result(IMsg.IDiscover._303004);
		}else if(!StringCheck.checkMd5(comment.getInvitationCode())){
			//贴子编号格式不正确 
			result(IMsg.IDiscover._303005);
		}else{
			comment.setMybatisMapperId("invlcommentlist");
			List<Comment> comments=commentService.getResultList(comment);
			comment.setMybatisMapperId("invlcommentlistcount");
			Integer totalCount=commentService.getSingleResult(comment);
			result(comments,totalCount,comment.getCurrentPage(),comment.getPageSize());
		}
	}
	
}