package tcg.security;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class User {

	private int currentDeck;
	private String currentDeckName;

	public String getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return null;
		}
		return auth.getName();
	}

	public int getCurrentDeck() {
		return currentDeck;
	}

	public void setCurrentDeck(int currentDeck) {
		this.currentDeck = currentDeck;
	}

	public String getCurrentDeckName() {
		return currentDeckName;
	}

	public void setCurrentDeckName(String currentDeckName) {
		this.currentDeckName = currentDeckName;
	}

	public Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

	public boolean isAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getAuthorities().stream().filter(a -> a.getAuthority().equals("ROLE_ADMIN"))
				.count() > 0;
	}

	public boolean isLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return !(auth instanceof AnonymousAuthenticationToken);
	}

}
