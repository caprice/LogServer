package com.logserver.utils;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.logserver.model.common.ServerConfigModel;

public class MailUtils {

	
/**
 * 
 * author: wang guang shuai
 * 2014-9-26
 * @param content 邮件内容
 * @param title   邮件标题
 */
	public static  void send(String title,String content) {
		 ServerConfigModel config = InitConfig.getInstance();
		 String from = config.getFrom(); // 发件人地址
		 String to = config.getTo(); // 收件人地址
		 String user = config.getUsername(); // 用户名
		 String pwd = config.getPassword(); // 密码
		 String host = "smtp.163.com";
		 Properties props = new Properties();

		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");

		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);

		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);

		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(from));
			ServerConfigModel configMode = InitConfig.getInstance();
			// 加载标题
			message.setSubject(configMode.getGame() + title);

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(content);
			multipart.addBodyPart(contentPart);
			// 添加附件
			/*
			 * BodyPart messageBodyPart= new MimeBodyPart(); DataSource source =
			 * new FileDataSource(affix); //添加附件的内容
			 * messageBodyPart.setDataHandler(new DataHandler(source));
			 * //添加附件的标题 //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
			 * sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
			 * messageBodyPart
			 * .setFileName("=?GBK?B?"+enc.encode(affixName.getBytes())+"?=");
			 * multipart.addBodyPart(messageBodyPart);
			 */

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			String[] tos = to.split(",");
			// 连接服务器的邮箱
			transport.connect(host, user, pwd);
			for(String des : tos){
				// 加载收件人地址
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(
						des));
				// 把邮件发送出去
				transport.sendMessage(message, message.getAllRecipients());
			}
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		MailUtils.send("测试邮件", "aaaaaaaaaaaaaaaaaaaaaa");
	}
}
