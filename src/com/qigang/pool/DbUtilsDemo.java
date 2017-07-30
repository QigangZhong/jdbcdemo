package com.qigang.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbUtilsDemo {
	/**
	 * 老的方式执行更新语句
	 */
	//@Test()
	public void update_old() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ComboPooledDataSource source=new ComboPooledDataSource();
		try {
			conn=source.getConnection();
			ps=conn.prepareStatement("update user set email=? where name=?");
			ps.setString(1, "email@example.com");
			ps.setString(2, "zhangsan");
			
			int rowCount = ps.executeUpdate();
			
			System.out.println("更新行数:"+rowCount);
		} catch (Exception e) {
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
	
	/**
	 * 使用DbUtils里面的QueryRunner2行就可以执行更新
	 * @throws SQLException
	 */
	//@Test
	public void update_new() throws SQLException{
		QueryRunner runner=new QueryRunner(new ComboPooledDataSource());
		runner.update("update user set email=? where name=?","abc@abc.com","zhangsan");
	}
	
	/**
	 * 用老的方式执行查询
	 */
	//@Test
	public void query_old(){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ComboPooledDataSource source=new ComboPooledDataSource();
		try {
			conn=source.getConnection();
			ps=conn.prepareStatement("select * from user where name=?");
			ps.setString(1, "zhangsan");
			
			rs=ps.executeQuery();
			while(rs.next()){
				String name=rs.getString("name");
				System.out.println(name);
			}
		} catch (Exception e) {
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
	
	//@Test
	public void query_new() throws SQLException{
		QueryRunner runner=new QueryRunner(new ComboPooledDataSource());
		List<String> names = runner.query("select * from user where name=?", new ResultSetHandler<List<String>>(){

			@Override
			public List<String> handle(ResultSet rs) throws SQLException {
				List<String> list=new ArrayList<String>();
				while(rs.next()){
					String name=rs.getString("name");
					System.out.println(name);
					list.add(name);
				}
				return list;
			}}, "zhangsan");
	}
	
	@Test
	public void query_new1() throws SQLException{
		/*QueryRunner runner=new QueryRunner(new ComboPooledDataSource());
		//ArrayHandler会把结果集第一行放到Object[]中返回
		Object[] objs=runner.query("select * from user where name=?", new ArrayHandler(), "zhangsan");
		System.out.println(objs);*/
		
		/*QueryRunner runner=new QueryRunner(new ComboPooledDataSource());
		//ArrayListHandler会把结果集放到List<Object[]>中
		List<Object[]> rows=runner.query("select * from user where name=?", new ArrayListHandler(), "zhangsan");
		System.out.println(rows);*/
		
		
		//最常用的方式
		QueryRunner runner=new QueryRunner(new ComboPooledDataSource());
		User user=runner.query("select * from user where name=?", new BeanHandler<User>(User.class), "zhangsan");
		System.out.println(user.getName());
		
		List<User> users=runner.query("select * from user where id>?", new BeanListHandler<User>(User.class), 2);
		System.out.println(users.size());
		
		//Map的方式
		Map<String,Object> map=runner.query("select * from user where name=?", new MapHandler(), "zhangsan");
		System.out.println(map.get("name"));

		//MapList的方式
		List<Map<String,Object>> mapList=runner.query("select * from user where name=?", new MapListHandler(), "zhangsan");
		System.out.println(mapList.size());
		
		//ColumnList的方式
		List<Object> column=runner.query("select * from user where name=?", new ColumnListHandler(3), "zhangsan");
		System.out.println(column);
		
		//KeyedHandler
		Map<Object,Map<String,Object>> keyedMap =runner.query("select * from user where name=?", new KeyedHandler(), "zhangsan");
		System.out.println(keyedMap.get(1).get("name"));
		
		//ScalarHandler获取第一行指定列的值
		Long scalar =(Long)runner.query("select count(*) from user", new ScalarHandler());
		System.out.println(scalar);
	}
}
