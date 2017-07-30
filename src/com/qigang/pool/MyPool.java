package com.qigang.pool;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;


public class MyPool implements DataSource{

	private static String connStr="jdbc:mysql://localhost:3306/jdbc?characterEncoding=utf8&useSSL=true";
	private static String userName="root";
	private static String password="root";
	
	private static List<Connection> pool =new LinkedList<Connection>();
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    for(int i=0;i<5;i++){
		    	Connection conn = DriverManager.getConnection(connStr,userName,password);
		    	pool.add(conn);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		if(pool.size()<=0){
			Connection conn = DriverManager.getConnection(connStr,userName,password);
	    	pool.add(conn);
		}
		
		final Connection conn=pool.remove(0);
		
		//利用动态代理改造close方法
		Connection proxy = (Connection)Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				if("close".equals(method.getName())){
					//对于想要改造的close方法，单独处理
					returnConnection(conn);
					return null;
				}else{
					//不想改造的方法还是调用原来的
					return method.invoke(conn, args);
				}
			}
		});
		
		System.out.println("获取一个连接,池里面还剩下"+pool.size()+"个连接");
		
		//返回一个代理对象, 里面的close方法已经经过改造
		return proxy;
	}
	
	private void returnConnection(Connection conn){
		try {
			if(conn!=null && !conn.isClosed()){
				pool.add(conn);
				System.out.println("还了一个连接,池里面还剩下"+pool.size()+"个连接");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
