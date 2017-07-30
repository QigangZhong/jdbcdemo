package com.qigang.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Demo {
	@Test
	public void Query() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try{
			ComboPooledDataSource source=new ComboPooledDataSource();
			//C3P0的DataSource默认会寻找c3p0-config.xml这个默认配置文件
			//source.setDriverClass("com.mysql.jdbc.Driver");
			//source.setJdbcUrl("jdbc:mysql://localhost:3306/jdbc?characterEncoding=utf8&amp;useSSL=true");
			//source.setUser("root");
			//source.setPassword("root");

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
		}
	}
}
