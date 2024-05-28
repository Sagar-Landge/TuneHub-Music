package com.example.demo.services;
		
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
public boolean sendEmail(String subject, String message, String to) {

//rest of the code..
boolean f = false;

String from = "slandge593@gmail.com";

//Variable for gmail
String host ="smtp.gmail.com";

//get the system properties 
Properties properties = System.getProperties(); 
System.out.println("PROPERTIES " + properties);

properties.put("mail.smtp.host", "smtp.gmail.com");
properties.put("mail.smtp.port", "465");
properties.put("mail.username","slandge593@gmail.com");
properties.put("mail.password","kxro tffv ejxi othi");
properties.put("mail.smtp.auth", "true");
properties.put("mail.smtp.ssl.enable", "true");

Session session=Session.getInstance(properties,new Authenticator() {
	@Override
	 protected PasswordAuthentication getPasswordAuthentication() {

	return new PasswordAuthentication("slandge593@gmail.com", "kxro tffv ejxi othi");
}

});


session.setDebug(true);

MimeMessage m=new MimeMessage(session);

try {
	m.setFrom(from);
	
	m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	
	m.setSubject(subject);
	
//	m.setText(message);
	m.setContent(message,"text/html");
	
	Transport.send(m);
	
	System.out.println("sent success......");
	f=true;
}
catch(Exception e)
{
	e.printStackTrace();
}
return f;
}
}


