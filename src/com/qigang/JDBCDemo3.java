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
			//����Ϊ���Զ��ύ������������commit���ύ����
			conn.setAutoCommit(false);
			
			//ִ�е�һ��SQL
			ps=conn.prepareStatement("insert into user(name,password,email,birthday) values('zhaoliu','123','zhaoliu@abc.com','2000-01-01')");
			int row = ps.executeUpdate();
			System.out.println(row);
			
			//���ûع���
			sp = conn.setSavepoint();
			
			//ִ�еڶ���SQL
			ps=conn.prepareStatement("insert into user(name,password,email,birthday) values('zhaoliu1','123','zhaoliu@abc.com','2000-01-01')");
			row = ps.executeUpdate();
			System.out.println(row);
			
			
			//�ύ����
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(sp==null)
				{
					//�����쳣ʱ�ع�����,����ع���û�����ã�ȫ���ع�
					conn.rollback();
				}else{
					//����ع��������ˣ���ô�ع����ع���,Ȼ���ύ,�ع���֮ǰ�Ĳ������ǿ����ύ�����ݿ�
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
