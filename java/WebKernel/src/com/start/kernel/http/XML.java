package com.start.kernel.http;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.start.framework.config.AppConstant;
import com.start.framework.controller.IActionResult;
import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.entity.PageQuery;
import com.start.kernel.entity.Root;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.PropertiesUtils;
import com.start.kernel.utils.StringUtils;
/**
 * @author Start
 * @format
 * <pre>
 * 	<result>
 * 		<info>
 * 			<code>返回编号</code>
 * 			<message>提示信息</message>
 * 		</info>
 * 		<pageinfo>
 * 			<totalpage>总页数</totalpage>
 * 			<pagesize>页大小</pagesize>
 * 			<totalcount>总记录数</totalcount>
 * 			<currentpage>当前页数</currentpage>
 * 		<pageinfo>
 * 		<maincontent>
 * 			<entity>
 * 				<key1>value1</key1>
 * 				<key2>value2</key2>
 * 				...................
 * 				<keyn>valuen</keyn>
 * 			</entity>
 * 		</maincontent>
 * </result>
 * </pre>
 */
public class XML<T extends Root>  implements IActionResult {

	private static final String RESULT="result";
	
	private static final String INFO="info";
	private static final String CODE="code";
	private static final String MESSAGE="message";
	
	private static final String PAGEINFO="pageinfo";
	private static final String TOTALPAGE="totalpage";
	private static final String PAGESIZE="pagesize";
	private static final String TOTALCOUNT="totalcount";
	private static final String CURRENTPAGE="currentpage";
	
	private static final String MAINCONTENT="maincontent";
	private static final String ENTITY="entity";
	/**
	 * 实体方法缓存
	 */
	private static final Map<Class<?>,List<Method>> cacheMethods=new HashMap<Class<?>,List<Method>>();
	
	private Integer info;
	
	private T entity;
	
	private Map<String,String> entityMaps;
	
	private PageQuery<T> pageQuery;
	
	public XML(Integer info){
		this.info=info;
	}
	
	public XML(Integer info,T entity){
		this.info=info;
		this.entity=entity;
	}
	
	public XML(Integer info,Map<String,String> entityMaps){
		this.info=info;
		this.entityMaps=entityMaps;
	}
	
	public XML(Integer info,PageQuery<T> pageQuery){
		this.info=info;
		this.pageQuery=pageQuery;
	}
	
	@Override
	public void doExecute(ActionResultInvocation invocation) {
		Document doc = new Document();
		Element root = new Element(RESULT);
		doc.setRootElement(root);
		Element eleInfo=new Element(INFO);
		root.getChildren().add(eleInfo);
		eleInfo.getChildren().add(new Element(CODE).setText(String.valueOf(info)));
		eleInfo.getChildren().add(new Element(MESSAGE).setText(PropertiesUtils.getMessage(String.valueOf(info))));
		try {
			if(entity!=null){
				Element entityNameInfo=new Element(MAINCONTENT);
				root.getChildren().add(entityNameInfo);
				Class<?> prototype=entity.getClass();
				List<Method> methods=cacheMethods.get(prototype);
				if(methods==null){
					methods=new ArrayList<Method>();
					for(Method m:prototype.getDeclaredMethods()){
						if(m.getName().startsWith("get")){
							methods.add(m);
						}
					}
					cacheMethods.put(prototype, methods);
				}
				for(Method m:methods){
					String fieldName=m.getName().substring(3);
					Object value=m.invoke(entity);
					if(value!=null){
						entityNameInfo.getChildren().add(new Element(fieldName.toLowerCase()).setText(String.valueOf(value)));
					}
				}
			}else if(entityMaps!=null){
				Element entityNameInfo=new Element(MAINCONTENT);
				root.getChildren().add(entityNameInfo);
				for(String key:entityMaps.keySet()){
					entityNameInfo.getChildren().add(new Element(key.toLowerCase()).setText(String.valueOf(entityMaps.get(key))));
				}
			}else if(pageQuery!=null){
				Element elePageInfo=new Element(PAGEINFO);
				root.getChildren().add(elePageInfo);
				elePageInfo.getChildren().add(new Element(TOTALPAGE).setText(String.valueOf(pageQuery.getTotalPage())));
				elePageInfo.getChildren().add(new Element(PAGESIZE).setText(String.valueOf(pageQuery.getPageSize())));
				elePageInfo.getChildren().add(new Element(TOTALCOUNT).setText(String.valueOf(pageQuery.getTotalCount())));
				elePageInfo.getChildren().add(new Element(CURRENTPAGE).setText(String.valueOf(pageQuery.getCurrentPage())));
				Element eles=new Element(MAINCONTENT);
				root.getChildren().add(eles);
				if(pageQuery.getEntityLists()!=null){
					for(T entity:pageQuery.getEntityLists()){
						Element entityNameInfo=new Element(ENTITY);
						eles.getChildren().add(entityNameInfo);
						Class<?> prototype=entity.getClass();
						List<Method> methods=cacheMethods.get(prototype);
						if(methods==null){
							methods=new ArrayList<Method>();
							for(Method m:prototype.getDeclaredMethods()){
								if(m.getName().startsWith("get")){
									methods.add(m);
								}
							}
							cacheMethods.put(prototype, methods);
						}
						for(Method m:methods){
							String fieldName=m.getName().substring(3);
							Object value=m.invoke(entity);
							if(value!=null){
								entityNameInfo.getChildren().add(new Element(fieldName.toLowerCase()).setText(String.valueOf(value)));
							}
						}
					}
				}else if(pageQuery.getEntityMaps()!=null){
					for(String key:pageQuery.getEntityMaps().keySet()){
						Element entityNameInfo=new Element(ENTITY);
						eles.getChildren().add(entityNameInfo);
						Map<String,String> p=pageQuery.getEntityMaps().get(key);
						for(String k:p.keySet()){
							entityNameInfo.getChildren().add(new Element(k.toLowerCase()).setText(String.valueOf(p.get(k))));
						}
					}
				}
			}
			Format format = Format.getPrettyFormat();
			format.setEncoding(AppConstant.ENCODING);
			if(ConfigParameter.SYSTEMFLAG){
				//去除空格
				format.setIndent("");
				//去除格式
				format.setLineSeparator("");
			}
			XMLOutputter xmlOutputter = new XMLOutputter(format);
			HttpServletResponse response=invocation.getResponse();
			if(response != null) {
				response.setCharacterEncoding(AppConstant.ENCODING);
		    	response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				xmlOutputter.output(doc, response.getWriter());
				LogUtils.logInfo("输出XML：" + xmlOutputter.outputString(doc).trim());
			} else {
				xmlOutputter.output(doc, System.out);
			}
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + "输出XML异常：" + StringUtils.nullToStrTrim(e.getMessage()));
		} finally {
			doc = null;
		}
	}
	
}