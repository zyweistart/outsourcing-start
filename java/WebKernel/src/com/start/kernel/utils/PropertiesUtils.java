package com.start.kernel.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.Variable;
/**
 * 属性数据处理工具类
 * @author Start
 */
public class PropertiesUtils {

	private static final String BUNDLE_NAME = "data.properties.";

	private static final String BUNDLE_NAME_BUSINES = BUNDLE_NAME + "busines";
	private static final String BUNDLE_NAME_MESSAGE = BUNDLE_NAME + "message";

	private final static ResourceBundle RESOURCE_BUNDLE_BUSINES;
	private final static ResourceBundle RESOURCE_BUNDLE_MESSAGE;

	static{
		RESOURCE_BUNDLE_BUSINES = ResourceBundle.getBundle(BUNDLE_NAME_BUSINES);
		RESOURCE_BUNDLE_MESSAGE = ResourceBundle.getBundle(BUNDLE_NAME_MESSAGE);
	}

	public static void clearCache() {
		ResourceBundle.clearCache();
		clearResourceCache();
	}

	private static String getString(ResourceBundle resourceBundle, String key) {
		if(resourceBundle == null) {
			return Variable.UNKNOWN;
		}
		try {
			return StringUtils.nullToStrTrim(resourceBundle.getString(StringUtils.nullToUnknown(key)));
		} catch (MissingResourceException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			return Variable.UNKNOWN;
		}
	}
	/**
	 * 业务属性键
	 */
	public static String getBusines(String key) {
		return getString(RESOURCE_BUNDLE_BUSINES, key);
	}
	/**
	 * 信息属性键
	 */
	public static String getMessage(String key) {
		return getString(RESOURCE_BUNDLE_MESSAGE,key);
	}

	private static void clearResourceCache() { //reloadBundles
        try {
            clearMap(ResourceBundle.class, null, "cacheList");
            // now, for the true and utter hack, if we're running in tomcat,
            // clear
            // it's class loader resource cache as well.
            clearTomcatCache();
        } catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static void clearMap(Class cl, Object obj, String name) 
    		throws SecurityException, NoSuchFieldException, 
    				IllegalArgumentException, IllegalAccessException, 
    				NoSuchMethodException, InvocationTargetException {
        Field field = cl.getDeclaredField(name);
        field.setAccessible(true);
        Object cache = field.get(obj);
        Class ccl = cache.getClass();
        Method sizeMethod = ccl.getMethod("size", (Class[])null);
		LogUtils.logInfo(name + ": size before clear: " + sizeMethod.invoke(cache, (Object[])null));
        Method clearMethod = ccl.getMethod("clear", (Class[])null);
        clearMethod.invoke(cache, (Object[])null);
		LogUtils.logInfo(name + ": size after clear: " + sizeMethod.invoke(cache,(Object[]) null));
    }

    @SuppressWarnings("rawtypes")
	private static void clearTomcatCache() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class cl = loader.getClass();
        try {
            if ("org.apache.catalina.loader.WebappClassLoader".equals(cl.getName())) {
                clearMap(cl, loader, "resourceEntries");
            } else {
    			LogUtils.logInfo("class loader " + cl.getName()+ " is not tomcat loader.");
            }
        } catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
        }
    }

}