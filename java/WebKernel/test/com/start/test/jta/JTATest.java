package com.start.test.jta;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.start.framework.jta.MyXid;

public class JTATest {

	public static void main(String[] args) {
		XADataSource xaDs1 = getDataSource("jdbc:mysql://localhost:3306/jstartdb?useUnicode=true&characterEncoding=utf-8","root", "hightern");
		XAConnection xaCon1 = null;
		XAResource xaRes1 = null;
		Connection conn1 = null;
		Statement stmt1 = null;

		XADataSource xaDs2 = getDataSource("jdbc:mysql://localhost:3306/discoverbackups?useUnicode=true&characterEncoding=utf-8","root", "hightern");
		XAConnection xaCon2 = null;
		XAResource xaRes2 = null;
		Connection conn2 = null;
		Statement stmt2 = null;

		int ret1 = 0;
		int ret2 = 0;

		Xid xid1 = new MyXid(100, new byte[] { 0x01 }, new byte[] { 0x02 });
		Xid xid2 = new MyXid(100, new byte[] { 0x01 }, new byte[] { 0x03 });
		try {
			xaCon1 = JTATest.getXAConnetion(xaDs1);
			xaRes1 = xaCon1.getXAResource();
			conn1 = JTATest.getConnection(xaCon1);
			stmt1 = conn1.createStatement();

			xaCon2 = JTATest.getXAConnetion(xaDs2);
			xaRes2 = xaCon2.getXAResource();
			conn2 = JTATest.getConnection(xaCon2);
			stmt2 = conn2.createStatement();

			xaRes1.start(xid1, XAResource.TMNOFLAGS);
			stmt1.execute("INSERT INTO mx_user (uid,name,add_time) VALUES (148,'JSTARTDB','2712-09-22')");
			xaRes1.end(xid1, XAResource.TMSUCCESS);

			if (xaRes2.isSameRM(xaRes1)) {
				xaRes2.start(xid1, XAResource.TMNOFLAGS);
				stmt2.execute("INSERT INTO mx_user (uid,name,add_time) VALUES (148,'BACKUPS','2712-09-22')");
				xaRes2.end(xid1, XAResource.TMSUCCESS);
			} else {
				xaRes2.start(xid2, XAResource.TMNOFLAGS);
				stmt2.execute("INSERT INTO mx_user (uid,name,add_time) VALUES ('148','BACKUPS','2712-09-22')");
				xaRes2.end(xid2, XAResource.TMSUCCESS);
				ret1 = xaRes2.prepare(xid2);
			}
			ret2 = xaRes1.prepare(xid1);
			if (ret1 == XAResource.XA_OK && ret2 == XAResource.XA_OK) {
				xaRes1.commit(xid1, false);
				// xaRes1.rollback(xid1);
				if (xaRes2.isSameRM(xaRes1)) {
					// xaRes2.rollback(xid1);
					xaRes2.commit(xid1, false);
				} else {
					// xaRes2.rollback(xid2);
					xaRes2.commit(xid2, false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (XAException e) {
			e.printStackTrace();
		}
	}

	private static XADataSource getDataSource(String url, String user,
			String password) {
		MysqlXADataSource dataSource = new MysqlXADataSource();
		dataSource.setUrl(url);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		return dataSource;
	}

	public static XAConnection getXAConnetion(XADataSource dataSource) {
		XAConnection XAConn = null;
		try {
			XAConn = dataSource.getXAConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return XAConn;
	}

	public static Connection getConnection(XAConnection XAConn) {
		Connection conn = null;
		try {
			conn = XAConn.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("连接关闭失败");
		}
	}

}