package tcg.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
public class MailSender {
	private Logger logger = LoggerFactory.getLogger(MailSender.class);

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
	@Value("${mail.admin}")
	private String adminMail;

	private List<Object[]> noPriceFound = new ArrayList<>();

	public void send(String email, String subjet, String content, Object... params)
			throws MessagingException, IOException {
		String fileName = content + '_' + LocaleContextHolder.getLocale() + ".mail";
		process(email, subjet, fileName, params);

	}

	public void sendNoPriceFound(Object... params) {
		noPriceFound.add(params);
	}

	@Scheduled(fixedDelay = 60 * 60 * 1000)
	public void sendPending() {
		if (!noPriceFound.isEmpty()) {
			String content = noPriceFound.stream()
					.map(ps -> ps[0] + " - " + ps[1] + " (" + ps[2] + ") / " + ps[3])
					.collect(Collectors.reducing("", (a, b) -> a + "\n" + b));
			noPriceFound.clear();
			try {
				process(adminMail, "No price Found", "adm_no_price_found.mail", content);
			} catch (MessagingException | IOException e) {
				logger.error("can't send admin mail " + e);
			}
		}
	}

	private void process(String email, String subjet, String fileName, Object... params)
			throws MessagingException, AddressException, IOException {
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
		message.setText(read(fileName, params));

		Transport.send(message);
	}

	private String read(String mail, Object[] params) throws IOException {
		StringBuilder message = new StringBuilder();
		try (InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(mail);
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
