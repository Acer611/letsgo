package com.umessage.letsgo.core.utils;

import com.umessage.letsgo.core.config.constant.ConfigConstant;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailUtil {
	private static String host;  // 主机
    private static String username;  // 用户名
    private static String password;  // 密码
    private static String auth;	//是否身份验证
	private static String from;	//发件人
    private static Session session;
    private static MimeMessage message;
    
    static {
		host = "smtp.exmail.qq.com";
		from = ConfigConstant.MAIL_SENDER;
    	username = ConfigConstant.MAIL_USERNAME;
    	password = ConfigConstant.MAIL_PASSWORD;
    	auth = "true";

    	// 构建环境属性信息
    	Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", auth);
		//props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.port", "25");
	    // 构建授权信息，用于进行SMTP进行身份验证
	    Authenticator authenticator = new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	    };
	    // 使用环境属性和授权信息，创建邮件会话
	    session = Session.getInstance(props, authenticator);
		session.setDebug(true);
    }

	/**
	 * 发送纯文本的邮件
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param to 收件人列表
	 * @throws MessagingException
	 */
	public static void mail(String title, String content, String[] to) throws MessagingException {
	    // 创建邮件消息
	    message = new MimeMessage(session);
	    // 设置发件人
	    InternetAddress form = new InternetAddress(from);
	    message.setFrom(form);

	    // 设置收件人
	    String toList = getAddresseeList(to);
        InternetAddress[] iaToList = InternetAddress.parse(toList);
	    message.setRecipients(RecipientType.TO, iaToList);

	    // 设置抄送
	    //InternetAddress cc = new InternetAddress("");
	    //message.setRecipient(RecipientType.CC, cc);

	    // 设置密送，其他的收件人不能看到密送的邮件地址
	    //InternetAddress bcc = new InternetAddress("");
	    //message.setRecipient(RecipientType.CC, bcc);

	    // 设置邮件标题
	    message.setSubject(title);

	    // 设置邮件的内容体
	    message.setContent(content, "text/html;charset=UTF-8");

	    // 发送邮件
	    Transport.send(message);
	}
	
	/**
	 * 发送带有附件的文件
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param attachment 邮件附件
	 * @param to 收件人列表
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static void mailWithAttachment(String title, String content, File attachment, String[] to) throws MessagingException, UnsupportedEncodingException {
	    // 创建邮件消息
	    message = new MimeMessage(session);
	    // 设置发件人
	    InternetAddress form = new InternetAddress(username);
	    message.setFrom(form);

	    // 设置收件人
	    //String to[]={"liumh@yidingdong.cn","qinyue@yidingdong.cn","baiye@yidingdong.cn"};
	    String toList = getAddresseeList(to);  
        InternetAddress[] iaToList = InternetAddress.parse(toList);
	    message.setRecipients(RecipientType.TO, iaToList);
	    
	    /*InternetAddress to = new InternetAddress("gongww@yidingdong.cn");
	    message.setRecipient(RecipientType.TO, to);*/

	    // 设置抄送
	    //InternetAddress cc = new InternetAddress("");
	    //message.setRecipient(RecipientType.CC, cc);

	    // 设置密送，其他的收件人不能看到密送的邮件地址
	    //InternetAddress bcc = new InternetAddress("");
	    //message.setRecipient(RecipientType.CC, bcc);

	    // 设置邮件标题
	    message.setSubject(title);
	    
	    // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
	    Multipart multipart = new MimeMultipart();

	    // 设置邮件的内容体
	    BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(content, "text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);
	    
	    // 设置附件
	    if (attachment != null) {
	    	BodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            
            // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
            
            //MimeUtility.encodeWord可以避免文件名乱码
            attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
            multipart.addBodyPart(attachmentBodyPart);
		}
	    
	    message.setContent(multipart);

	    // 发送邮件
	    Transport.send(message);
	}
	
	/**
	 * 获取收件人列表
	 * @param mailArray
	 * @return
	 */
	private static String getAddresseeList(String[] mailArray){
        StringBuffer toList = new StringBuffer();  
        int length = mailArray.length;  
        if(mailArray!=null && length <2){  
             toList.append(mailArray[0]);  
        } else {  
        	for(int i=0;i<length;i++){  
        		toList.append(mailArray[i]);  
            	if(i!=(length-1)){  
                	toList.append(",");  
             	}
        	}  
        }  
        return toList.toString();
	}

	public static void main(String argv[]){
		String to[] = {"tonggh@umessage.com.cn"};
		try {
			mail("一封邮件Test", "邮件内容Test", to);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
