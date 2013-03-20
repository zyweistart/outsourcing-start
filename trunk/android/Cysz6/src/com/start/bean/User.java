package com.start.bean;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.start.common.AppException;
import com.start.common.Constant;
import com.start.utils.StringUtils;

public class User extends Base {

	private static final long serialVersionUID = 1L;

	private int uid;

	private String name;

	private String face;

	private String account;

	private String pwd;

	private boolean isRememberMe;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public boolean isRememberMe() {
		return isRememberMe;
	}

	public void setRememberMe(boolean isRememberMe) {
		this.isRememberMe = isRememberMe;
	}

	public static User parse(InputStream stream) throws IOException,AppException {
		User user = new User();
		Result res = null;
		// 获得XmlPullParser解析器
		XmlPullParser xmlParser = Xml.newPullParser();
		try {
			xmlParser.setInput(stream, Constant.ENCODE);
			// 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
			int evtType = xmlParser.getEventType();
			// 一直循环，直到文档结束
			while (evtType != XmlPullParser.END_DOCUMENT) {
				String tag = xmlParser.getName();
				switch (evtType) {
					case XmlPullParser.START_TAG:
						// 如果是标签开始，则说明需要实例化对象了
						if (tag.equalsIgnoreCase("result")) {
							res = new Result();
						} else if (tag.equalsIgnoreCase("errorCode")) {
							res.setErrorCode(StringUtils.toInt(xmlParser.nextText(), -1));
						} else if (tag.equalsIgnoreCase("errorMessage")) {
							res.setErrorMessage(xmlParser.nextText().trim());
						} else if (res != null && res.OK()) {
							if (tag.equalsIgnoreCase("uid")) {
								user.uid = StringUtils.toInt(xmlParser.nextText(),0);
							} else if (tag.equalsIgnoreCase("name")) {
								user.setName(xmlParser.nextText());
							} else if (tag.equalsIgnoreCase("portrait")) {
								user.setFace(xmlParser.nextText());
							}else if (tag.equalsIgnoreCase("notice")) {
								// 通知信息
								user.setNotice(new Notice());
							} else if (user.getNotice() != null) {
								if (tag.equalsIgnoreCase("msgCount")) {
									user.getNotice().setMsgCount(StringUtils.toInt(xmlParser.nextText(),0));
								} 
							}
						}
						break;
					case XmlPullParser.END_TAG:
						// 如果遇到标签结束，则把对象添加进集合中
						if (tag.equalsIgnoreCase("result") && res != null) {
							user.setResult(res);
						}
						break;
				}
				// 如果xml没有结束，则导航到下一个节点
				evtType = xmlParser.next();
			}
		} catch (XmlPullParserException e) {
			throw AppException.xml(e);
		} finally {
			stream.close();
		}
		return user;
	}

}
