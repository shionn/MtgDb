package tcg.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
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

	public void send(String email, String subjet, String content, Object... params)
			throws MessagingException, IOException {
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
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		message.setSubject(subjet);
		message.setText(read(content, params));

		Transport.send(message);

	}

	private String read(String content, Object[] params) throws IOException {
		StringBuilder message = new StringBuilder();
		try (InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(content + LocaleContextHolder.getLocale() + ".mail");
				InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr)) {
			String line = br.readLine();
			while (line != null) {
				message.append(MessageFormat.format(line, params)).append('\n');
				line = br.readLine();
			}
		}
		return message.toString();
	}

}
