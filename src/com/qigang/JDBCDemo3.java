package com.qigang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import org.junit.Test;

import com.qigang.jdbc.util.JDBCUtils;

public class JDBCDemo3 {
	@Test
	public void add(){
		Connection conn=null;
		PreparedStatement ps=null;
		Savepoint sp=null;
		try {
			conn=JDBCUtils.getConn();
			//设置为非自动提交事务，主动调用commit来提交事务
			conn.setAutoCommit(false);
			
			//执行第一个SQL
			ps=conn.prepareStatement("insert into user(name,password,email,birthday) values('zhaoliu','123','zhaoliu@abc.com','2000-01-01')");
			int row = ps.executeUpdate();
			System.out.println(row);
			
			//设置回滚点
			sp = conn.setSavepoint();
			
			//执行第二个SQL
			ps=conn.prepareStatement("insert into user(name,password,email,birthday) values('zhaoliu1','123','zhaoliu@abc.com','2000-01-01')");
			row = ps.executeUpdate();
			System.out.println(row);
			
			
			//提交事务
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(sp==null)
				{
					//出现异常时回滚事务,如果回滚点没有设置，全部回滚
					conn.rollback();
				}else{
					//如果回滚点设置了，那么回滚到回滚点,然后提交,回滚点之前的操作还是可以提交到数据库
					conn.rollback(sp);
					conn.commit();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JDBCUtils.close(null, ps, conn);
		}
	}
}
