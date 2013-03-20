package com.start.test.mobile;

import java.util.ArrayList;
import java.util.List;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.transmission.NotificationProgressListener;
import javapns.notification.transmission.NotificationThread;
import javapns.notification.transmission.NotificationThreads;

public class JavaPNSTest {

	/**
	 * 1.将aps_developer_identity.cer转换成pem openssl x509 -in
	 * aps_developer_identity.cer -inform der -out PushChatCert.pem 2.将Apple
	 * Development Push Services证书转换成pem openssl pkcs12 -nocerts -out
	 * PushChatKey.pem -in Push.p12 3.合成两个pem证书(Java服务器所需的证书为p12格式) openssl
	 * pkcs12 -export -in PushChatCert.pem -inkey PushChatKey.pem -out
	 * pushCert.p12 -name “apns-cert”
	 */
	// 证书路径和证书名
	private static String keystore =JavaPNSTest.class.getResource("/data/iosPushCert.p12").getPath(); 
	// 证书密码
	private static String password = "Start19890624";

	public static void main(String[] args) {
		String token = "592e7f0b29bf1b4931241881e99890327d7b19f9c11c920fb8666e592363a575";// 手机唯一标识
		// 设置true为正式服务地址，false为开发者地址
		boolean production = false; 
		// 线程数
		int threadThreads = 10; 
		try {
			// 建立与Apple服务器连接
			AppleNotificationServer server = new AppleNotificationServerBasicImpl(
					keystore, password, production);
			List<PayloadPerDevice> list = new ArrayList<PayloadPerDevice>();
			PushNotificationPayload payload = new PushNotificationPayload();
			payload.addAlert("我日我日");
			payload.addSound("default");// 声音
			payload.addBadge(1);// 图标小红圈的数值
			payload.addCustomDictionary("url", "www.baidu.com");// 添加字典
			PayloadPerDevice pay = new PayloadPerDevice(payload, token);// 将要推送的消息和手机唯一标识绑定
			list.add(pay);
			NotificationThreads work = new NotificationThreads(server, list,
					threadThreads);//
			// 对线程的监听，一定要加上这个监听
			work.setListener(DEBUGGING_PROGRESS_LISTENER);
			work.start(); // 启动线程
			work.waitForAllThreads();// 等待所有线程启动完成
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		JavaPNSTest send = new JavaPNSTest();
//		List<String> tokens = new ArrayList<String>();
//		tokens.add(token);
//		tokens.add(token);
//		tokens.add(token);
//		String message = "{'aps':{'alert':'iphone推送测试 www.baidu.com'}}";
//		Integer count = 1;
//		boolean sendCount = false;
//		send.sendpush(tokens, keystore, password, message, count, sendCount);
	}

	// 线程监听
	public static final NotificationProgressListener DEBUGGING_PROGRESS_LISTENER = new NotificationProgressListener() {

		public void eventThreadStarted(NotificationThread notificationThread) {
			System.out.println("   [EVENT]: thread #"
					+ notificationThread.getThreadNumber() + " started with "
					+ " devices beginning at message id #"
					+ notificationThread.getFirstMessageIdentifier());
		}

		public void eventThreadFinished(NotificationThread thread) {
			System.out.println("   [EVENT]: thread #"
					+ thread.getThreadNumber() + " finished: pushed messages #"
					+ thread.getFirstMessageIdentifier() + " to "
					+ thread.getLastMessageIdentifier() + " toward "
					+ " devices");
		}

		public void eventConnectionRestarted(NotificationThread thread) {
			System.out.println("   [EVENT]: connection restarted in thread #"
					+ thread.getThreadNumber() + " because it reached "
					+ thread.getMaxNotificationsPerConnection()
					+ " notifications per connection");
		}

		public void eventAllThreadsStarted(
				NotificationThreads notificationThreads) {
			System.out.println("   [EVENT]: all threads started: "
					+ notificationThreads.getThreads().size());
		}

		public void eventAllThreadsFinished(
				NotificationThreads notificationThreads) {
			System.out.println("   [EVENT]: all threads finished: "
					+ notificationThreads.getThreads().size());
		}

		public void eventCriticalException(
				NotificationThread notificationThread, Exception exception) {
			System.out.println("   [EVENT]: critical exception occurred: "
					+ exception);
		}
	};

	/**
	 * 这是一个比较简单的推送方法，
	 * apple的推送方法
	 * @param tokens
	 *            iphone手机获取的token
	 * @param path
	 *            这里是一个.p12格式的文件路径，需要去apple官网申请一个
	 * @param password
	 *            p12的密码 此处注意导出的证书密码不能为空因为空密码会报错
	 * @param message
	 *            推送消息的内容
	 * @param count
	 *            应用图标上小红圈上的数值
	 * @param sendCount
	 *            单发还是群发 true：单发 false：群发
	 * 测试推送服务器地址：gateway.sandbox.push.apple.com /2195
	 * 产品推送服务器地址：gateway.push.apple.com / 2195      
	 */

	public void sendpush(List<String> tokens, String path, String password,
			String message, Integer count, boolean sendCount) {
		try {
			// message是一个json的字符串{“aps”:{“alert”:”iphone推送测试”}}
			PushNotificationPayload payLoad = PushNotificationPayload
					.fromJSON(message);
			payLoad.addAlert("iphone推送测试 www.baidu.com"); // 消息内容
			payLoad.addBadge(count); // iphone应用图标上小红圈上的数值
			payLoad.addSound("default"); // 铃音 默认
			PushNotificationManager pushManager = new PushNotificationManager();
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager
					.initializeConnection(new AppleNotificationServerBasicImpl(
							path, password, false));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			if (sendCount) {
				System.out.println("--------------------------apple 推送 单-------");
				Device device = new BasicDevice();
				device.setToken(tokens.get(0));
				PushedNotification notification = pushManager.sendNotification(
						device, payLoad, true);
				notifications.add(notification);
			} else {
				System.out.println("--------------------------apple 推送 群-------");
				List<Device> device = new ArrayList<Device>();
				for (String token : tokens) {
					device.add(new BasicDevice(token));
				}
				notifications = pushManager.sendNotifications(payLoad, device);
			}
			List<PushedNotification> failedNotifications = PushedNotification
					.findFailedNotifications(notifications);
			List<PushedNotification> successfulNotifications = PushedNotification
					.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			int successful = successfulNotifications.size();
			if (successful > 0 && failed == 0) {
				System.out.println("-----All notifications pushed 成功 ("
						+ successfulNotifications.size() + "):");
			} else if (successful == 0 && failed > 0) {
				System.out.println("-----All notifications 失败 ("
						+ failedNotifications.size() + "):");
			} else if (successful == 0 && failed == 0) {
				System.out
						.println("No notifications could be sent, probably because of a critical error");
			} else {
				System.out.println("------Some notifications 失败 ("
						+ failedNotifications.size() + "):");
				System.out.println("------Others 成功 (" + successfulNotifications.size()
						+ "):");
			}
			// pushManager.stopConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}