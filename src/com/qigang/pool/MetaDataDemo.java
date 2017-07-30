package com.qigang.pool;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.qigang.jdbc.util.JDBCUtils;

public class MetaDataDemo {
	public static void main(String[] args) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try{
			ComboPooledDataSource source=new ComboPooledDataSource();
			conn=source.getConnection();
			DatabaseMetaData md = conn.getMetaData();
			String driverName = md.getDriverName();
			String userName=md.getUserName();
			String url=md.getURL();
			System.out.println("driverName:"+driverName+" userName:"+userName+" url:"+url);
			
			ps=conn.prepareStatement("select * from user where id=? and name=?");
			ParameterMetaData pmd = ps.getParameterMetaData();
			int count=pmd.getParameterCount();
			System.out.println("�����ĸ���Ϊ:"+count);
			//MySql������ȡ���������ͣ�������jdbcUrl������ϲ���&amp;generateSimpleParameterMetadata=true
			//���磺jdbc:mysql://localhost:3306/jdbc?characterEncoding=utf8&amp;useSSL=true&amp;generateSimpleParameterMetadata=true
			String type = pmd.getParameterTypeName(1);
			//MySql���۲�����ɶ��������VARCHAR
			System.out.println("��һ��������������:"+type);
			
			ps.setInt(1, 1);
			ps.setString(2, "zhangsan");
			
			
			rs=ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int rsCount = rsmd.getColumnCount();
			System.out.println("�����������Ϊ:"+rsCount);
			String c1Name=rsmd.getColumnName(1);
			String c1Type=rsmd.getColumnTypeName(1);
			System.out.println("��һ�е�����Ϊ:"+c1Name+" ����Ϊ:"+c1Type);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//JDBCUtils.close(rs, ps, conn);
			DbUtils.closeQuietly(conn, ps, rs);//ʹ��Appache��DbUtils���ر���Դ
		}
	}
}
