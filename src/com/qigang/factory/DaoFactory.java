package com.qigang.factory;

import java.io.FileReader;
import java.util.Properties;

public class DaoFactory {
	private static DaoFactory _instance=new DaoFactory();
	private static Properties _props=null;
	private DaoFactory(){
		
	}
	static{
		//��ȡ����
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
			//�������ж�ȡ��Ҫʵ������������
			String className=_props.getProperty("com.qigang.xxx");
			Class c = Class.forName(className);
			return c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
