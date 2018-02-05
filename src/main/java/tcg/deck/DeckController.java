package tcg.deck;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.DeckDao;
import tcg.db.dbo.DeckType;
import tcg.security.User;

@Controller
@SessionScope
public class DeckController {

	@Autowired
	private SqlSession session;

	@Autowired
	private User user;

	@RequestMapping(value = "/d", method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("deck/list").addObject("types", DeckType.values())
				.addObject("decks", session.getMapper(DeckDao.class).readAll(user.getUser()));
	}

	@RequestMapping(value = "/d", method = RequestMethod.POST)
	public String create(@RequestParam("name") String name, @RequestParam("type") DeckType type) {
		session.getMapper(DeckDao.class).create(name, type, user.getUser());
		session.commit();
		return "redirect:/d";
	}

	@RequestMapping(value = "/d/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") int id) {
		return "redirect:/d/table/" + id;
	}

}
