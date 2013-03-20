package com.start.kernel.listener;

import java.util.Properties;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.start.kernel.config.Business;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.config.Variable;
import com.start.kernel.listener.tasks.DataBaseBackupTask;
import com.start.kernel.listener.tasks.LogPackTask;
import com.start.kernel.listener.tasks.ProgramBackupTask;
import com.start.kernel.listener.tasks.ServerMonitorTask;
import com.start.kernel.listener.tasks.TomcatlogPackTask;

public class ServletContext implements ServletContextListener {

	/**
	 * 服务器容器路径 例：/Users/start/Documents/Developer/eclipse/WebKernel/WebRoot/
	 */
	public static String SERVLETCONTEXTREALPATH;

	private TimerTasks task = new TimerTasks();

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		SERVLETCONTEXTREALPATH = servletContextEvent.getServletContext()
				.getRealPath("/");
		if (ConfigParameter.INITSTATUS) {
			// 在此加入系统启动需要初始化的代码
		}
		task.run();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (task != null) {
			try{
				task.destroy();
			}finally{
				task = null;
			}
		}
	}

	private class TimerTasks {

		private static final String LINUX="LINUX";
		private static final String WINDOWS="Windows";
		
		private Timer timer = new Timer();

		public void run() {
			if(ConfigParameter.SYSTEMSTATUS){
				//获得系统属性集    
				Properties props=System.getProperties(); 
				//操作系统名称
				String osName = props.getProperty("os.name"); 
				if(LINUX.equals(osName)){
					//LINUX环境
				}else if(WINDOWS.equals(osName)){
					//Windows环境
					timer.schedule(new ServerMonitorTask(), Variable.ONESECOND, Variable.HOUR_MILLISECOND);
				}
				if(ConfigParameter.SYSTEMFLAG){
					//只在正式环境中执行备份任务
					if(ConfigParameter.BALANCED_WORKER_INDEX.equals(Business.BACKUP_BALANCED_WORKER_INDEX)){
						timer.schedule(new DataBaseBackupTask(),Variable.TOMORROWHOURTIME,Variable.DAY_MILLISECOND);
						timer.schedule(new LogPackTask(),Variable.TOMORROWHOURTIME,Variable.DAY_MILLISECOND);
						timer.schedule(new TomcatlogPackTask(),Variable.TOMORROWHOURTIME,Variable.DAY_MILLISECOND);
						timer.schedule(new ProgramBackupTask(),Variable.TOMORROWHOURTIME,Variable.DAY_MILLISECOND);
					}
				}
			}
		}

		public void destroy() {
			if(timer != null) {
				try{
					timer.cancel();
				}finally{
					timer = null;
				}
			}
		}

	}
	
}