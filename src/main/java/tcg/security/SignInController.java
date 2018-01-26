package tcg.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignInController {

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("signin");
	}

}
