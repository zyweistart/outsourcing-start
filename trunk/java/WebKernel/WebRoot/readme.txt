////////////////////////////////////////////////////////////////////////
jstl-1.2.jar
standard.jar
log4j-1.2.16.jar
commons-logging-1.1.1.jar
ojdbc14_g.jar
mysql-jdbc.jar
mybatis-3.1.1.jar(<requirement:cglib,asm-all,javassist,log4j,commons-logging,slf4j-api,slf4j-log4j12,ognl-3.0.6>)
c3p0-0.9.1.2.jar
commons-dbcp-1.4.jar(<requirement:commons-pool,commons-collections>)
commons-pool-1.5.6.jar
commons-collections-3.2.1.jar
////////////////////////////////////////////////////////////////////////
jedis-2.1.0.jar(redis内存数据库)
aliyun-openservices.jar(<requirement:HTTP协议编程,jdom>)
mail1.4.4.jar
jdom-2.0.2.jar(XML解析)
poi-3.0.1-FINAL-20070705.jar(Excel文件操作)
ant.jar(使用了ZIP压缩操作类)
commons-net-3.1.jar(使用了FTP操作类)
commons-codec-1.6.jar(处理常用的编码方法的工具类包，例如DES、SHA1、MD5、Base64等等)
HTTP协议编程(<requirement:commons-codec,commons-logging>)
	httpclient-4.1.3.jar
	httpclient-cache-4.1.3.jar
	httpcore-4.1.4.jar
	httpmime-4.1.3.jar
Windows环境检测
	sigar.jar
	sigar-amd64-winnt.dll
	sigar-x86-winnt.dll
	sigar-x86-winnt.lib
<:可选可删除>
	cglib-2.2.jar(代码生成包)
	asm-all-3.3.1.jar
	javassist-3.16.1-GA.jar(分析、编辑和创建Java字节码的类库)
	slf4j-api-1.6.2.jar
	slf4j-log4j12-1.6.2.jar
	ognl-3.0.6.jar
javapns(iphone消息推送)<requirement:log4j>
	JavaPNS
	bcprov-jdk15-146
androindpn(androind消息推送)<requirement:log4j,commons-codec,commons-collections>
	commons-digester
	commons-beanutils
	commons-configuration
	commons-lang
	dom4j
	libidn
	mina-core
	mina-integration-beans
	starter
	tinder
	xpp3


androindpn_message format:
<stream:stream to="192.168.0.26" xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams" version="1.0">
<starttls xmlns="urn:ietf:params:xml:ns:xmpp-tls"/>
<iq id="Q1G6R-0" type="get">
	<query xmlns="jabber:iq:auth">
		<username>ccccdd1393ff4bf6a5ff7de634d17576</username>
	</query>
</iq>
<iq id="Q1G6R-1" type="set">
	<query xmlns="jabber:iq:auth">
		<username>ccccdd1393ff4bf6a5ff7de634d17576</username>
		<digest>e023cc2c49c7f6e7acb63b747cdc7121e4bf51b3</digest>
		<resource>AndroidpnClient</resource>
	</query>
</iq>
<iq id="Q1G6R-2" type="get">
	<query xmlns="jabber:iq:roster" ></query>
</iq>
<presence id="Q1G6R-3">
</presence>