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
			props.setProperty("mail.transport.protocol", "smtp");// ָ���ʼ��ķ���Э�飬�����ǹ淶�涨��
			props.setProperty("mail.host", "smtp.163.com");// ָ�������������ĵ�ַ�������ǹ淶�涨��
			props.setProperty("mail.stmp.auth", "true");// ������������������֤�������Ǿ���JavaMailʵ�ֹ涨��
			Session session = Session.getInstance(props);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress("young_geng@163.com"));
			message.addRecipients(MimeMessage.RecipientType.TO, customer.getEmail());
			message.setSubject("�����������ļ����ʼ�");

			message.setContent(
					"�װ���"
							+ customer.getName()
							+ "<br/>��л��ע���Ϊ���ǵĻ�Ա�����ʹ����漤�������˻���<br/><a href='http://localhost:8080/day23_00_netstore/servlet/ClientServlet?op=active&code="
							+ customer.getCode() + "'>������</a><br/>���ʼ���ϵͳ�Զ��������벻Ҫֱ�ӻظ���",
					"text/html;charset=UTF-8");
			message.saveChanges();*/
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");//ָ���ʼ����͵�Э�飬�����ǹ淶�涨��
			props.setProperty("mail.host", "smtp.qq.com");//ָ�������������ĵ�ַ�������ǹ淶�涨��
//		props.setProperty("mail.debug", "true");//�ʼ����͵ĵ���ģʽ�������ǹ淶�涨��
			props.setProperty("mail.smtp.auth", "true");//������������������֤������������JavaMailʵ���й�
			//�����Ǻ�ӵ�  ���Ӳ��У�= Ĭ��port��25   socketfactory��֪����ʲô
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			
			Session session = Session.getInstance(props);
			session.setDebug(true);
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress("545620701@qq.com"));
			message.setRecipients(Message.RecipientType.TO, customer.getEmail());
			message.setSubject("����XX��վ�ļ����ʼ�");
			
			message.setContent("�װ���"+customer.getName()+"<br/>��л��ע���Ϊ���ǵĻ�Ա�����ʹ����漤�������˻���<br/><a href='http://localhost:8080/day23_00_netstore/servlet/ClientServlet?op=active&code="+customer.getCode()+"'>������</a><br/>���ʼ���ϵͳ�Զ��������벻Ҫֱ�ӻظ���", "text/html;charset=UTF-8");
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
 * ����ʼ��Ự����

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
�������ÿ���
*/
