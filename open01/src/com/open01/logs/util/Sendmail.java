package com.open01.logs.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
* @ClassName: Sendmail
* @Description: 发送Email
*
*/ 
public class Sendmail {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	String title="test";
    	String content="1223";
    	String reciver = "xianghong.dai@open01.com";
    	sendSimpleMail(title,content,reciver);
     
    }
    
    /**
    * @Method: createSimpleMail
    * @Description: 创建一封只包含文本的邮件
    * @Anthor:孤傲苍狼
    *
    * @param session
    * @return
    * @throws Exception
    */ 
    public static void  createSimpleMail(String title, String content, String reciver) throws Exception {
    
        
        Properties prop = new Properties();
        prop.setProperty("mail.host", Consant.MAIL_HOST);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        // 设置环境信息  
        Session session = Session.getInstance(prop);  
        
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人 
        message.setFrom(new InternetAddress(Consant.MAIL_SENDER));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(reciver));
        //邮件的标题
        message.setSubject(title);
        //邮件的文本内容
        message.setContent(content, "text/html;charset=UTF-8");
       
        
        //1、开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(Consant.MAIL_HOST, Consant.MAIL_SEND_USERNAME, Consant.MAIL_SEND_PASSWORD);
        //4、创建邮件
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
    
    public static void sendSimpleMail(String title, String content, String reciver)  throws Exception {
    	createSimpleMail(title,content,reciver);
    }
}