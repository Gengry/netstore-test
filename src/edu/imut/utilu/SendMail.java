package edu.imut.utilu;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import edu.imut.domain.Customer;

public class SendMail extends Thread {
	private Customer customer;
	public SendMail(Customer customer) {
		this.customer = customer;
	}
	@Override
	public void run() {
		Properties props = new Properties();
		
		Session session = Session.getInstance(props);
		MimeMessage message = new MimeMessage(session);
	}

}
