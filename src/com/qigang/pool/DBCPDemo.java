package com.qigang.pool;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

public class DBCPDemo {
	@Test
	public void Query() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//1. 使用自己写的连接池
		//MyPool pool=new MyPool();
		
		//2. 使用appache的DBCP数据库连接池
		//BasicDataSource source=new BasicDataSource();
		//source.setDriverClassName("com.mysql.jdbc.Driver");
		//source.setUrl("jdbc:mysql://localhost:3306/jdbc?characterEncoding=utf8&useSSL=true");
		//source.setUsername("root");
		//source.setPassword("root");
		
		try{
			//3. 使用appache的DBCP数据库连接池，通过读取配置文件的方式
			Properties props=new Properties();
			props.load(new FileReader(DBCPDemo.class.getClassLoader().getResource("dbcp.properties").getPath()));
			DataSource source = BasicDataSourceFactory.createDataSource(props);
		
		
			//从连接池中拿一个连接出来
			//conn=pool.getConnection();
			conn=source.getConnection();
			ps=conn.prepareStatement("select * from user");
			rs=ps.executeQuery();
			while(rs.next()){
				String name=rs.getString("name");
				System.out.println(name);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					rs=null;
				}
			}
			
			if(ps!=null){
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					ps=null;
				}
			}
			
			//原来该怎么关闭连接还是怎么关闭连接
			if(conn!=null){
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					conn=null;
				}
			}
			
//			if (source != null) {
//				try {
//					source.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					source = null;
//				}
//			}
		}
	}
}
