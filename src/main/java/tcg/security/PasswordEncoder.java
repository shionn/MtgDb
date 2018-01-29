package tcg.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
	@Autowired
	@Value("${auth.salt}")
	private String salt = "salt";

	private Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);

	public String encode(UsernamePasswordAuthenticationToken token) {
		return encode(token.getCredentials());
	}

	public String encode(Object pass) {
		String encoded = DigestUtils.sha512Hex(pass + salt);
		logger.info(encoded);
		return encoded;
	}

}
