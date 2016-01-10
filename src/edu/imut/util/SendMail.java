package edu.imut.util;

import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.imut.domain.Customer;

public class SendMail extends Thread {
	private Customer customer;

	public SendMail(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void run() {
		try {
			/*Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");// 指定邮件的发送协议，参数是规范规定的
			props.setProperty("mail.host", "smtp.163.com");// 指定发件服务器的地址，参数是规范规定的
			props.setProperty("mail.stmp.auth", "true");// 请求服务器进行身份认证，参数是具体JavaMail实现规定的
			Session session = Session.getInstance(props);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress("young_geng@163.com"));
			message.addRecipients(MimeMessage.RecipientType.TO, customer.getEmail());
			message.setSubject("来自网上书店的激活邮件");

			message.setContent(
					"亲爱的"
							+ customer.getName()
							+ "<br/>感谢您注册成为我们的会员，请猛戳下面激活您的账户。<br/><a href='http://localhost:8080/day23_00_netstore/servlet/ClientServlet?op=active&code="
							+ customer.getCode() + "'>戳这里</a><br/>本邮件由系统自动发出，请不要直接回复。",
					"text/html;charset=UTF-8");
			message.saveChanges();*/
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");//指定邮件发送的协议，参数是规范规定的
			props.setProperty("mail.host", "smtp.qq.com");//指定发件服务器的地址，参数是规范规定的
//		props.setProperty("mail.debug", "true");//邮件发送的调试模式，参数是规范规定的
			props.setProperty("mail.smtp.auth", "true");//请求服务器进行身份认证。参数与具体的JavaMail实现有关
			//这两是后加的  不加不行，= 默认port是25   socketfactory不知道是什么
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			
			Session session = Session.getInstance(props);
			session.setDebug(true);
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress("545620701@qq.com"));
			message.setRecipients(Message.RecipientType.TO, customer.getEmail());
			message.setSubject("来自XX网站的激活邮件");
			
			message.setContent("亲爱的"+customer.getName()+"<br/>感谢您注册成为我们的会员，请猛戳下面激活您的账户。<br/><a href='http://localhost:8080/day23_00_netstore/servlet/ClientServlet?op=active&code="+customer.getCode()+"'>戳这里</a><br/>本邮件由系统自动发出，请不要直接回复。", "text/html;charset=UTF-8");
			message.saveChanges();
			
			Transport ts = session.getTransport();
			ts.connect("545620701", "esilvululmfibdfh");
			//esilvululmfibdfh
			ts.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

/*
 * 
 * 获得邮件会话属性

public Properties getProperties() {
Properties p = new Properties();
Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
p.put("mail.smtp.socketFactory.fallback", "false");
p.put("mail.smtp.starttls.enable", "true");
p.put("mail.smtp.host", this.mailServerHost);
p.put("mail.smtp.port", this.mailServerPort);
p.put("mail.smtp.auth", validate ? "true" : "false");
p.put("mail.mime.address.strict", "false");
return p;
}
这样配置可以
*/
