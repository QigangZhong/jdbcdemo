package com.qigang.jdbc.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
	private static Properties props=null;
	
	private JDBCUtils() {

	}
	static{
		try {
			props=new Properties();
			//��ȡsrc��Ŀ¼�µ�config.properties���ã���ȡ���ݿ���������
			props.load(new FileReader(JDBCUtils.class.getClassLoader().getResource("config.properties").getPath()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConn() throws ClassNotFoundException, SQLException{
		
		//1. ע�����ݿ�����
		Class.forName(props.getProperty("driver"));
		
		//2. ��ȡ����
		return DriverManager.getConnection(props.getProperty("url"),props.getProperty("username"),props.getProperty("password"));
	}
	
	public static void close(ResultSet rs,Statement statement, Connection conn){
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				statement = null;
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}
}
