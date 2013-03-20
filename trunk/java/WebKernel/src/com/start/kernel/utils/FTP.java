package com.start.kernel.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.start.framework.utils.StackTraceInfo;

public class FTP {

	private FTPClient ftpClient;
	private String hostname;
	private Integer port = 21;
	private String username;
	private String password;
	private Boolean isLogin = false;
	/**
	 * @param hostname
	 * 主机地址
	 * @param port
	 * 端口默认21
	 * @param username
	 * 登陆用户名
	 * @param password
	 * 密码
	 */
	public FTP(String hostname, int port, String username, String password)throws SocketException, IOException {
		this.ftpClient = new FTPClient();
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	/**
	 * @param hostname
	 * 主机地址,端口默认21
	 * @param username
	 * 登陆用户名
	 * @param password
	 * 密码
	 */
	public FTP(String hostname, String username, String password)throws SocketException, IOException {
		this(hostname,21, username, password);
	}
	/**
	 * 连接
	 */
	public void connect() throws SocketException, IOException {
		if(ftpClient != null && !ftpClient.isConnected()) {
			ftpClient.setControlEncoding("gbk");
//			ftpClient.connect(hostname, port);
			ftpClient.setConnectTimeout(30000);
			ftpClient.setDataTimeout(30000);
			ftpClient.setDefaultTimeout(30000);
			ftpClient.connect(hostname, port);
			ftpClient.setControlKeepAliveTimeout(300);
			ftpClient.setSoTimeout(30000);
		}
	}

	public void disconnect() {
		if(ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}finally{
				ftpClient = null;
			}
		}
	}
	/**
	 * 登陆FTP
	 */
	public void login() throws IOException {
		if(ftpClient != null && ftpClient.isConnected() && !isLogin) {
			isLogin = ftpClient.login(username, password);
		}
	}
	/**
	 * 退出FTP
	 */
	public void logout() {
		if(ftpClient != null && ftpClient.isConnected() && isLogin) {
			try {
				ftpClient.logout();
			} catch (IOException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}
	}

	public void makeDirectory(String pathlist) {
		StringTokenizer s = new StringTokenizer(pathlist, "/");
		String pathname = "";
		while (s.hasMoreElements()) {
			pathname = pathname + "/" + (String) s.nextElement();
			try {
				ftpClient.makeDirectory(pathname);
			} catch (IOException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			}
		}
	}

	public boolean download(String remoteFileName, String localFileName) {
		boolean flag = false;
		BufferedOutputStream out = null;
		try {
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			// ftpClient.changeWorkingDirectory(remoteDir);
			ftpClient.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
			ftpClient.setBufferSize(8196); // 1kb 1024 *8 8KB
			File localFile = new File(localFileName);
			if (!localFile.getParentFile().exists()) {
				if(!localFile.getParentFile().mkdirs()) {
					return false;
				}
			}
			out = new BufferedOutputStream(new FileOutputStream(localFileName));
			if (out != null) {
				ftpClient.changeWorkingDirectory("/"); // 回到FTP根目录
				flag = ftpClient.retrieveFile(remoteFileName, out);
			}
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
				}finally{
					try {
						out.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					}finally{
						out = null;
					}
				}
			}
		}
		return flag;
	}

	public FTPFile[] listFiles(String pathname) {
		ftpClient.enterLocalPassiveMode();
		try {
			ftpClient.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
			return ftpClient.listFiles(pathname);
		} catch (IOException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			return null;
		}
	}

	public boolean rename(String from, String to) {
		try {
			return ftpClient.rename(from, to);
		} catch (IOException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			return false;
		}
	}

	public boolean move(String from, String to) {
		return rename(from, to);
	}

	public boolean move(String from, String to, String path) {
		makeDirectory(path);
		return rename(from, to);
	}

}
