package tcg.security;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	@Autowired
	@Value("${server.host}")
	private String host;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("sign/signin");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUp() {
		return new ModelAndView("sign/signup");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("confirm") String confirm,
			RedirectAttributes attr, HttpServletRequest request)
			throws MessagingException, IOException {
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
		mailSender.send(email, "Welcome", "welcome", activationLink(email, request));
		session.commit();
		return "redirect:/signup/confirm/";
	}

	@RequestMapping(value = "/signup/confirm/", method = RequestMethod.GET)
	public ModelAndView signUpConfirm() {
		return new ModelAndView("sign/signup-confirm");
	}

	@RequestMapping(value = "/signup/activate/{email}/{key}", method = RequestMethod.GET)
	public ModelAndView signUpActivate(@PathVariable("email") String email,
			@PathVariable("key") String key) {
		if (StringUtils.equals(encoder.encode(email), key)
				&& session.getMapper(SignInDao.class).activate(email) == 1) {
			session.commit();
			return new ModelAndView("sign/signup-activate-success");
		}
		return new ModelAndView("sign/fail");
	}

	@RequestMapping(value = "/recover", method = RequestMethod.GET)
	public ModelAndView recover() {
		return new ModelAndView("sign/recover-step1");
	}

	@RequestMapping(value = "/recover", method = RequestMethod.POST)
	public ModelAndView recover(@RequestParam("email") String email, RedirectAttributes attr,
			HttpServletRequest request) throws MessagingException, IOException {
		SignInDao dao = session.getMapper(SignInDao.class);
		if (!dao.exist(email)) {
			attr.addFlashAttribute("email", email);
			attr.addFlashAttribute("error", SignUpError.emailNotUsed);
			return new ModelAndView("redirect:/recover");
		}
		mailSender.send(email, "Password reseting", "recover", recoverLink(email, request));
		return new ModelAndView("sign/recover-step2");
	}

	@RequestMapping(value = "/recover/{email}/{key}", method = RequestMethod.GET)
	public ModelAndView recover(@PathVariable("email") String email,
			@PathVariable("key") String key) {
		SignInDao dao = session.getMapper(SignInDao.class);
		if (StringUtils.equals(encoder.encode(email), key) && dao.exist(email)) {
			return new ModelAndView("sign/recover-step3").addObject("email", email).addObject("key",
					key);
		}
		return new ModelAndView("sign/fail");
	}

	@RequestMapping(value = "/recover/{email}/{key}", method = RequestMethod.POST)
	public ModelAndView recover(@PathVariable("email") String email,
			@PathVariable("key") String key, @RequestParam("password") String password,
			@RequestParam("confirm") String confirm, RedirectAttributes attr,
			HttpServletRequest request) {
		SignInDao dao = session.getMapper(SignInDao.class);
		if (!dao.exist(email) || !StringUtils.equals(encoder.encode(email), key)) {
			return new ModelAndView("sign/fail");
		}
		if (!StringUtils.equals(password, confirm)) {
			attr.addFlashAttribute("email", email);
			attr.addFlashAttribute("key", key);
			attr.addFlashAttribute("error", SignUpError.passwordDontMatch);
			return new ModelAndView("redirect:/recover/" + email + "/" + key);
		}
		dao.updatePassword(email, password);
		session.commit();
		return new ModelAndView("sign/recover-step4");
	}

	private String activationLink(String email, HttpServletRequest request) {
		return "http://" + host + request.getContextPath() + "/signup/activate/" + email + "/"
				+ encoder.encode(email);
	}

	private String recoverLink(String email, HttpServletRequest request) {
		return "http://" + host + request.getContextPath() + "/recover/" + email + "/"
				+ encoder.encode(email);
	}

}
