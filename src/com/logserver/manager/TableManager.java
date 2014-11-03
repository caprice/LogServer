package com.logserver.manager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.logserver.db.MySqlSessionFactory;
import com.logserver.model.BaseEntity;
import com.logserver.model.JibenxingweiLog;
import com.logserver.utils.DateUtils;
import com.logserver.utils.LogManagerUtil;
import com.logserver.utils.MailUtils;
import com.logserver.utils.OrderConstance;

public class TableManager {
	public final static int days = 60;
	/**
	 * 
	 * <p>Title: createTable</p>
	 * <p>Description: 向数据库创建日志表</p>
	 * @author guangshuai.wang
	 */
	public void createTable(){
		//初始化表名
		this.initTableNames();
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
		// 今日
		String timeStamp = format.format(new Date());
		// 明天
		String tomStamp = format.format(Long
				.valueOf(new Date().getTime() + 86400000L));
		Collection<String> tableNames = OrderConstance.tableNamesMap.values();
		SqlSessionFactory factory = MySqlSessionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		//计算固定删除表的日期
		long todayZero = DateUtils.getTodayZero(0);
		long deleDate = todayZero - days * 24 * 60 * 60 *1000L;
		String deleteDate = format.format(deleDate);
		
		for(String str :  tableNames){
			if(str == null){
				continue;
			}
			String tableName = "";
			//创建今天的日志表
			tableName = str + "_log_" + timeStamp;
			BaseEntity base  = new BaseEntity();
			base.setTableName(tableName);
			session.update(str + "_mapper.createTable", base);
			LogManagerUtil.ServerInfo("创建表：" + tableName + " 成功");
			
			//创建明天的表
			tableName = str + "_log_" + tomStamp;
			base.setTableName(tableName);
			if(session.update(str + "_mapper.createTable", base) == 0){
				LogManagerUtil.ServerInfo("创建表：" + tableName + " 成功");
			}
			//删除某天前的日志
			JibenxingweiLog log = new JibenxingweiLog();
			log.setTableName( str+ "_log_" + deleteDate);
			session.delete("base_mapper.deleteTable",log);
		}
		
		session.close();
		LogManagerUtil.ServerInfo("创建日志表 成功");
	}
	/**
	 * 
	 * <p>Title: initTableNames</p>
	 * <p>Description:初始化表的类型和名字到一个map中，方便获取 </p>
	 * @author guangshuai.wang
	 */
	@SuppressWarnings("unchecked")
	private void initTableNames(){
		InputStream input = null;
		try{
			SAXReader read = new SAXReader();
			input = new FileInputStream("config/TableNames.xml");
			Document doc = read.read(input);
			Element root = doc.getRootElement();
			Iterator<Element> ite = root.element("tables").elementIterator();
			while(ite.hasNext()){
				Element ele = ite.next();
				OrderConstance.tableNamesMap.put(Integer.parseInt(ele.attributeValue("type")), ele.attributeValue("name"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void insertLog(String jsonData,int type){
		GetLog getLog = new GetLog();
		BaseEntity log = getLog.getLogEntity(jsonData, type);
		SqlSession session = null;
		try{
		SqlSessionFactory factory = MySqlSessionFactory.getSqlSessionFactory();
		 session = factory.openSession();
		//根据类型获取表名
		String tableName = OrderConstance.tableNamesMap.get(type);
		//设置带时间格式的表名
		log.setTableName(tableName +"_log_"+ DateUtils.getDate(System.currentTimeMillis()));
		session.insert(tableName + "_mapper.insertLog", log);
		}catch (Exception e) {
			LogManagerUtil.LogErr("往数据库插入数据错误：" + e.getMessage() + ";/n" + e.getCause());
			MailUtils.send("日志服务器写入数据库线程异常", e.getMessage() + "\n" + e.getCause() + "\n");
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	public static void main(String[] args) {
		/*RegistLog log = new RegistLog(0, "www", "aa", new Date(System.currentTimeMillis()), "11", "dd", 1, "11111", 1);
		String*/
	}
}
