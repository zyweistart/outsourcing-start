package com.start.kernel.utils;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.start.framework.utils.StackTraceInfo;

public class SMTP {
	
	private final static String protocol = "smtp";
	private final static String connectiontimeout = "5000";
	private final static String timeout = "10000";
	private final static String type = "text/html";
	private final static String charset = "utf-8";
	
	private String host= "";
	private Integer port = 25;
	private String username= "";
	private String password= "";
	private Boolean auth = true;

	public SMTP(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}

	public SMTP(String host, boolean auth, String username, String password) {
		this.host = host;
		this.auth = auth;
		this.username = username;
		this.password = password;
	}

	public SMTP(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public SMTP(String host, int port, boolean auth, String username, String password) {
		this.host = host;
		this.port = port;
		this.host = host;
		this.username = username;
		this.password = password;
	}

	public void send(String from, String to, String subject, String content) {
		send(from, to, subject, content, false);
	}

	public void sendPriority(String from, String to, String subject, String content) {
		send(from, to, subject, content, true);
	}

	private void send(String from, String to, String subject, String content, boolean priority) {
		EamilsendThread eamilsendThread = new EamilsendThread(from, to, subject, content, priority);
		eamilsendThread.start();
	}

	private class EamilsendThread extends Thread {

		private String from = "";
		private String to = "";
		private String subject = "";
		private String content = "";
		private boolean priority = false;

		private EamilsendThread(String from, String to, String subject, String content, boolean priority) {
			this.from = from;
			this.to = to;
			this.subject = subject;
			this.content = content;
			this.priority = priority;
		}

		public void run() {
			send(from, to, null, null, subject, content, null, priority, false, false);
		}
	}

	public boolean send(String from, String to, String cc, String bcc,
			String subject, String content, List<String> filenames,
			boolean priority, boolean sensitivity, boolean notification)  {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", auth ? "true" : "false");
		properties.put("mail.smtp.connectiontimeout", connectiontimeout);
		properties.put("mail.smtp.timeout", timeout);
		Session session = Session.getDefaultInstance(properties, null);
		Transport transport = null;
		try {
			if (from == null || from.trim().length() == 0) {
				return false;
			}
			Message message = new MimeMessage(session);
			InternetAddress fromInternetAddress = new InternetAddress(from);
			message.setFrom(new InternetAddress(fromInternetAddress.getAddress(), fromInternetAddress.getPersonal(), charset));
			if (to != null && to.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			}
			if (cc != null && cc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
			}
			if (bcc != null && bcc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
			}
			if (subject != null && subject.length() > 0) {
				message.setSubject(MimeUtility.encodeText(subject, charset, "B"));
			}
			if (content == null) {
				content = "";
			}
			Multipart multipart = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(content, type + ";charset=" + charset);
			multipart.addBodyPart(bodyPart);
			if (filenames != null) {
				int size = filenames.size();
				for (int i = 0; i < size; i++) {
					if (filenames.get(i) != null && filenames.get(i).trim().length() > 0) {
						File file = new File(filenames.get(i));
						if (file.exists()) {
							bodyPart = new MimeBodyPart();
							FileDataSource fileDataSource = new FileDataSource(filenames.get(i));
							bodyPart.setDataHandler(new DataHandler(fileDataSource));
							bodyPart.setFileName(MimeUtility.encodeWord(fileDataSource.getName()));
							multipart.addBodyPart(bodyPart);
						}
					}
				}
			}
			if (priority) {
				message.setHeader("X-Priority", "1");
			}
			if (sensitivity) {
				message.setHeader("Sensitivity", "Company-Confidential");
			}
			if (notification) {
				message.setHeader("Disposition-Notification-To", from);
			}
			message.setContent(multipart);
			message.saveChanges();
			transport = session.getTransport(protocol);
			transport.connect(username, password);
			transport.sendMessage(message, message.getAllRecipients());
			return true;
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() 
					+ "email send fail " + "from:" + from + ";to:" + to + ";priority:" + priority + " " 
					+ StringUtils.nullToStrTrim(e.getMessage()));
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() 
							+ "email send fail " + "from:" + from + ";to:" + to + ";priority:" + priority + " " 
							+ StringUtils.nullToStrTrim(e.getMessage()));
				}
				transport = null;
			}
		}
		return false;
	}
	
}