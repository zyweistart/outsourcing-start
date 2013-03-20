package com.start.kernel.listener.tasks;

import java.util.TimerTask;

import com.start.application.common.utils.EmailUtil;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.config.SystemPath;
import com.start.kernel.listener.ServletContext;
import com.start.kernel.utils.FileUtils;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.TimeUtils;
import com.start.kernel.utils.ZIPUtils;

/**
 * 程序备份
 * @author Start
 */
public class ProgramBackupTask extends TimerTask {

	private final static String FILENAME = "WebCoreProgram";
	private final static String COMMENT = ConfigParameter.SYSTEMINFO + "程序备份";
	private final static int maxtrytimes = 10;

	private static boolean flag = false;

	@Override
	public void run() {
		if(flag) {
			return;
		}
		flag = true;
		LogUtils.logInfo(this.getClass().getSimpleName());
		new ProgramBackupThread().start();
	}

	private class ProgramBackupThread extends Thread {

		public void run() {
			int trytimes = 1;
			while(true) {
				String date = TimeUtils.getSysdateInt();
				//备份文件的完整路径
				String backupPath = SystemPath.PROGRAMBACKUP_PATH + FILENAME + date + ZIPUtils.ZIP;
				//备注信息
				String comment = COMMENT + date;
				//删除备份文件
				if(FileUtils.deleteFile(backupPath)){
					if(ZIPUtils.zip(ServletContext.SERVLETCONTEXTREALPATH, backupPath, comment)) {
						LogUtils.log(flag, comment +"-->Success");
						flag = false;
						return;
					} else {
						LogUtils.log(flag, comment +"-->Fail");
						if(trytimes >= maxtrytimes) {
							//发送邮件请示管理员
							EmailUtil.sendAlarmEmail("系统出现异常", "系统出现异常");
							flag = false;
							return;
						} else {
							trytimes ++;
							TimeUtils.sleep(10000);
						}
					}
				}
			}
		}
		
	}

}
