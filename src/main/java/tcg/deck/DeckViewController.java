package tcg.deck;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tcg.card.formater.CardFormater;
import tcg.db.dao.DeckDao;
import tcg.db.dbo.Deck;
import tcg.db.dbo.DeckEntry;

@Controller
public class DeckViewController {

	@Autowired
	private SqlSession session;

	@Autowired
	private CardFormater formater;

	@RequestMapping(value = "/d/table/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") int id) {
		Deck deck = session.getMapper(DeckDao.class).read(id);
		deck.getCards().stream().forEach(this::format);
		return new ModelAndView("deck/table").addObject("deck", deck);
	}

	private void format(DeckEntry e) {
		e.getCard().setManaCost(formater.manaCost(e.getCard()));
	}

}
