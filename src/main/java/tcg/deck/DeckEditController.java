package tcg.deck;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.DeckDao;
import tcg.db.dao.DeckEditDao;
import tcg.db.dbo.DeckType;
import tcg.security.User;

@Controller
public class DeckEditController {
	@Autowired
	private SqlSession session;
	@Autowired
	private User user;

	@RequestMapping(value = "/d/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") int id) {
		return new ModelAndView("deck/edit-modal")//
				.addObject("types", DeckType.values())//
				.addObject("deck", session.getMapper(DeckDao.class).readDeckBase(id));//
	}

	@RequestMapping(value = "/d/edit/{id}", method = RequestMethod.POST)
	public String edit(@PathVariable("id") int deck, @RequestParam("name") String name,
			@RequestParam("type") DeckType type, @RequestHeader("referer") String referer) {
		DeckEditDao dao = session.getMapper(DeckEditDao.class);
		if (dao.checkAllow(user.getUser(), deck)) {
			dao.udateDeckBase(deck, name, type);
			session.commit();
		}
		if (referer.endsWith("/d")) {
			return "redirect:/d";
		}
		return "redirect:/d/" + deck;
	}

}
