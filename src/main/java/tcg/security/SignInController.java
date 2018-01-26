package tcg.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignInController {

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("signin");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUp() {
		return new ModelAndView("signup");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signUp(@RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("confirm") String confirm) {
		return new ModelAndView("signup");
	}
}
