package com.logserver.db;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
public class MySqlSessionFactory {

	private static SqlSessionFactory sqlSessionFactory;
	
	public synchronized static SqlSessionFactory getSqlSessionFactory(){
		if(sqlSessionFactory == null){
			try{
				//Reader reader = Resources.getResourceAsReader("com/config/mybatis_config.xml");
				InputStream reader = new FileInputStream("config/mybatis_config.xml");
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlSessionFactory;
	}
	
}
