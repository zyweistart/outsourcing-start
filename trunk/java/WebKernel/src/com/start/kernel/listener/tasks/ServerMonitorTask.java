package com.start.kernel.listener.tasks;

import java.util.Map;
import java.util.TimerTask;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.start.application.common.utils.EmailUtil;
import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.config.Variable;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.StringUtils;

/**
 * 服务器监控
 * @author Start
 */
public class ServerMonitorTask extends TimerTask {

	private static boolean flag = false;

	@Override
	public void run() {
		if(flag) {
			return;
		}
		flag = true;
		LogUtils.logInfo(this.getClass().getSimpleName());
		new ServerMonitorThread().start();
	}

	private class ServerMonitorThread extends Thread {
		
		public void run() {
			try {
				LogUtils.printLogInfo(ConfigParameter.SYSTEMINFO_FULL);
				
				OperatingSystem sys = OperatingSystem.getInstance();
				LogUtils.printLogInfo("操作系统：" + sys.getDescription() + " " + sys.getPatchLevel() + "(" + sys.getName() + " " + sys.getArch() + ")");
				Map<String, String> map = System.getenv();
				LogUtils.printLogInfo("计算机：" + map.get("COMPUTERNAME") + "\\" + map.get("USERNAME"));
				
				Sigar sigar = new Sigar();
		        NetInterfaceConfig config = sigar.getNetInterfaceConfig();
				LogUtils.printLogInfo("IP地址：" + config.getAddress());
				LogUtils.printLogInfo("MAC地址：" + config.getHwaddr());
				
				StringBuffer stringBuffer = new StringBuffer();
				CpuPerc cpuCerc = sigar.getCpuPerc();
				int cpuUsed = (int)(cpuCerc.getCombined() * 100);
				LogUtils.printLogInfo("CPU已使用: " + cpuUsed + "%");
				if(cpuUsed >= ConfigParameter.CPUALARMPERC) {
					stringBuffer.append(";CPU已使用" + cpuUsed + "%");
				}
				
				Mem mem = sigar.getMem();
				long memFree = mem.getFree();
				LogUtils.printLogInfo("内存:总量(" + StringUtils.convertSize(mem.getTotal()) + ") 已用(" + StringUtils.convertSize(mem.getUsed()) + ") 可用(" + StringUtils.convertSize(memFree) + ")");
				if(memFree <= ConfigParameter.MEMALARMSIZE) {
					stringBuffer.append(";可用内存" + StringUtils.convertSize(memFree));
				}
				
				FileSystem[] fileSystems = sigar.getFileSystemList();
				FileSystemUsage diskUsage = null;
				for (FileSystem fileSystem : fileSystems) {
					if (fileSystem.getType() == 2) {
						diskUsage = sigar.getFileSystemUsage(fileSystem.getDevName());
						LogUtils.printLogInfo("磁盘" + fileSystem.getDevName() + " " + fileSystem.getSysTypeName() 
								+ " 容量(" + StringUtils.convertKBSize(diskUsage.getTotal()) + ") 已用(" + StringUtils.convertKBSize(diskUsage.getUsed()) + ") 可用(" + StringUtils.convertKBSize(diskUsage.getFree()) + ")");
					}
				}
				
				String[] monitorDisks = ConfigParameter.MONITORDISK.split(";");
				long diskFree = 0L;
				for(String disk : monitorDisks) {
					diskUsage = sigar.getFileSystemUsage(disk + ":");
					diskFree = diskUsage.getFree();
					if(diskFree * Variable.Byte_Hex <= ConfigParameter.DISKALARMSIZE) {
						stringBuffer.append(";磁盘" + disk + "可用" + StringUtils.convertKBSize(diskFree));
					}
				}
				
				if(stringBuffer.length() > 1) {
					//发送邮件提醒系统管理员
					EmailUtil.sendAlarmEmail("负载服务器:"+ConfigParameter.BALANCED_WORKER_INDEX ,stringBuffer.substring(1));
				}
			} catch (SigarException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			}finally{
				flag = false;
			}
		}
		
	}
	
}
