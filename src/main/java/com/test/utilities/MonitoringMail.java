package com.test.utilities;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.test.utilities.TestConfig;

public class MonitoringMail {
	public void sendMail(String mailServer, String from, String[] to, String subject, String messageBody) {

		Properties prop = new Properties();

		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(TestConfig.from, TestConfig.password);
			}
		});

		try {
			Transport bus = session.getTransport("smtp");
			bus.connect();

			Message message = new MimeMessage(session);

			// X-Priority values are generally numbers like 1 (for highest priority),
			// 3(normal) and 5 (lowest)
			message.addHeader("X_Priority", "1");
			message.setFrom(new InternetAddress(from));
			InternetAddress addressTo[] = new InternetAddress[to.length];

			for (int i = 0; i < to.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}

			message.setRecipients(Message.RecipientType.TO, addressTo);
			message.setSubject(subject);

			BodyPart body = new MimeBodyPart();
			body.setContent(messageBody, "text/html");

			// BodyPart attachment = new MimeBodyPart();
			// DataSource source = new FileDataSource(attachmentPath);
			// attachment.setDataHandler(new DataHandler(source));
			// attachment.setFileName(attachmentName);
			
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(body);
			//multipart.addBodyPart(attachment);
			message.setContent(multipart);
			
			Transport.send(message);
			System.out.println("Mail successfully sent");
			bus.close();

		} catch (MessagingException m) {
			m.printStackTrace();
		}
	}

}
