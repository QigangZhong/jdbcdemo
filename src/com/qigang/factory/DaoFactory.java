package com.qigang.factory;

import java.io.FileReader;
import java.util.Properties;

public class DaoFactory {
	private static DaoFactory _instance=new DaoFactory();
	private static Properties _props=null;
	private DaoFactory(){
		
	}
	static{
		//获取配置
		try {
			_props=new Properties();
			_props.load(new FileReader(DaoFactory.class.getClassLoader().getResource("config.properties").getPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DaoFactory instance(){
		return _instance;
	}
	
	public Object getDao(){
		
		try {
			//从配置中读取需要实例化的类名称
			String className=_props.getProperty("com.qigang.xxx");
			Class c = Class.forName(className);
			return c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
