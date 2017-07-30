package com.qigang.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

public class TestMyPool {
	@Test
	public void Query() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MyPool pool=new MyPool();
		try{
			//从连接池中拿一个连接出来
			conn=pool.getConnection();
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
