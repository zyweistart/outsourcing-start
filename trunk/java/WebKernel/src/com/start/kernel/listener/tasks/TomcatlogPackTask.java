package com.start.kernel.listener.tasks;

import java.io.File;
import java.util.TimerTask;

import com.start.framework.config.AppConstant;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.config.SystemPath;
import com.start.kernel.utils.FileUtils;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.TimeUtils;
import com.start.kernel.utils.ZIPUtils;
/**
 * Tomcat服务器日志备份
 * @author Start
 */
public class TomcatlogPackTask extends TimerTask {

	private static boolean flag = false;

	@Override
	public void run() {
		if(flag) {
			return;
		}
		flag = true;
		LogUtils.logInfo(this.getClass().getSimpleName());
		new TomcatlogPackThread().start();
	}
	
	private class TomcatlogPackThread extends Thread {

		public void run() {
			try{
				String yesterday = TimeUtils.getTheDayBefore();
				String regex = "[0-9A-Za-z_-]*." + TimeUtils.getTheDayBefore_F() + ".[0-9A-Za-z]*";
				File[] filelist = FileUtils.getFilesByFilterRegex(SystemPath.TOMCATLOG_PATH, regex);
				if(filelist != null) {
					for(File subfile : filelist) {
						if(subfile.isFile()) {
							if(FileUtils.renameTo(subfile, SystemPath.TOMCATLOGBACKUP_PATH+ yesterday + AppConstant.FILESEPARATOR + subfile.getName())) {
								String inputFilePath = SystemPath.TOMCATLOGBACKUP_PATH+ yesterday + AppConstant.FILESEPARATOR;
								//backup/tomcatlog/2012/04/20120425(1).zip
								String zipFileName = SystemPath.TOMCATLOGBACKUP_PATH + yesterday.substring(0, 4) + AppConstant.FILESEPARATOR + 
										yesterday.substring(4, 6) + AppConstant.FILESEPARATOR + yesterday + "(" +
										ConfigParameter.BALANCED_WORKER_INDEX + ")" +ZIPUtils.ZIP;
								if(FileUtils.isFileExists(zipFileName)) {
									if(ZIPUtils.unZip(zipFileName, SystemPath.TOMCATLOGBACKUP_PATH)) {
										FileUtils.deleteFile(zipFileName);
									}
								}
								if(ZIPUtils.zip(inputFilePath, zipFileName, ConfigParameter.SYSTEMNAME + "TOMCAT(" + ConfigParameter.BALANCED_WORKER_INDEX + ")日志备份" + yesterday)) {
									FileUtils.deleteDir(inputFilePath);
								}
							}
						}
					}
				}
			}finally{
				flag = false;
			}
		}

	}

}