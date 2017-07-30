package com.qigang.pool;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

public class MyQueryRunner {
	private DataSource _source=null;
	public MyQueryRunner(){}
	public MyQueryRunner(DataSource source){
		this._source=source;
	}
	
	public int update(String sql, Object... params) throws SQLException{
		Connection conn=_source.getConnection();
		PreparedStatement ps=conn.prepareStatement(sql);
		ParameterMetaData pmd = ps.getParameterMetaData();
		int paramCount =pmd.getParameterCount();
		for(int i=1;i<=paramCount;i++){
			ps.setObject(i, params[i-1]);
		}
		
		int rowCount = ps.executeUpdate();
		
		DbUtils.closeQuietly(conn, ps, null);
		
		return rowCount;
	}
}
