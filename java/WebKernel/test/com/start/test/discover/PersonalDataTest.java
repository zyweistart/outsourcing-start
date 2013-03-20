package com.start.test.discover;

import java.io.File;

import com.start.kernel.utils.MD5;
import com.start.test.BaseCls;

public class PersonalDataTest extends BaseCls {
	/**
	 * 获取个人信息
	 */
	public void testGet() throws Exception{
		url="personalData/get.do";
		sendRequest();
	}
	
	public void testModify() throws Exception{
		url="personalData/modify.do";
		params.put("name", "小张");
		params.put("birthday", "1989-06-24");
		params.put("statement", "这家伙很懒什么也没留下");
		params.put("profession", "软件开发");
		params.put("company", "安存科技");
		params.put("school", "浙江大学");
		params.put("location", "杭州");
		params.put("homepage", "http://127.0.0.1/zyweistart");
		sendRequest();
	}
	
	public void testGetfriend() throws Exception{
		url="personalData/getfriend.do";
		params.put("usercode", "868a0ff0527ebd788c061d26777b024b");
		accessKey=null;
		sendRequest();
	}
	/**
	 * 上传头像
	 */
	public void testUportrait() throws Exception{
		url="personalData/uportrait.do";
		String filepath="d:/test.png";
		File file=new File(filepath);
		header.put("fileName", file.getName());
		header.put("originalSize",file.length()+"");
		header.put("originalMD5", MD5.md5file(filepath));
		header.put("transportMode", "1");
		header.put("transportSize",file.length()+"");
		header.put("transportMD5",MD5.md5file(filepath));
		sendFileUpload(file);
	}
	/**
	 * 下载头像
	 */
	public void testGportrait() throws Exception{
		url="personalData/gportrait.do";
		String filepath="d:/testdownload.png";
		params.put("code", "beac88917a4c2791b9b04a425f0acefa");
		accessKey=null;
		fileDownload(new File(filepath));
	}
}
