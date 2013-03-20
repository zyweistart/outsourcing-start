package com.start.test.mobile;

import java.net.InetSocketAddress;

import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.codec.XmppCodecFactory;
import org.androidpn.server.xmpp.net.XmppIoHandler;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.androidpn.service.ServiceLocator;
import org.androidpn.service.entity.ApnUser;
import org.androidpn.service.service.ApnUserService;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.start.framework.context.Init;

public class AndroidpnTest {

	private static final int SERVER_PORT = 5222;
	
	public static void main(String[] args) throws Exception {
		Init.loadConfigFile();
		Init.loadContextClass();
		NotificationManager notificationManager=new NotificationManager();
		
		// 创建一个非阻塞的Server端Socket，用NIO
		SocketAcceptor acceptor = new NioSocketAcceptor();
		// 定义每次接收数据大小
		SocketSessionConfig sessionConfig = acceptor.getSessionConfig();
		sessionConfig.setReadBufferSize(2048);
		// 创建接受数据的过滤器
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("executor", new ExecutorFilter());
		chain.addLast("codec", new ProtocolCodecFilter(new XmppCodecFactory()));
		// 设定服务器端的消息处理器: 一个 SimpleMinaServerHandler 对象
		acceptor.setHandler(new XmppIoHandler());
		// 绑定端口，启动服务器
		acceptor.setDefaultLocalAddress(new InetSocketAddress(SERVER_PORT));
		acceptor.setReuseAddress(true);
		acceptor.bind();
		System.out.println("Mina server is listing port:" + SERVER_PORT+",等待建立连接...");
		ApnUserService service=ServiceLocator.getInstance().getApnUserService();
		int i=0;
		while(true){
			try{
				ApnUser user=service.getUserById("30");
				if(user==null){
					Thread.sleep(3000);
				}else{
					Thread.sleep(3000);
					System.out.println("发送推送消息.....");
					notificationManager.sendBroadcast(Config.getString("apiKey", ""), "这是标题哦","消息内容	", "http://www.qq.com");
					if(i++>50000){
						break;
					}
				}
			}finally{
				service.clearSessionCache();
			}
		}
		System.out.println("服务关闭");
		service.closeSession();
		acceptor.unbind();
	}

}
