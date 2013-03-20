package com.start.test.jta;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

public class XADemo {
	
    public static XADataSource getXADataSource() {
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setUser("root");
        dataSource.setPassword("******");
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
    public static void main(String[] args) {
        XADataSource dataSource = XADemo.getXADataSource();
        XAConnection xaConn = XADemo.getXAConnetion(dataSource);
        xaConn.addConnectionEventListener(new ConnectionEventListener() {
            public void connectionClosed(ConnectionEvent event) {
                System.out.println("连接被关闭");
            }
            public void connectionErrorOccurred(ConnectionEvent event) {
                System.out.println("连接发生错误");
            }
        });
        Connection conn = XADemo.getConnection(xaConn);
        try {
            Statement statement=conn.createStatement();
            statement.executeUpdate("insert into customer(name) values('corey')");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {   
            //关闭连接，但物理连接没有关闭，
            conn.close();
            //再次获得连接；
            conn=xaConn.getConnection();
            Statement statement2=conn.createStatement();
            statement2.executeUpdate("insert into customer(name) values('syna')");
        } catch (SQLException e) {
            System.out.println("关闭连接失败");
        }
    }
}