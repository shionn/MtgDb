package tcg.security;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

	@Value("${smtp.host}")
	private String host;
	@Value("${smtp.port}")
	private String port;
	@Value("${smtp.user}")
	private String user;
	@Value("${smtp.pass}")
	private String pass;
	@Value("${smtp.from}")
	private String from;
	@Value("${smtp.replyto}")
	private String replyto;

	public void send(String email, String subjet, String content) throws MessagingException {
		Properties props = new Properties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setReplyTo(new InternetAddress[] { new InternetAddress(replyto) });
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
		message.setSubject(subjet);
		message.setText(content);

		Transport.send(message);

	}

}
