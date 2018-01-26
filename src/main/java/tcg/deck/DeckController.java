package tcg.deck;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionScope
public class DeckController {

	@Autowired
	private SqlSession session;


	@RequestMapping(value = "/d", method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("deck");
	}

}
