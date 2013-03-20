package com.start.framework.context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.start.framework.config.AppConfig;
import com.start.framework.config.AppConstant;
import com.start.framework.config.Message;
import com.start.framework.context.annnotation.Controller;
import com.start.framework.context.annnotation.Entity;
import com.start.framework.context.annnotation.Repository;
import com.start.framework.context.annnotation.Service;
import com.start.framework.exception.AnnoationError;
import com.start.framework.exception.ConfigInitError;
import com.start.framework.utils.ClassLoaderUtils;
import com.start.framework.utils.ClassUtils;

public class Init {
	/**
	 * 加载配置文件
	 */
	public static void loadConfigFile() {
		//框架默认的配置文件
		String FRAMEWORKCONFIGFILEPATH="com/start/framework/META-INF/start-config.xml";
		//应用的配置文件
		String APPCONFIGFILEPATH=AppConfig.RESOURCEPATH+"/platform-config.xml";
		AppConfig.Constants.clear();
		AppConfig.Interceptors.clear();
		AppConfig.Persistents.clear();
		AppConfig.ConnectionConfigs.clear();
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder=null;
		try{
			factory=DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			Document[] docs=new Document[]{
					//加载框架默认的配置文件
					builder.parse(ClassLoaderUtils.getResourceAsStream(FRAMEWORKCONFIGFILEPATH,AppConfig.class)),
					///加载自定义应用的配置文件
					builder.parse(new File(APPCONFIGFILEPATH))
			};
			for(Document doc:docs){
				NodeList  mainNodeList =doc.getChildNodes();
				for(int i=0;i<mainNodeList.getLength();i++){
					Node mainNode=mainNodeList.item(i);
					NodeList nodes=mainNode.getChildNodes();
					for(int j=0;j<nodes.getLength();j++){
						Node node=nodes.item(j);
						if(node.getNodeName().equals("Constants")){
							//常量配置
							NodeList childNodes=node.getChildNodes();
							for(int k=0;k<childNodes.getLength();k++){
								Node childNode=childNodes.item(k);
								if(childNode.getNodeName().equals("Constant")){
									NamedNodeMap nodeAtts=childNode.getAttributes();
									String key=null,value=null;
									for(int l=0;l<nodeAtts.getLength();l++){
										Node nodeAtt=nodeAtts.item(l);
										if(nodeAtt.getNodeName().equals("name")){
											key=nodeAtt.getNodeValue();
										}else if(nodeAtt.getNodeName().equals("value")){
											value=nodeAtt.getNodeValue();
										}
									}
									if(value==null){
										value=childNode.getTextContent().trim();
									}
									AppConfig.Constants.put(key, value);
								}
							}
						}else if(node.getNodeName().equals("Interceptors")){
							//是否重新加载拦截器配置
							Node reload=node.getAttributes().getNamedItem("reload");
							if(reload!=null){
								if(Boolean.parseBoolean(reload.getNodeValue())){
									AppConfig.Interceptors.clear();
								}
							}
							//拦截器配置
							NodeList childNodes=node.getChildNodes();
							for(int k=0;k<childNodes.getLength();k++){
								Node childNode=childNodes.item(k);
								if(childNode.getNodeName().equals("Interceptor")){
									NamedNodeMap nodeAtts=childNode.getAttributes();
									for(int l=0;l<nodeAtts.getLength();l++){
										Node nodeAtt=nodeAtts.item(l);
										if(nodeAtt.getNodeName().equals("class")){
											try {
												//加载时不进行初始化操作,只有在调用newInstance()方法才进行初始化操作
												AppConfig.Interceptors.add(Class.forName(nodeAtt.getNodeValue(),false,Init.class.getClassLoader()));
											} catch (Exception e) {
												throw new ConfigInitError(e);
											}
										}
									}
								}
							}
						}else if(node.getNodeName().equals("Persistent")){
							//持久化配置
							NodeList childNodes=node.getChildNodes();
							for(int k=0;k<childNodes.getLength();k++){
								Node childNode=childNodes.item(k);
								if(childNode.getNodeName().equals("Param")){
									NamedNodeMap nodeAtts=childNode.getAttributes();
									String key=null,value=null;
									for(int l=0;l<nodeAtts.getLength();l++){
										Node nodeAtt=nodeAtts.item(l);
										if(nodeAtt.getNodeName().equals("name")){
											key=nodeAtt.getNodeValue();
										}else if(nodeAtt.getNodeName().equals("value")){
											value=nodeAtt.getNodeValue();
										}
									}
									if(value==null){
										value=childNode.getTextContent();
									}
									AppConfig.Persistents.put(key, value);
								}else if(childNode.getNodeName().equals("Connection")){
									NodeList perChildNodes=childNode.getChildNodes();
									Properties properties=new Properties();
									for(int c=0;c<perChildNodes.getLength();c++){
										Node perNode=perChildNodes.item(c);
										if(perNode.getNodeName().equals("propertie")){
											properties.put(perNode.getAttributes().getNamedItem("name").getTextContent(),
													perNode.getTextContent());
										}
									}
									AppConfig.ConnectionConfigs.put(childNode.getAttributes().getNamedItem("name").getTextContent(),properties);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ConfigInitError(e);
		}finally{
			builder=null;
			factory=null;
		}
	}
	/**
	 * 加载容器类对象
	 */
	public static void loadContextClass(){
		AppContext.getActions().clear();
		AppContext.getInjections().clear();
		AppContext.getEntitys().clear();
		List<Class<?>> classes = new ArrayList<Class<?>>();
		// 扫描类文件
		for (String packName : AppConstant.CLASSSCANPATH
				.split(",")) {
			packName = packName.trim();
			ClassUtils.findAndAddClassesInPackageByFile(
					packName,
					AppConfig.RESOURCEPATH
							+ packName.replace(".", AppConstant.FILESEPARATOR),
					true, classes);
		}
		for (Class<?> clasz : classes) {
			// 控制层
			Controller controller = clasz.getAnnotation(Controller.class);
			if (controller != null) {
				if (AppContext.getActions().get(controller.value()) == null) {
					AppContext.getActions().put(controller.value(), clasz);
					continue;
				}
				throw new AnnoationError(Message.getMessage(Message.PM_1003,
						controller.value()));
			}
			// 服务层
			Service service = clasz.getAnnotation(Service.class);
			if (service != null) {
				if (AppContext.getInjections().get(service.value()) == null) {
					AppContext.getInjections().put(service.value(), clasz);
					continue;
				}
				throw new AnnoationError(Message.getMessage(Message.PM_1003,
						service.value()));
			}
			// 数据访问层
			Repository repository = clasz.getAnnotation(Repository.class);
			if (repository != null) {
				if (AppContext.getInjections().get(repository.value()) == null) {
					AppContext.getInjections().put(repository.value(), clasz);
					continue;
				}
				throw new AnnoationError(Message.getMessage(Message.PM_1003,
						repository.value()));
			}
			// 实体层
			Entity entity = clasz.getAnnotation(Entity.class);
			if (entity != null) {
				if (AppContext.getEntitys().get(entity.value()) == null) {
					AppContext.getEntitys().put(entity.value(), clasz);
					continue;
				}
				throw new AnnoationError(Message.getMessage(Message.PM_1003,
						entity.value()));
			}
		}
	}
	
}