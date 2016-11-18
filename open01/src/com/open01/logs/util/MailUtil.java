package com.open01.logs.util;

import java.util.logging.Logger;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.open01.logs.model.Mail;
import com.open01.logs.web.DefaultPlainBean;

/**
 * 邮件发送工具实现类
 * 
 * @author shadow
 * @create 2013/07/12
 */
public class MailUtil {

	private static Logger logger = Logger.getLogger(DefaultPlainBean.class.getName());

	public static boolean send(Mail mail) {
		// 发送email
		HtmlEmail email = new HtmlEmail();
		try {
			// 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
			email.setHostName(mail.getHost());
			// 字符编码集的设置
			email.setCharset(Mail.ENCODEING);
			// 收件人的邮箱
			email.addTo(mail.getReceiver());
			// 发送人的邮箱
			email.setFrom(mail.getSender(), mail.getName());
			// 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
			email.setAuthentication(mail.getUsername(), mail.getPassword());
			// 要发送的邮件主题
			email.setSubject(mail.getSubject());
			// 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
			email.setMsg(mail.getMessage());
			// 发送
			email.send();
			logger.info(mail.getSender() + " 发送邮件到 " + mail.getReceiver());
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			logger.info(mail.getSender() + " 发送邮件到 " + mail.getReceiver() + " 失败");
			return false;
		}
	}
	
	public static boolean sendMail(String receiver, String title, String content){
		Mail mail = new Mail();
		mail.setHost(Consant.MAIL_HOST); // 设置邮件服务器,如果不用163的,自己找找看相关的
		mail.setSender(Consant.MAIL_SENDER);
		mail.setReceiver(receiver); // 接收人
		mail.setUsername(Consant.MAIL_SEND_USERNAME); // 登录账号,一般都是和邮箱名一样吧
		mail.setPassword(Consant.MAIL_SEND_PASSWORD); // 发件人邮箱的登录密码
		mail.setSubject(title);
		mail.setMessage(content);
		return MailUtil.send(mail);
	}
	public static void main(String[] args) {
		
	}
}