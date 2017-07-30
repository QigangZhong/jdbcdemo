package com.qigang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.junit.Test;

public class JDBCDemo1 {
	@Test
	public void TestJDBC() {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			// 1. ע�����ݿ�����
			// mysql��Driver���ʵ�����Լ�ע����һ�Σ����Իᵼ��mysql�����ᱻע������
			// ����org.gjt.mm.mysql.Driver�࣬������mysql �� Driverʵ��
			// DriverManager.registerDriver(new Driver());
			Class.forName("com.mysql.jdbc.Driver");// ���÷���ʵ������mysql.Driver��,ע����Driver

			// 2. ��ȡ���ݿ�����
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/jdbc?characterEncoding=utf8&useSSL=true",
							"root", "root");

			// 3. ��ȡ����������
			statement = conn.createStatement();

			// 4. ���ô���������sql��䵽���ݿ�ִ�У���ȡ���������
			rs = statement.executeQuery("select * from user");

			// 5. �������������ȡ��ѯ����
			while (rs.next()) {
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				Date birthday = rs.getDate("birthday");

				System.out.println("name:" + name + "  password:" + password
						+ "  email:" + email + "  birthday:"
						+ birthday.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. �ر���Դ
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

}
