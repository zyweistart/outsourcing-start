package com.start.framework.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.start.framework.config.Message;
import com.start.framework.context.annnotation.PersistenceContext;
import com.start.framework.context.annnotation.Qualifier;
import com.start.framework.context.annnotation.Resource;
import com.start.framework.mybatis.MybatisSessionFactory;
import com.start.framework.utils.StackTraceInfo;
/**
 * 容器参数注入
 * @author Start
 */
public final class ContextInjection {
	
	private static final Log log=LogFactory.getLog(ContextInjection.class);
	/**
	 * 缓存PersistenceContext注解的容器字段
	 */
	private static final Map<Class<?>,Set<Field>> cachePersistenceContextField=new ConcurrentHashMap<Class<?>,Set<Field>>();
	/**
	 * 缓存Resource注解的字段
	 */
	private static final Map<Class<?>,Map<String,Field>> cacheResourceField=new ConcurrentHashMap<Class<?>,Map<String,Field>>();
	
	private SqlSession sqlSession;
	
	public SqlSession getSqlSession() {
		if(sqlSession==null){
			//默认事务模式设为自动提交
			sqlSession=MybatisSessionFactory.getInstance().openSession(true);
		}
		return sqlSession;
	}

	private final Map<String,Object> resourceInjection=new ConcurrentHashMap<String,Object>();
	
	public Object getResourceInjectionValue(String resourceName){
		Object tarObj=resourceInjection.get(resourceName);
		if(tarObj==null){
			Class<?> prototype=AppContext.getInjections().get(resourceName);
			if(prototype!=null){
				tarObj= createInstance(prototype);
				resourceInjection.put(resourceName, tarObj);
			}else{
				throw new NullPointerException(Message.getMessage(Message.PM_1002, resourceName));
			}
		}
		return tarObj;
	}
	
	public Object createInstance(Class<?> prototype){
		Object instance = null;
		Constructor<?>[] constructors = prototype.getConstructors();
		for (Constructor<?> constructor : constructors) {
			if(constructor.getParameterTypes().length==1){
				Annotation[][] annotations = constructor.getParameterAnnotations();
				if(annotations.length==1){
					for(Annotation[] anns:annotations){
						for (Annotation annotation : anns) {
							if (annotation.annotationType().equals(Qualifier.class)) {
								try {
									instance=constructor.newInstance(getResourceInjectionValue(((Qualifier)annotation).value()));
								} catch (Exception e) {
									log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
								}
							}
						}
					}
				}
			}
		}
		//如果构造函数未注册则创造一个实例
		if (instance == null) {
			try {
				//创建的对象必须要有一个默认的构造函数
				instance = prototype.newInstance();
			} catch (Exception e) {
				log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}
		Map<String,Field> cacheField=cacheResourceField.get(prototype);
		Set<Field> cacheEntityContextField=cachePersistenceContextField.get(prototype);
		if(cacheField==null&&cacheEntityContextField==null){
			//创建缓存
			cacheField=new HashMap<String,Field>();
			//创建缓存容器
			cacheEntityContextField=new HashSet<Field>();
			//拷贝一份字符码
			Class<?> cloneProperty=prototype;
			while (true) {
				if (cloneProperty != null) {
					Field[] fields = cloneProperty.getDeclaredFields();
					for (Field field : fields) {
						Resource resource=field.getAnnotation(Resource.class);
						if (resource!=null) {
							//注入的资源名称
							String resourceName=resource.value().isEmpty()?field.getName():resource.value();
							field.setAccessible(true);
							try {
								field.set(instance,getResourceInjectionValue(resourceName));
							} catch (Exception e) {
								log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
							}
							//加入缓存
							cacheField.put(resourceName, field);
						}else if (field.getAnnotation(PersistenceContext.class)!=null) {
							field.setAccessible(true);
							try {
								field.set(instance,getSqlSession());
							} catch (Exception e) {
								log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
							}
							//加入缓存
							cacheEntityContextField.add(field);
						}
					}
				} else {
					break;
				}
				cloneProperty = cloneProperty.getSuperclass();
			}
			cacheResourceField.put(prototype,cacheField);
			cachePersistenceContextField.put(prototype, cacheEntityContextField);
		}else{
			//字段注入
			for(String key:cacheField.keySet()){
				Field field=cacheField.get(key);
				try {
					//由于在存入缓存时已经调用field.setAccessible(true);故在此不需要重复调用
					field.set(instance, getResourceInjectionValue(key));
				} catch (Exception e) {
					log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
				}
			}
			//注入实体容器对象
			for(Field field:cacheEntityContextField){
				try {
					//由于在存入缓存时已经调用field.setAccessible(true);故在此不需要重复调用
					field.set(instance, getSqlSession());
				} catch (Exception e) {
					log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
				}
			}
		}
		return instance;
	}

	@Override
	public void finalize() throws Throwable {
		if(sqlSession!=null){
			try{
				getSqlSession().close();
			}finally{
				sqlSession=null;
			}
		}
		super.finalize();
	}
	
}