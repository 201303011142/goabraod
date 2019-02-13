package com.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import com.base.entity.Email;


public class MailUtil {
	public static void sendMails(String recipients,String codes,String person){ 
		Logger logger = Logger.getLogger(MailUtil.class); 
		logger.info("开始发送邮件！"); 
		Email mail=new Email(); 
		                //发件人的邮箱地址（要完整），会显示在收件人的邮件中 
		mail.setSender("travbao@163.com"); 
		                //发件人登录邮箱的账号（@符合前面的部分） 
		mail.setUserName("travbao"); 
		                //下面填的是邮箱客户端授权码，切忌：邮箱务必要开启（POP3/SMTP服务） 
		mail.setPassword("travbao888"); 
		try{ 
			//截取用户名
			String userName = recipients.split("@")[0];
			//创建邮件对象 
			Session session=null; 
			Properties props = new Properties (); 
			                        //此处为发送方邮件服务器地址，要根据邮箱的不同需要自行设置 
			props.put("mail.smtp.host", "smtp.163.com"); 
			props.setProperty("mail.transport.protocol", "smtp"); 
			                        //SMTP端口号 
			props.put("mail.smtp.port", "465"); 
			                        //设置成需要邮件服务器认证 
			props.put("mail.smtp.auth","true"); 
			props.put("mail.smtp.starttls.enable","true"); 
			props.put("mail.smtp.starttls.required","true"); 
			props.put("mail.smtp..socketFactory.port","465"); 
			props.put("mail.smtp..socketFactory.class","true"); 
			props.put("mail.smtp..socketFactory.fallback","false"); 
			props.put("mail.debug","true"); 
			session=Session.getInstance(props); 
			session.setDebug(true); 
			Message message = new MimeMessage(session); 
			                        // 设置发件人 
			message.setFrom(new InternetAddress(mail.getSender())); 
			                        // 设置收件人 
			message.addRecipient(RecipientType.TO, new InternetAddress(recipients)); 
			                        // 设置标题 
			message.setSubject("落地签批文申请："); 
			                        //邮件内容，根据自己需要自行制作模板 
			message.setContent("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title></title><style>"+
			"*{padding: 0;margin:0;list-style: none;}"+
			".ww{width:700px;margin:0 auto;height:310px;border:1px solid #ccc;padding:48px 0 0 30px;}"+
			".tit{font-size:16px;color:#333333;font-weight: bold;margin-bottom: 35px;}"+
			".content{font-size:16px;color:#333333;}"+
			".content>.h{margin:19px 0 98px 0;overflow: hidden;}"+
			".content>.h>span{float:left;}"+
			".content>.h>.NUE{color:#ff9900;font-weight: bold;font-size:22px;float:left;}"+
			".content>.h>.k{margin:3px 0 0 10px;}"+
			".time{margin:19px 0 0 0;}"+
			"</style></head><body><div class=\"ww\">"+
			"<div class=\"tit\">尊敬的"+userName+"用户:</div>"+
			"<div class=\"content\">"+
			"<p>您好!感谢您使用出国宝服务，订单号码为:</p>"+
			"<p class=\"h\"><span class=\"NUE\">"+codes+"</span><span class=\"k\">的用户要办理</span><span class=\"NUE\">"+person+"</span><span class=\"k\">人的落地签批文。</span></p>"+
			"</div>"+
			"<div>出国宝服务团队</div>"+
			"<div class=\"time\">"+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())+"</div>"+
			"</div></body></html>", "text/html;charset=utf-8"); 
			//发送邮件 
			Transport transport=session.getTransport("smtp"); 
			transport.connect("smtp.163.com",mail.getUserName(),mail.getPassword());//以smtp方式登录邮箱 
			                        //发送邮件,其中第二个参数是所有已设好的收件人地址 
			transport.sendMessage(message,message.getAllRecipients()); 
			transport.close(); 
		}catch(Exception e){ 
		e.printStackTrace(); 
		} 
	} 
	
	public static void sendMails1(String recipients,String codes,String person){ 
		Logger logger = Logger.getLogger(MailUtil.class); 
		logger.info("开始发送邮件！"); 
		Email mail=new Email(); 
		                //发件人的邮箱地址（要完整），会显示在收件人的邮件中 
		mail.setSender("travbao@163.com"); 
		                //发件人登录邮箱的账号（@符合前面的部分） 
		mail.setUserName("travbao"); 
		                //下面填的是邮箱客户端授权码，切忌：邮箱务必要开启（POP3/SMTP服务） 
		mail.setPassword("travbao888"); 
		try{ 
			//截取用户名
			String userName = recipients.split("@")[0];
			//创建邮件对象 
			Session session=null; 
			Properties props = new Properties (); 
			                        //此处为发送方邮件服务器地址，要根据邮箱的不同需要自行设置 
			props.put("mail.smtp.host", "smtp.163.com"); 
			props.setProperty("mail.transport.protocol", "smtp"); 
			                        //SMTP端口号 
			props.put("mail.smtp.port", "465"); 
			                        //设置成需要邮件服务器认证 
			props.put("mail.smtp.auth","true"); 
			props.put("mail.smtp.starttls.enable","true"); 
			props.put("mail.smtp.starttls.required","true"); 
			props.put("mail.smtp..socketFactory.port","465"); 
			props.put("mail.smtp..socketFactory.class","true"); 
			props.put("mail.smtp..socketFactory.fallback","false"); 
			props.put("mail.debug","true"); 
			session=Session.getInstance(props); 
			session.setDebug(true); 
			Message message = new MimeMessage(session); 
			                        // 设置发件人 
			message.setFrom(new InternetAddress(mail.getSender())); 
			                        // 设置收件人 
			message.addRecipient(RecipientType.TO, new InternetAddress(recipients)); 
			                        // 设置标题 
			message.setSubject("落地签批文申请："); 
			                        //邮件内容，根据自己需要自行制作模板 
			message.setContent("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title></title><style>"+
			"*{padding: 0;margin:0;list-style: none;}"+
			".ww{width:700px;margin:0 auto;height:310px;border:1px solid #ccc;padding:48px 0 0 30px;}"+
			".tit{font-size:16px;color:#333333;font-weight: bold;margin-bottom: 35px;}"+
			".content{font-size:16px;color:#333333;}"+
			".content>.h{margin:19px 0 98px 0;overflow: hidden;}"+
			".content>.h>span{float:left;}"+
			".content>.h>.NUE{color:#ff9900;font-weight: bold;font-size:22px;float:left;}"+
			".content>.h>.k{margin:3px 0 0 10px;}"+
			".time{margin:19px 0 0 0;}"+
			"</style></head><body><div class=\"ww\">"+
			"<div class=\"tit\">尊敬的"+userName+"用户:</div>"+
			"<div class=\"content\">"+
			"<p>您好!感谢您使用出国宝服务，订单号码为:</p>"+
			"<p class=\"h\"><span class=\"NUE\">"+codes+"</span><span class=\"k\">的用户要办理</span><span class=\"NUE\">"+person+"</span><span class=\"k\">人的境外综合旅行险。</span></p>"+
			"</div>"+
			"<div>出国宝服务团队</div>"+
			"<div class=\"time\">"+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())+"</div>"+
			"</div></body></html>", "text/html;charset=utf-8"); 
			//发送邮件 
			Transport transport=session.getTransport("smtp"); 
			transport.connect("smtp.163.com",mail.getUserName(),mail.getPassword());//以smtp方式登录邮箱 
			                        //发送邮件,其中第二个参数是所有已设好的收件人地址 
			transport.sendMessage(message,message.getAllRecipients()); 
			transport.close(); 
		}catch(Exception e){ 
		e.printStackTrace(); 
		} 
	} 
	
	
	@SuppressWarnings("deprecation")
	public static void sendCommonMail(String toMailAddr,String ccMailAddr, String codes, String person) {
			HtmlEmail hemail = new HtmlEmail();
			try {
			String [] tos = null;
			if(toMailAddr.contains(",")){
			String[] split = toMailAddr.split(",");
			tos = new String[split.length];
			for (int i = 0; i < split.length; i++) {
			tos[i] = split[i];
			}
			}else{
			tos = new String[1];
			tos[0]=toMailAddr;

			}
			//需要开区ssl协议
			hemail.setSSL(true);
			hemail.setHostName("smtp.163.com");
			//截取用户名
//			String userName = toMailAddr.split("@")[0];
			//端口号
			hemail.setSmtpPort(465);
			//科目
			String subject = "落地签批文申请：";
			//内容
			String message = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title></title><style>"+
					"*{padding: 0;margin:0;list-style: none;}"+
					".ww{width:700px;margin:0 auto;height:310px;border:1px solid #ccc;padding:48px 0 0 30px;}"+
					".tit{font-size:16px;color:#333333;font-weight: bold;margin-bottom: 35px;}"+
					".content{font-size:16px;color:#333333;}"+
					".content>.h{margin:19px 0 98px 0;overflow: hidden;}"+
					".content>.h>span{float:left;}"+
					".content>.h>.NUE{color:#ff9900;font-weight: bold;font-size:22px;float:left;}"+
					".content>.h>.k{margin:3px 0 0 10px;}"+
					".time{margin:19px 0 0 0;}"+
					"</style></head><body><div class=\"ww\">"+
					"<div class=\"tit\">落地签批文申请:</div>"+
					"<div class=\"content\">"+
					"<p>您好!感谢您使用出国宝服务，订单号码为:</p>"+
					"<p class=\"h\"><span class=\"NUE\">"+codes+"</span><span class=\"k\">的用户要办理</span><span class=\"NUE\">"+person+"</span><span class=\"k\">人的落地签批文。</span></p>"+
					"</div>"+
					"<div>出国宝服务团队</div>"+
					"<div class=\"time\">"+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())+"</div>"+
					"</div></body></html>";

			hemail.setCharset("UTF-8");
			hemail.addTo(tos);
			hemail.addCc(ccMailAddr);
			hemail.setFrom("travbao@163.com", "travbao");
			hemail.setAuthentication("travbao@163.com", "travbao888");
			hemail.setSubject(subject);
			hemail.setMsg(message);
			hemail.send();
//			log.info("email send true!");
			} catch (Exception e) {
			e.printStackTrace();
//			log.error("email send error!");
			}


			}
	
	@SuppressWarnings("deprecation")
	public static void sendCommonMail1(String toMailAddr,String ccMailAddr, String codes, String person) {
			HtmlEmail hemail = new HtmlEmail();
			try {
			String [] tos = null;
			if(toMailAddr.contains(",")){
			String[] split = toMailAddr.split(",");
			tos = new String[split.length];
			for (int i = 0; i < split.length; i++) {
			tos[i] = split[i];
			}
			}else{
			tos = new String[1];
			tos[0]=toMailAddr;

			}
			//需要开区ssl协议
			hemail.setSSL(true);
			hemail.setHostName("smtp.163.com");
			//截取用户名
//			String userName = toMailAddr.split("@")[0];
			//端口号
			hemail.setSmtpPort(465);
			//科目
			String subject = "8天境外综合旅行险申请：";
			//内容
			String message = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title></title><style>"+
					"*{padding: 0;margin:0;list-style: none;}"+
					".ww{width:700px;margin:0 auto;height:310px;border:1px solid #ccc;padding:48px 0 0 30px;}"+
					".tit{font-size:16px;color:#333333;font-weight: bold;margin-bottom: 35px;}"+
					".content{font-size:16px;color:#333333;}"+
					".content>.h{margin:19px 0 98px 0;overflow: hidden;}"+
					".content>.h>span{float:left;}"+
					".content>.h>.NUE{color:#ff9900;font-weight: bold;font-size:22px;float:left;}"+
					".content>.h>.k{margin:3px 0 0 10px;}"+
					".time{margin:19px 0 0 0;}"+
					"</style></head><body><div class=\"ww\">"+
					"<div class=\"tit\">境外综合旅行险业务:</div>"+
					"<div class=\"content\">"+
					"<p>您好!感谢您使用出国宝服务，订单号码为:</p>"+
					"<p class=\"h\"><span class=\"NUE\">"+codes+"</span><span class=\"k\">的用户要办理</span><span class=\"NUE\">"+person+"</span><span class=\"k\">人的境外综合旅行险。</span></p>"+
					"</div>"+
					"<div>出国宝服务团队</div>"+
					"<div class=\"time\">"+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())+"</div>"+
					"</div></body></html>";

			hemail.setCharset("UTF-8");
			hemail.addTo(tos);
			hemail.addCc(ccMailAddr);
			hemail.setFrom("travbao@163.com", "travbao");
			hemail.setAuthentication("travbao@163.com", "travbao888");
			hemail.setSubject(subject);
			hemail.setMsg(message);
			hemail.send();
//			log.info("email send true!");
			} catch (Exception e) {
			e.printStackTrace();
//			log.error("email send error!");
			}
	}

	@SuppressWarnings("deprecation")
	public static void sendCommonMail2(String toMailAddr,String ccMailAddr, String codes, String person) {
		HtmlEmail hemail = new HtmlEmail();
		try {
			String [] tos = null;
			if(toMailAddr.contains(",")){
				String[] split = toMailAddr.split(",");
				tos = new String[split.length];
				for (int i = 0; i < split.length; i++) {
					tos[i] = split[i];
				}
			}else{
				tos = new String[1];
				tos[0]=toMailAddr;

			}
			//需要开区ssl协议
			hemail.setSSL(true);
			hemail.setHostName("smtp.163.com");
			//截取用户名
//			String userName = toMailAddr.split("@")[0];
			//端口号
			hemail.setSmtpPort(465);
			//科目
			String subject = "15天境外综合旅行险申请：";
			//内容
			String message = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title></title><style>"+
					"*{padding: 0;margin:0;list-style: none;}"+
					".ww{width:700px;margin:0 auto;height:310px;border:1px solid #ccc;padding:48px 0 0 30px;}"+
					".tit{font-size:16px;color:#333333;font-weight: bold;margin-bottom: 35px;}"+
					".content{font-size:16px;color:#333333;}"+
					".content>.h{margin:19px 0 98px 0;overflow: hidden;}"+
					".content>.h>span{float:left;}"+
					".content>.h>.NUE{color:#ff9900;font-weight: bold;font-size:22px;float:left;}"+
					".content>.h>.k{margin:3px 0 0 10px;}"+
					".time{margin:19px 0 0 0;}"+
					"</style></head><body><div class=\"ww\">"+
					"<div class=\"tit\">境外综合旅行险业务:</div>"+
					"<div class=\"content\">"+
					"<p>您好!感谢您使用出国宝服务，订单号码为:</p>"+
					"<p class=\"h\"><span class=\"NUE\">"+codes+"</span><span class=\"k\">的用户要办理</span><span class=\"NUE\">"+person+"</span><span class=\"k\">人的境外综合旅行险。</span></p>"+
					"</div>"+
					"<div>出国宝服务团队</div>"+
					"<div class=\"time\">"+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())+"</div>"+
					"</div></body></html>";

			hemail.setCharset("UTF-8");
			hemail.addTo(tos);
			hemail.addCc(ccMailAddr);
			hemail.setFrom("travbao@163.com", "travbao");
			hemail.setAuthentication("travbao@163.com", "travbao888");
			hemail.setSubject(subject);
			hemail.setMsg(message);
			hemail.send();
//			log.info("email send true!");
		} catch (Exception e) {
			e.printStackTrace();
//			log.error("email send error!");
		}
	}
}
