package com.start.application.common.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.start.application.common.entity.Account;
import com.start.application.common.entity.LoginLog;
import com.start.application.common.service.AccountService;
import com.start.application.common.service.LoginLogService;
import com.start.application.common.utils.Check;
import com.start.application.system.entity.OperatorLog;
import com.start.framework.context.annnotation.Resource;
import com.start.framework.controller.ControllerInvocation.InvokeAction;
import com.start.framework.controller.IActionResult;
import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.action.RootAction;
import com.start.kernel.config.Business;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.config.IMsg;
import com.start.kernel.config.Variable;
import com.start.kernel.entity.PageQuery;
import com.start.kernel.entity.Root;
import com.start.kernel.exception.AppException;
import com.start.kernel.exception.AppRuntimeException;
import com.start.kernel.http.RequestUtils;
import com.start.kernel.http.XML;
import com.start.kernel.listener.CacheContext;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.PropertiesUtils;
import com.start.kernel.utils.StringCheck;
import com.start.kernel.utils.StringUtils;
import com.start.kernel.utils.TimeUtils;
/**
 * 服务端控制层超类
 * @author Start
 */
public abstract class AbstractServerSuperAction extends RootAction {
	
	private RequestUtils requestUtils; 
	
	@Resource
	protected AccountService accountService;
	@Resource
	protected LoginLogService loginLogService;
	
	@Override
	public void intercept(InvokeAction invokeAction) {
		try{
			CacheContext.activeUser();
			if(ConfigParameter.SYSTEMSTATUS){
				invokeAction.invoke();
			}else{
				result(IMsg._993);
			}
		}catch(Exception e){
			Class<?> cls=e.getClass();
			if(AppRuntimeException.class.equals(cls)){
				result(StringUtils.nullToIntZero(e.getMessage()));
				return;
			}else if(InvocationTargetException.class.equals(cls)){
				Throwable cause=e.getCause();
				if(cause!=null){
					if(AppRuntimeException.class.equals(cause.getClass())){
						result(StringUtils.nullToIntZero(cause.getMessage()));
						return;
					}else if(AppException.class.equals(cause.getClass())){
						result(IMsg._992);
					}else{
						result(IMsg._500);
					}
				}else{
					result(IMsg._500);
				}
			}else{
				result(IMsg._996);
			}
			//非正式环境打印错误信息
			if(!ConfigParameter.SYSTEMFLAG){
				e.printStackTrace();
			}
			LogUtils.printLogError(StackTraceInfo.getTraceInfo() + e.getMessage());
		}finally{
			if(getResult()!=null){
				invokeAction.setActionResult(getResult());
			}
			CacheContext.inactiveUser();
		}
	}
	
	public RequestUtils getRequestUtils() {
		if(requestUtils==null){
			requestUtils=new RequestUtils(request);
		}
		return requestUtils;
	}
	/**
	 * 返回信息给客户端XML,JSON两种格式
	 */
	protected IActionResult result(Integer msg){
		return result(msg, null,null,null);
	}
	
	protected <T extends Root> IActionResult result(T entity){
		return result(IMsg._200,entity,null,null);
	}
	
	protected IActionResult result(Map<String,String> entityMaps){
		return result(IMsg._200,null,entityMaps,null);
	}
	
	protected <T extends Root> IActionResult result(List<T> entityLists,Integer totalCount,Integer currentPage,Integer pageSize){
		return result(IMsg._200,null,null,new PageQuery<T>(entityLists,totalCount,currentPage,pageSize));
	}
	
	protected <T extends Root> IActionResult result(Map<String,Map<String,String>> entityMaps,Integer totalCount,Integer currentPage,Integer pageSize){
		return result(IMsg._200,null,null,new PageQuery<T>(entityMaps,totalCount,currentPage,pageSize));
	}
	
	private <T extends Root> IActionResult result(Integer msg,T entity,Map<String,String> entityMaps,PageQuery<T> pageQuery){
		try{
			//记录操作日志
			OperatorLog operatorLog=new OperatorLog();
			operatorLog.setLoadIndex(StringUtils.nullToIntZero(ConfigParameter.BALANCED_WORKER_INDEX));
			operatorLog.setAction("["+msg+"]-->"+PropertiesUtils.getMessage(String.valueOf(msg)));
			operatorLog.setRequestIp(getRequestUtils().getRequestIP());
			operatorLog.setPath(request.getServletPath());
			operatorLog.setRequestTime(getRequestUtils().getRequesttime());
			operatorLog.setServerTime(new Date());
			if(getRequestUtils().isFileUpload()){
				operatorLog.setContent("File Upload");
			}else{
				operatorLog.setContent(getRequestUtils().getBodyData());
			}
			operatorLog.setMybatisMapperId(Variable.INSERT);
			operatorLogService.save(operatorLog);
		}catch(Exception e){
			System.out.println("operatorLog failure："+e.getMessage());
		}finally{
			operatorLogService.closeSession();
		}
		if(entity!=null){
			setResult(new XML<T>(msg, entity));
		}else if(entityMaps!=null){
			setResult(new XML<T>(msg, entityMaps));
		}else if(pageQuery!=null){
			setResult(new XML<T>(msg, pageQuery));
		}else{
			setResult(new XML<T>(msg));
		}
		return getResult();
	}
	/**
	 * 获取当前登陆信息并对请求的数据进行签名
	 * @param check 检测并激活当前用户
	 */
	protected LoginLog getCurrentLoginLog(Boolean check){
		return getCurrentLoginLog(true,check);
	}
	/**
	 * 获取当前登陆信息并对请求的数据进行签名
	 * @param sign 是否对主体数据进行签名
	 * @param check 检测并激活当前用户
	 */
	protected LoginLog getCurrentLoginLog(Boolean sign,Boolean check){
		//请求访问ID必须放于请求头中
		String accessId=getRequestUtils().getHeaderValue("accessId");
		if(accessId==null){
			//请求访问ID不能为空
			throw new AppRuntimeException(IMsg.ICommon._101001);
		}else if(!StringCheck.checkMd5(accessId)){
			//请求访问ID格式不正确
			throw new AppRuntimeException(IMsg.ICommon._101002);
		}else{
			LoginLog loginLog=new LoginLog();
			loginLog.setAccessId(accessId);
			loginLog.setInvalidTime(new Date());
			loginLog.setMybatisMapperId("findCurrentLoginLogByAccessId");
			loginLog=loginLogService.load(loginLog);
			if(loginLog==null){
				//当前登陆身份不存在或已过期
				throw new AppRuntimeException(IMsg.ICommon._101003);
			}else{
				//判断是否需要对主体数据进行签名一般文件上传无需对主体进行签名
				if(sign){
					getRequestUtils().signaturesHmacSHA1(loginLog.getAccessKey());
				}
				if(check){
					Account account=new Account();
					account.setCode(loginLog.getUserCode());
					account.setMybatisMapperId(Variable.LOAD);
					account=accountService.load(account);
					//账户检测
					loginLog.setAccount(Check.checkAccount(account));
					//激活当前登陆信息
					loginLog.setInvalidTime(TimeUtils.AddHour(Business.USERLOGINLOG_ACTIVATION_HOUR));
					loginLog.setMybatisMapperId("activationCurrentLoginLog");
					loginLogService.save(loginLog);
				}
				return loginLog;
			}
		}
	}
	
}