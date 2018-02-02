package tcg.deck;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.DeckDao;
import tcg.db.dbo.DeckType;

@Controller
@SessionScope
public class DeckController {

	@Autowired
	private SqlSession session;

	@Autowired
	@Qualifier("current-user")
	private String user;

	@RequestMapping(value = "/d", method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("deck/list").addObject("types", DeckType.values())
				.addObject("decks",
				session.getMapper(DeckDao.class).readAll(user));
	}

}
