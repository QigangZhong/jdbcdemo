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
		//1. ʹ���Լ�д�����ӳ�
		//MyPool pool=new MyPool();
		
		//2. ʹ��appache��DBCP���ݿ����ӳ�
		//BasicDataSource source=new BasicDataSource();
		//source.setDriverClassName("com.mysql.jdbc.Driver");
		//source.setUrl("jdbc:mysql://localhost:3306/jdbc?characterEncoding=utf8&useSSL=true");
		//source.setUsername("root");
		//source.setPassword("root");
		
		try{
			//3. ʹ��appache��DBCP���ݿ����ӳأ�ͨ����ȡ�����ļ��ķ�ʽ
			Properties props=new Properties();
			props.load(new FileReader(DBCPDemo.class.getClassLoader().getResource("dbcp.properties").getPath()));
			DataSource source = BasicDataSourceFactory.createDataSource(props);
		
		
			//�����ӳ�����һ�����ӳ���
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
			
			//ԭ������ô�ر����ӻ�����ô�ر�����
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
