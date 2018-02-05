package tcg.deck;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tcg.db.dao.DeckEditDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.DeckEntry;
import tcg.db.dbo.DeckEntryCategory;
import tcg.security.User;

@Controller
public class DeckEditController {

	@Autowired
	private SqlSession session;
	@Autowired
	private User user;

	@RequestMapping(value = "/d/add/{card}", method = RequestMethod.GET)
	public String view(@PathVariable("card") String id) {
		DeckEditDao dao = session.getMapper(DeckEditDao.class);
		if (dao.checkAllow(user.getUser(), user.getCurrentDeck())) {
			Card card = new Card();
			card.setId(id);
			DeckEntry entry = new DeckEntry();
			entry.setCard(card);
			entry.setCategory(DeckEntryCategory.main);
			entry.setDeck(user.getCurrentDeck());
			entry.setFoil(false);
			entry.setQty(1);
			dao.addEntry(entry);
			dao.updateDeck(user.getCurrentDeck());
			// dao.addHistory()
			session.commit();
		}
		return "redirect:/c/" + id;
	}


}
