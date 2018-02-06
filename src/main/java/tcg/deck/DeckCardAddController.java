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
public class DeckCardAddController {

	@Autowired
	private SqlSession session;
	@Autowired
	private User user;

	@RequestMapping(value = "/c/d/add/{card}", method = RequestMethod.GET)
	public String addMain(@PathVariable("card") String id) {
		return add(id, 1, DeckEntryCategory.main);
	}

	@RequestMapping(value = "/c/d/add-p/{card}", method = RequestMethod.GET)
	public String addMainPlayset(@PathVariable("card") String id) {
		return add(id, 4, DeckEntryCategory.main);
	}

	@RequestMapping(value = "/c/d/add-s/{card}", method = RequestMethod.GET)
	public String addSide(@PathVariable("card") String id) {
		return add(id, 1, DeckEntryCategory.side);
	}

	private String add(String cardId, int qty, DeckEntryCategory category) {
		DeckEditDao dao = session.getMapper(DeckEditDao.class);
		if (dao.checkAllow(user.getUser(), user.getCurrentDeck())) {
			Card card = new Card();
			card.setId(cardId);
			DeckEntry entry = new DeckEntry();
			entry.setCard(card);
			entry.setCategory(category);
			entry.setDeck(user.getCurrentDeck());
			entry.setFoil(false);
			entry.setQty(qty);
			dao.addEntry(entry);
			dao.updateDeck(user.getCurrentDeck());
			// dao.addHistory()
			session.commit();
		}
		return "redirect:/c/" + cardId;
	}


}
