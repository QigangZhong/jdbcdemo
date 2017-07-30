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
			// 1. 注册数据库驱动
			// mysql在Driver类的实现中自己注册了一次，所以会导致mysql驱动会被注册两次
			// 引用org.gjt.mm.mysql.Driver类，依赖了mysql 的 Driver实现
			// DriverManager.registerDriver(new Driver());
			Class.forName("com.mysql.jdbc.Driver");// 利用反射实例化了mysql.Driver类,注册了Driver

			// 2. 获取数据库链接
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/jdbc?characterEncoding=utf8&useSSL=true",
							"root", "root");

			// 3. 获取传输器对象
			statement = conn.createStatement();

			// 4. 利用传输器传输sql语句到数据库执行，获取结果集对象
			rs = statement.executeQuery("select * from user");

			// 5. 遍历结果集，获取查询数据
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
			// 6. 关闭资源
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
