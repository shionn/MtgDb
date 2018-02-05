package tcg.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class User {

	private int currentDeck;

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

}
