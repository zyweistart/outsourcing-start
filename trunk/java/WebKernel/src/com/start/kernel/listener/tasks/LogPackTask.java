package com.start.kernel.listener.tasks;

import java.io.File;
import java.util.TimerTask;

import com.start.framework.config.AppConstant;
import com.start.kernel.config.SystemPath;
import com.start.kernel.utils.FileUtils;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.TimeUtils;
import com.start.kernel.utils.ZIPUtils;

/**
 * 程序日志备份
 * @author Start
 */
public class LogPackTask extends TimerTask {

	private static boolean flag = false;

	@Override
	public void run() {
		if(flag) {
			return;
		}
		flag = true;
		LogUtils.logInfo(this.getClass().getSimpleName());
		new LogPackThread().start();
	}

	private class LogPackThread extends Thread {
		
		public void run() {
			try{
				String yesterday = TimeUtils.getTheDayBefore();
				String inputFilePath =SystemPath.LOG_PATH + yesterday + AppConstant.FILESEPARATOR;
				String zipFileName =SystemPath.LOGBACKUP_PATH  + yesterday.substring(0, 4) + AppConstant.FILESEPARATOR + 
						yesterday.substring(4, 6) + AppConstant.FILESEPARATOR + yesterday +ZIPUtils.ZIP;
				if(ZIPUtils.zip(inputFilePath, zipFileName)){
					FileUtils.remove(new File(inputFilePath));
				}
			}finally{
				flag = false;
			}
		}

	}

}
