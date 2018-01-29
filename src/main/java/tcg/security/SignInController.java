package tcg.security;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tcg.db.dao.SignInDao;

@Controller
public class SignInController {

	@Autowired
	private SqlSession session;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private MailSender mailSender;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("signin");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUp() {
		return new ModelAndView("signup");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("confirm") String confirm,
			RedirectAttributes attr) throws MessagingException {
		SignInDao dao = session.getMapper(SignInDao.class);
		if (dao.exist(email)) {
			attr.addFlashAttribute("email", email);
			attr.addFlashAttribute("error", SignUpError.emailAlreadyTaken);
			return "redirect:/signup";
		}
		if (!StringUtils.equals(password, confirm)) {
			attr.addFlashAttribute("email", email);
			attr.addFlashAttribute("error", SignUpError.passwordDontMatch);
			return "redirect:/signup";
		}
		dao.register(email, encoder.encode(password));
		mailSender.send(email, "Welcome", "Contenu à définir");
		return "redirect:/signup/confirm/" + email;
	}

	@RequestMapping(value = "/signup/confirm/{email}", method = RequestMethod.GET)
	public ModelAndView signUpConfirm(@PathVariable("email") String email) {


		return new ModelAndView("signup-confirm");
	}
}
