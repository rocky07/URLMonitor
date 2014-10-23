package com.bourntec.URLMonitor;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail implements Runnable {
	private Map<String,String> mailConfig;
	private String messageBody;
	public static void sendMail(Map<String,String> mailConfig,String messageBody){
		System.out.println("Sending mail...");
	      String to = mailConfig.get("mailTo");
	      String from = mailConfig.get("mailFrom");
	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", "smtp.gmail.com");
	      props.put("mail.smtp.port", "587");	      
	      final String userName=mailConfig.get("emailUser");
	      final String password=mailConfig.get("emailPass");			
	      Session session = Session.getInstance(props,
	    		  new javax.mail.Authenticator() {
	    			@Override
					protected PasswordAuthentication getPasswordAuthentication() {
	    				return new PasswordAuthentication(userName,password);
	    			}
	    		  });
	      String[] recipients=to.split(",");
	      InternetAddress[] mailRecipients=new InternetAddress[recipients.length];
	      int count=0;
	      for (String string : recipients) {
	    	  try{
	    		  mailRecipients[count++]=new InternetAddress(string);
	    	  }catch(Exception e){
	    		  e.printStackTrace();
	    	  }
	      	}	

	      try{
	          MimeMessage message = new MimeMessage(session);
	          message.setFrom(new InternetAddress(from));
	          message.addRecipients(Message.RecipientType.TO,
	        		  mailRecipients);
	          message.setSubject(mailConfig.get("emailSubject"));
	          message.setContent(messageBody,"text/html" );	   
	          message.setSender(new InternetAddress(from));
	          message.setReplyTo(new javax.mail.Address[]
	                                                    {
	                                                        new javax.mail.internet.InternetAddress(from)
	                                                    });
	          
	          // Send message
	          Transport.send(message);
	          System.out.println("Sent message successfully....");
	       }catch (MessagingException mex) {
	          mex.printStackTrace();
	       }
	}

	@Override
	public void run() {
		sendMail(mailConfig,messageBody);		
	}
	public SendMail(Map<String,String> mailConfig,String messageBody){
		this.mailConfig=mailConfig;
		this.messageBody=messageBody;
	}
}
