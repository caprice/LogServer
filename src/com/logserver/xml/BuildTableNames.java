package com.logserver.xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.logserver.utils.OrderConstance;
import com.logserver.utils.StringUtils;

/**
 * 
 * @author guangshuai.wang
 *         <p>
 *         Description:根据配置表TableNames.xml自动创建类，配置文件等
 *         </p>
 */
public class BuildTableNames {
	// 根据配置文件创建日志对应的基类，子类，及mybatis配置文件
	public void build() {
		try {
			SAXReader read = new SAXReader();
			Document doc = read.read(new File("config/TableNames.xml"));
			Element root = doc.getRootElement();
			Element common = root.element("common");
			this.createBase(common);
			this.createMybatisConfig();
			//创建消息号
			this.createProcessCode();
			//创建获取log类型的类
			this.createGetLog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>
	 * Title: createBase
	 * </p>
	 * <p>
	 * Description: 创建表的基类：BaseEntity.java
	 * </p>
	 * 
	 * @author guangshuai.wang
	 */
	@SuppressWarnings("unchecked")
	public void createBase(Element common) {

		StringBuilder str = new StringBuilder();
		StringBuilder fieldMethod = new StringBuilder();
		// 记录字段名
		StringBuilder fieldStr = new StringBuilder();
		// 记录字段的类型
		StringBuilder typeStr = new StringBuilder();
		// 记录所有字段的sql类型
		StringBuilder sqlTypeSB = new StringBuilder();
		str.append("package com.logserver.model;\n");
		str.append("//由BuildTableNames.java自动生成\n");
		str.append("public class BaseEntity {\n");
		try {
			Iterator<Element> eles = common.elementIterator();
			while (eles.hasNext()) {
				Element ele = eles.next();
				str.append("\t//" + ele.attributeValue("desc") + "\n\t");
				str.append("public ");
				str.append(ele.attributeValue("classType") + " ");
				str.append(ele.getName() + ";\n");
				sqlTypeSB.append(ele.attributeValue("sqlType") + ",");
				fieldStr.append(ele.getName() + ",");
				typeStr.append(ele.attributeValue("classType") + ",");
			}
			String[] fields = fieldStr.toString().split(",");
			String[] types = typeStr.toString().split(",");
			// 添加一个tableName字段，用于记录表的名字
			str.append("\t//存储表的名字\n");
			str.append("\tpublic String tableName;\n");
			// 加构造方法
			str.append("\tpublic BaseEntity() {}\n\t");
			str.append("public BaseEntity(");
			// 加get/set方法和构造方法
			StringBuilder thisBuild = new StringBuilder();
			for (int i = 0; i < fields.length; i++) {
				// 添加构造方法
				str.append(types[i]);
				str.append(" ");
				str.append(fields[i] + ", ");
				thisBuild.append("\n\t\t");
				thisBuild.append("this." + fields[i] + "=" + fields[i] + ";");

				fieldMethod.append("public void set"
						+ StringUtils.firstToUpper(fields[i]) + "(" + types[i]
						+ " " + fields[i] + ") {\n");
				fieldMethod.append("\t\t");
				fieldMethod.append("this." + fields[i] + " = " + fields[i]
						+ ";\n\t");
				fieldMethod.append("}\n\t");

				fieldMethod.append("public " + types[i] + " get"
						+ StringUtils.firstToUpper(fields[i]) + "() {\n\t\t");
				fieldMethod.append("return this." + fields[i]);
				fieldMethod.append(";\n\t}\n\t");
			}
			str.deleteCharAt(str.length() - 2);
			str.append(") {");
			str.append(thisBuild);
			str.append("\n");
			str.append("\t}\n\t");
			str.append(fieldMethod);
			// 添加tableName字段的get/set方法
			str
					.append("public void setTableName(String tableName){\n\t\tthis.tableName = tableName;\n\t}\n");
			str
					.append("\tpublic String getTableName(){\n\t\treturn tableName;\n\t}\n");
			str.append("}");
			File file = new File(System.getProperty("user.dir") + "/"
					+ "src/com/logserver/model/BaseEntity.java");
			FileOutputStream out = new FileOutputStream(file);
			out.write(str.toString().getBytes("utf8"));
			out.flush();
			out.close();
			System.out.println("====生成基类 BaseEntity.java 成功===");
			// 创建详细的子类
			Element parentEle = common.getParent();

			this.createTableClass(fieldStr.toString(), typeStr.toString(),
					sqlTypeSB.toString(), parentEle.element("tables"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * <p>
	 * Title: createTableClass
	 * </p>
	 * <p>
	 * Description: 创建与数据库表相关的类
	 * </p>
	 * 
	 * @param basefields
	 *            基类的字段名，多个以逗号隔开。
	 * @param tables
	 *            表的xml节点
	 * @param sqlTypeStr
	 *            数据库的类型
	 * @author guangshuai.wang
	 */
	@SuppressWarnings("unchecked")
	public void createTableClass(String basefields, String baseType,
			String sqlTypeStr, Element tables) {
		String baseSqlType = sqlTypeStr;
		Iterator<Element> iteTables = tables.elementIterator();
		while (iteTables.hasNext()) {
			Element tableEle = iteTables.next();
			//用于生成数据库的dao类
			StringBuilder daoStr = new StringBuilder();
			daoStr.append("package com.mirun.gamelog.dao;\n");
			daoStr.append("import java.util.List;\n");
			daoStr.append("import net.paoding.rose.jade.annotation.DAO;\n");
			daoStr.append("import net.paoding.rose.jade.annotation.SQL;\n");
			daoStr.append("import com.mirun.gamelog.model." + StringUtils.firstToUpper(tableEle.attributeValue("name")));
			daoStr.append("Log;\n");
			daoStr.append("//由log服务器自动生成\n\n");
			daoStr.append("@DAO\n");
			daoStr.append("public interface "+ StringUtils.firstToUpper(tableEle.attributeValue("name")) +"DAO {\n");
			
			daoStr.append("\t@SQL(\"SELECT COUNT(*) FROM information_schema.TABLES WHERE TABLE_NAME= :1\")\n");
			daoStr.append("\tpublic int selectTableExistsByName(String tableName);\n\n");
			
			daoStr.append("\t@SQL(\"select * from ##(:1) where 1=1 and createTime >= :2 and createTime <= :3\")\n");
			daoStr.append("\tpublic List<"+ StringUtils.firstToUpper(tableEle.attributeValue("name")) +"Log> selectGameDateLog(String tableName, String beginTime, String endTime);\n\n");
			
			daoStr.append("\t@SQL(\"select * from ##(:1) where 1=1 and createTime >= :2 and createTime <= :3 ##(:4)\")\n");
			daoStr.append("\tpublic List<"+ StringUtils.firstToUpper(tableEle.attributeValue("name")) +"Log> selectGameDateLog(String tableName, String beginTime, String endTime, String paramSql);\n\n");
			daoStr.append("\t@SQL(\"select count(*) from ##(:1) where 1 = 1 ##(:2)\")\n");
			daoStr.append("\tpublic int selectGameDateLogCount(String tableName, String paramSql);\n\n");
			daoStr.append("}");
			StringBuilder str = new StringBuilder();
			// 记录表名,按类型存储表名，以利于查找
			OrderConstance.tableNamesMap.put(Integer.parseInt(tableEle
					.attributeValue("type")), tableEle.attributeValue("name"));
			str.append("package com.logserver.model;\n");
			str.append("//由BuildTableNames.java自动生成\n");
			str.append("public class "
					+ StringUtils.firstToUpper(tableEle.attributeValue("name"))
					+ "Log");
			str.append(" extends BaseEntity");
			str.append(" {\n");
			Iterator<Element> iteField = tableEle.elementIterator();
			// 记录所有字段
			StringBuilder fieldStr = new StringBuilder();
			// 记录所有字段的类型
			StringBuilder typeStr = new StringBuilder();
			// 记录所有字段的sql类型
			StringBuilder sqlTypeSB = new StringBuilder();
			while (iteField.hasNext()) {
				Element fieldEle = iteField.next();
				str.append("\t//" + fieldEle.attributeValue("desc") + "\n");
				sqlTypeSB.append(fieldEle.attributeValue("sqlType") + ",");
				str.append("\tpublic " + fieldEle.attributeValue("classType"));
				str.append(" ");
				str.append(fieldEle.getName());
				str.append(";\n");
				fieldStr.append(fieldEle.getName() + ",");
				typeStr.append(fieldEle.attributeValue("classType") + ",");

			}
			str.append("\tpublic "
					+ StringUtils.firstToUpper(tableEle.attributeValue("name")
							+ "Log() {}\n"));
			// 带参数的构造方法：继承基类
			str.append("\tpublic "
					+ StringUtils.firstToUpper(tableEle.attributeValue("name"))
					+ "Log");
			str.append("(");
			String[] baseFields = basefields.split(",");
			String[] baseTypes = baseType.split(",");
			// 添加基类字段
			for (int i = 0; i < baseFields.length; i++) {
				str.append(baseTypes[i]);
				str.append(" ");
				str.append(baseFields[i]);
				str.append(",");
			}
			// 添加本类字段
			String[] fields = fieldStr.toString().split(",");
			String[] typeStrs = typeStr.toString().split(",");
			for (int i = 0; i < fields.length; i++) {
				str.append(typeStrs[i]);
				str.append(" ");
				str.append(fields[i]);
				str.append(",");
			}
			str.deleteCharAt(str.length() - 1);
			str.append(")");
			str.append("{\n");
			str.append("\t\t");
			str.append("super(");
			for (int i = 0; i < baseFields.length; i++) {
				str.append(baseFields[i]);
				str.append(",");
			}
			str.deleteCharAt(str.length() - 1);
			str.append(");\n");
			// 在构造函数中添加本类字段
			for (int i = 0; i < fields.length; i++) {
				str.append("\t\tthis." + fields[i]);
				str.append(" = ");
				str.append(fields[i]);
				str.append(";\n");
			}
			str.append("\t}\n");

			// 添加set/get方法

			for (int i = 0; i < typeStrs.length; i++) {
				str.append("\tpublic void");
				str.append(" set" + StringUtils.firstToUpper(fields[i]));
				str.append("(" + typeStrs[i] + " " + fields[i] + "){\n");
				str.append("\t\t");
				str.append("this." + fields[i]);
				str.append(" = " + fields[i]);
				str.append(";\n");
				str.append("\t}\n");

				str.append("\tpublic " + typeStrs[i] + " get"
						+ StringUtils.firstToUpper(fields[i]));
				str.append("(){\n");
				str.append("\t\t");
				str.append("return this." + fields[i] + ";\n");
				str.append("\t}\n");
			}
			str.append("}");
			// 生成文件
			File file = new File(System.getProperty("user.dir")
					+ "/"
					+ "src/com/logserver/model/"
					+ StringUtils.firstToUpper(tableEle.attributeValue("name")
							+ "Log") + ".java");
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				out.write(str.toString().getBytes("utf8"));
				out.flush();
				out.close();
				//生成dao文件，这里是游戏项目的路径，记得核对
				file = new File("I:/server/trunk/club/src/com/mirun/gamelog/dao/" + StringUtils.firstToUpper(tableEle.attributeValue("name")) + "DAO.java");
				out = new FileOutputStream(file);
				out.write(daoStr.toString().getBytes("utf8"));
				out.flush();
				out.close();
				System.out.println("====生成日志类"
						+ StringUtils.firstToUpper(tableEle
								.attributeValue("name")) + "Log.java 成功===");
				String allfields = basefields + fieldStr.toString();
				String newsqlTypeStr = baseSqlType + sqlTypeSB.toString();
				this.CreateMybatisXml(tableEle.attributeValue("name"),
						newsqlTypeStr, allfields);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void CreateMybatisXml(String tableName, String sqlTypeStr,
			String fieldStr) {
		StringBuilder str = new StringBuilder();
		str
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
						+ "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n"
						+ "\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		str.append("<mapper namespace=\"" + tableName + "_mapper\">\n");
		str.append("<insert id=\"insertLog\" parameterType=\"" + tableName
				+ "\">\n");
		str.append("\t\tinsert into ");
		str.append("${tableName} ");
		str.append("(");
		String[] fields = fieldStr.split(",");
		StringBuilder parameters = new StringBuilder();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals("id")) {
				continue;
			}
			str.append(fields[i]);
			str.append(",");
			parameters.append("#{");
			parameters.append(fields[i]);
			parameters.append("},");
		}
		str.deleteCharAt(str.length() - 1);
		str.append(") values(");
		str.append(parameters.deleteCharAt(parameters.length() - 1).toString());
		str.append(")\n");
		str.append("</insert>\n");

		str.append("<update id=\"createTable\" parameterType=\"" + tableName
				+ "\">\n");
		str.append("\tcreate table if not exists ${tableName} ");
		str.append("(");
		String[] types = sqlTypeStr.split(",");
		for (int i = 0; i < fields.length; i++) {
			if(fields[i].equals("id")){
				continue;
			}
			str.append(fields[i]);
			str.append(" ");
			str.append(types[i]);
			str.append(",");
		}
		str.deleteCharAt(str.length() - 1);
		str.append(")");
		str.append(" ENGINE = MyISAM DEFAULT CHARSET UTF8\n");
		str.append("</update>\n");
		str.append("</mapper>");
		
		// 生成文件
		File file = new File(System.getProperty("user.dir") + "/"
				+ "src/com/logserver/model/"
				+ StringUtils.firstToUpper(tableName + "Log") + ".xml");
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			out.write(str.toString().getBytes("utf8"));
			out.flush();
			out.close();
			System.out.println("====生成日志类"
					+ StringUtils.firstToUpper(tableName) + "Log.xml 成功===");
			str.delete(0, str.length());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void createMybatisConfig() {
		StringBuilder strSB = new StringBuilder();
		strSB.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
						+ "<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n"
						+ "\"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n");
		strSB.append("<configuration>\n");
		strSB.append("\t<typeAliases>\n");
		Collection<String> col = OrderConstance.tableNamesMap.values();
		StringBuilder res = new StringBuilder();
		for (String str : col) {
			if (str == null) {
				continue;
			}
			strSB.append("\t\t<typeAlias type=\"com.logserver.model."
					+ StringUtils.firstToUpper(str) + "Log\" alias=\"" + str
					+ "\"/>\n");
			res.append("\t<mapper resource=\"com/logserver/model/"+ StringUtils.firstToUpper(str) +"Log.xml\"/>\n");
		}
		//获取连接数据库的信息
		InputStream in = null;
		String mysql_url = null;
		String mysql_root = null;
		String mysql_password = null;
		try {
			in = new BufferedInputStream(new FileInputStream("config/server_config.properties"));
			Properties pro = new Properties();
			pro.load(in);
			mysql_url = pro.getProperty("mysql_url");
			mysql_root = pro.getProperty("mysql_root");
			mysql_password = pro.getProperty("mysql_password");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res.append("\t<mapper resource=\"com/logserver/model/BaseEntity.xml\"/>\n");
		strSB.append("\t</typeAliases> \n");
		strSB.append("\t<environments default=\"development\">\n"
						+ "\t\t<environment id=\"development\">\n"
						+ "\t\t<transactionManager type=\"JDBC\"/>\n"
						+ "\t\t<dataSource type=\"POOLED\">\n"
						+ "\t\t\t<property name=\"driver\" value=\"com.mysql.jdbc.Driver\" />\n"
						+ "\t\t\t<property name=\"url\" value=\"" + mysql_url + "?useUnicode=true&amp;characterEncoding=utf-8\" />\n"
						+ "\t\t\t<property name=\"username\" value=\"" + mysql_root + "\" />\n"
						+ "\t\t\t<property name=\"password\" value=\""+ mysql_password+"\" />\n"
						+ "\t\t\t<property name='poolMaximumActiveConnections' value='20'/>\n"
						+ "\t\t\t<property name='poolMaximumCheckoutTime' value='20000'/>\n"
						+ "\t\t</dataSource>\n" + "\t\t</environment>\n"
						+ "</environments> \n");
		strSB.append("<mappers>\n");
		strSB.append(res.toString());
		strSB.append("</mappers>\n");
		strSB.append("</configuration>");
		
		// 生成文件
		File file = new File("config/"
				+ "mybatis_config.xml");
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			out.write(strSB.toString().getBytes("utf8"));
			out.flush();
			out.close();
			System.out.println("====生成配置文件"
					+ "mybatis_config.xml 成功===");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <p>Title: createProcessCode</p>
	 * <p>Description: 创建消息号，这些消息号基于日志表的类型</p>
	 * @author guangshuai.wang
	 */
	public void createProcessCode(){
		StringBuilder strSB = new StringBuilder();
		 strSB.append("package com.logserver.model;\n");
		 strSB.append("//由BuildTableNames.java自动生成\n");
		 strSB.append("public class ProcessCode {\n");
		 Iterator<Integer> keys = OrderConstance.tableNamesMap.keySet().iterator();
		 while(keys.hasNext()){
			 int key = keys.next();
			 strSB.append("\tpublic final static int "+ OrderConstance.tableNamesMap.get(key).toUpperCase() + "_LOG = " + key);
			 strSB.append(";\n");
		 }
		 strSB.append("}");
		// 生成文件
			File file = new File(System.getProperty("user.dir")
					+ "/"
					+ "src/com/logserver/model/"
					+"ProcessCode.java");
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				out.write(strSB.toString().getBytes("utf8"));
				out.flush();
				out.close();
				System.out.println("====生成日志类"
						+ "ProcessCode.java 成功===");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public void createGetLog(){
		StringBuilder strSB = new StringBuilder();
		 strSB.append("package com.logserver.manager;\n");
		 strSB.append("import com.alibaba.fastjson.JSON;\n");
		 strSB.append("import com.logserver.model.BaseEntity;\n");
		 Collection<String> col = OrderConstance.tableNamesMap.values();
		 for(String str : col){
			 strSB.append("import com.logserver.model." + StringUtils.firstToUpper(str) + "Log;\n");
		 }
		 strSB.append("//由BuildTableNames.java自动生成\n");
		 strSB.append("public class GetLog {\n");
		 strSB.append("\tpublic BaseEntity getLogEntity(String jsonData,int type){\n");
		 strSB.append("\t\tBaseEntity log = null;\n");
		 strSB.append("\t\tswitch(type){\n");
		 Iterator<Integer> keys = OrderConstance.tableNamesMap.keySet().iterator();
		 while(keys.hasNext()){
			 int key = keys.next();
			 strSB.append("\t\t\tcase " + key + " :\n");
			 strSB.append("\t\t\tlog = ("+ StringUtils.firstToUpper(OrderConstance.tableNamesMap.get(key))+"Log)JSON.parseObject(jsonData, "+ StringUtils.firstToUpper(OrderConstance.tableNamesMap.get(key))+"Log.class" +");\n");
			 strSB.append("\t\t\tbreak;\n");
		 }
		 strSB.append("\t\t}\n");
		 strSB.append("\t\treturn log;\n");
		 strSB.append("\t}\n");
		 strSB.append("}");
		// 生成文件
			File file = new File(System.getProperty("user.dir")
					+ "/"
					+ "src/com/logserver/manager/"
					+"GetLog.java");
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				out.write(strSB.toString().getBytes("utf8"));
				out.flush();
				out.close();
				System.out.println("====生成日志类"
						+ "GetLog.java 成功===");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public static void main(String[] args) {

		BuildTableNames build = new BuildTableNames();
		build.build();
	}
}
