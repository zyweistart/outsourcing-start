package com.start.kernel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.Variable;

public class ZIPUtils {

	public final static String ZIP=".zip";
	
	private static final String SEPARATOR = "/";
	private static final String EMPTY = "";

	public static boolean zip(String inputFilePath, String zipFileName) {
		return zip(inputFilePath, zipFileName, null);
	}

	public static boolean zip(String inputFilePath, String zipFileName, String comment) {
		File inputFile = new File(inputFilePath);
		if(!inputFile.exists()) {
			return false;
		}
		File zipFile = new File(zipFileName);
		if (!zipFile.getParentFile().exists()) {
			if(!zipFile.getParentFile().mkdirs()) {
				return false;
			}
		}
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
			if(StringUtils.isNotEmpty(comment)) {
				out.setComment(comment);
			}
			zip(inputFile, out, inputFile.getName());
			return true;
		} catch (IOException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			return false;
		} finally {
			if(out!=null){
				try {
					out.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return false;
				}finally{
					try {
						out.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
						return false;
					}finally{
						out=null;
					}
				}
			}
		}
	}

	private static void zip(File file, ZipOutputStream out, String base)throws IOException {
		ZipEntry zipEntry = null;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			zipEntry = new ZipEntry(base + SEPARATOR);
			zipEntry.setTime(file.lastModified());
			out.putNextEntry(zipEntry);
			base = StringUtils.isEmpty(base) ? EMPTY : base + SEPARATOR;
			for (int i = 0; i < files.length; i++) {
				zip(files[i], out, base + files[i].getName());
			}
		} else if (file.isFile()) {
			zipEntry = new ZipEntry(base);
			zipEntry.setTime(file.lastModified());
			out.putNextEntry(zipEntry);
			FileInputStream in = null;
			int count = -1;
			byte[] data = new byte[Variable.BUFFER];
			try {
				in = new FileInputStream(file);
				while ((count = in.read(data)) != -1) {
					out.write(data, 0, count);
				}
			} catch (IOException e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
				throw new IOException(StackTraceInfo.getTraceInfo() + e.getMessage());
			} finally {
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					}finally{
						in=null;
					}
				}
			}
		}
	}

    @SuppressWarnings("rawtypes")
	public static boolean unZip(String zipFileName, String outputFilePath) {
		File file = new File(zipFileName);
		if (!file.exists()) {
			return false;
		}
		ZipFile zipFile = null;
		InputStream in = null;
		FileOutputStream out = null;
		try {
			zipFile = new ZipFile(file);
			Enumeration em = zipFile.getEntries();
			while (em.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) em.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					FileUtils.mkdirs(outputFilePath + name);
				} else {
					File f = new File(outputFilePath + zipEntry.getName());
					f.getParentFile().mkdirs();
					f.createNewFile();
					in = zipFile.getInputStream(zipEntry);
					out = new FileOutputStream(f);
					int len = -1;
					byte[] b = new byte[Variable.BUFFER];
					while ((len = in.read(b)) != -1) {
						out.write(b, 0, len);
					}
					out.close();
					in.close();
				}
			}
			return true;
		} catch (IOException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			return false;
		} finally {
			if(out!=null){
				try {
					out.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return false;
				}finally{
					try {
						out.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
						return false;
					}finally{
						out=null;
					}
				}
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return false;
				}finally{
					in=null;
				}
			}
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return false;
				}
			}
		}
	}

}
