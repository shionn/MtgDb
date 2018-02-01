package tcg.test;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestPageController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView testPage() {
		Locale locale = LocaleContextHolder.getLocale();
		return new ModelAndView("test").addObject("locale", locale);
	}

}
