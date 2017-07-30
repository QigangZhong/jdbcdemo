package com.qigang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.qigang.jdbc.util.JDBCUtils;

public class JDBCDemo2 {
	@Test
	public void add(){
		Connection conn=null;
		Statement statement=null;
		try {
			//1. ע������
			//2. ��ȡ����
			conn=JDBCUtils.getConn();
			
			//3. ��ȡͨ��
			statement=conn.createStatement();
			
			//4. ִ��sql����ȡ�����
			int i = statement.executeUpdate("insert into user(name,password,email,birthday) values('zhaoliu','123','zhaoliu@abc.com','2000-01-01')");
			System.out.println(i);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtils.close(null, statement, conn);
		}
	}
	
	@Test
	public void update(){
		Connection conn=null;
		Statement statement=null;
		try {
			//1. ע������
			//2. ��ȡ����
			conn=JDBCUtils.getConn();
			
			//3. ��ȡͨ��
			statement=conn.createStatement();
			
			//4. ִ��sql����ȡ�����
			int i = statement.executeUpdate("update user set password=456 where name='zhaoliu'");
			System.out.println(i);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtils.close(null, statement, conn);
		}
	}
	
	@Test
	public void delete(){
		Connection conn=null;
		Statement statement=null;
		try {
			//1. ע������
			//2. ��ȡ����
			conn=JDBCUtils.getConn();
			
			//3. ��ȡͨ��
			statement=conn.createStatement();
			
			//4. ִ��sql����ȡ�����
			int i = statement.executeUpdate("delete from user where name='zhaoliu'");
			System.out.println(i);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtils.close(null, statement, conn);
		}
	}
	
	@Test
	public void find(){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			//1. ע������
			//2. ��ȡ����
			conn=JDBCUtils.getConn();
			
			//3. ��ȡͨ��
			ps=conn.prepareStatement("select * from user where name=?");
			ps.setString(1, "zhangsan");
			
			//4. ִ��sql����ȡ�����
			rs = ps.executeQuery();
			
			//5. ���������
			while(rs.next()){
				String name=rs.getString("name");
				System.out.println(name);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtils.close(rs, ps, conn);
		}
	}
}
