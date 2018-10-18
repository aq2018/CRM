package com.test.crm.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtil {
	/**
	 * 
	 * @param code 验证码
	 * @param email 目标邮件
	 * @return
	 */
	public static boolean send(String code, String email ,String varTime){
		Properties props = new Properties();  
        // debug调试  
        props.setProperty("mail.debug", "false");  
        // 发送服务器需要身份验证  
        props.setProperty("mail.smtp.auth", "true");  
        // 设置邮件服务器主机名 使用163邮箱发送 
        props.setProperty("mail.host", "smtp.163.com");  
        // 发送邮件协议名称  
        props.setProperty("mail.transport.protocol", "smtp");  
        // 设置环境信息  
        Session session = Session.getInstance(props);  

        // 创建邮件对象  
        Message msg = new MimeMessage(session);  
        try {
        	
			msg.setSubject("修改密码");
			// 设置邮件内容 
			msg.setText("亲爱的用户：您的账号邮箱验证码为：" + code + "，此验证码有效时长" + varTime + "分钟，请勿转发他人 xxx网敬上");  
			// 设置发件人  
			msg.setFrom(new InternetAddress("anquan6@163.com"));  

			Transport transport = session.getTransport();  
			// 连接邮件服务器     xzbbrvnlsjpdbfei
			transport.connect("anquan6@163.com", "admin318");//不是登录密码，需要开启客户授权密码，生成授权码，此处填写授权码
			// 发送邮件  
			transport.sendMessage(msg, new Address[] {new InternetAddress(email)}); //目标地址，即接收邮件的邮箱地址
			// 关闭连接  
			transport.close();
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}  
		
	}  
}
