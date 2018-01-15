package tcg.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestPageController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String testPage() {
		return "test";
	}

}
